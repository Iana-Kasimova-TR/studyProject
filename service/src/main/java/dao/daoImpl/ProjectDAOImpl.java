package dao.daoImpl;

import dao.ProjectDAO;
import dao.mappers.ProjectMapper;
import dao.mappers.TaskMapper;
import entities.Project;
import entities.ProjectId;
import entities.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public class ProjectDAOImpl implements ProjectDAO {

    @Inject
    private TaskDAOImpl taskDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;

    private final String SQL_SAVE_PROJECT = "insert into PROJECTS(project_name, project_description) values(?,?)";
    private final String SQL_SAVE_PROJECT_UPDATE_TASKS = "update TASKS set is_deleted_from_project=0 and project_id=? where task_id=?";
    private final String SQL_DELETE_PROJECT = "update PROJECTS set is_deleted=1 where project_id=?";
    private final String SQL_DELETE_TASK_FROM_PROJECT = "update TASKS set is_deleted_from_project=1 where task_id=?";
    private final String SQL_GET_PROJECT = "select * from PROJECTS where project_id=? and is_deleted=0";
    private final String SQL_GET_PARENT_TASKS_FOR_PROJECT = "select * from TASKS where project_id=? and is_deleted_from_project=0 and parent_task_id is null";





    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {

        if(project == null){
            throw new RuntimeException("project should not be null!");
        }

        //create project and return id of project from DB
        if(project.getId() == null){
            ProjectId projectId = new ProjectId(jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE_PROJECT, new String[]{"id"});
                ps.setString(2, project.getTitle());
                ps.setString(3, project.getDescription());
                return ps;
            }, new GeneratedKeyHolder()));

            //set up project for tasks
            for (Task task : project.getTasks()) {
                addTaskInProject(task);
            }

            project.setId(projectId);
            return project;
        }

        //if we update existing project
        Project projectFromDB = (Project) jdbcTemplate.queryForObject(SQL_GET_PROJECT, new ProjectMapper(), project.getId().getValue());
        projectFromDB.setTasks(getTasksFromDBForProject(project));
        if(!projectFromDB.equals(project)){
            updateProject(project, projectFromDB);
        }

        return project;
    }


    @Override
    public boolean deleteProject(Project project) {
        if(project == null){
            throw new RuntimeException("project should not be null!");
        }
        //delete project
        jdbcTemplate.update(SQL_DELETE_PROJECT, project.getId().getValue());
        //delete task from project
        for(Task task : project.getTasks()){
            taskDAO.deleteTask(task);
        }

        return true;
    }

    @Override
    public Project getProject(ProjectId id) {
        if(id == null){
            throw new RuntimeException("project should not be null!");
        }

        Project projectFromDB = (Project) jdbcTemplate.queryForObject(SQL_GET_PROJECT, new ProjectMapper(), id.getValue());
        projectFromDB.setTasks(getTasksFromDBForProject(projectFromDB));
        return  projectFromDB;

    }


    private List<Task> getTasksFromDBForProject(Project project) {
        List<Task> tasks = jdbcTemplate.query(SQL_GET_PARENT_TASKS_FOR_PROJECT, new TaskMapper(), project.getId().getValue());
        for(Task task : tasks){
            task.setProject(project);
            task.setSubTasks(taskDAO.getSubTasksForTask(project, task));
        }

        return tasks;
    }



    private void updateProject(Project project, Project projectFromDB) {
        if(!project.getTitle().equals(projectFromDB.getTitle())){
            jdbcTemplate.update("update PROJECTS set project_name=? where project_id=? ", project.getTitle(), project.getId().getValue());
        }
        if(!project.getDescription().equals(projectFromDB.getDescription())){
            jdbcTemplate.update("update PROJECTS set project_description=? where project_id=? ", project.getDescription(), project.getId().getValue());
        }
        if(!CollectionUtils.isEqualCollection(project.getTasks(), projectFromDB.getTasks())){
            updateTasksInProject(project.getTasks(), projectFromDB.getTasks());
        }
    }


    private void updateTasksInProject(List<Task> tasks, List<Task> tasksFromDB) {

        //update the same tasks, wich were not added or deleted from project
     for(int i = 0; i<tasks.size(); i++){

           for(int j = 0; j<tasksFromDB.size(); j++){

               if(tasks.get(i).getId().equals(tasksFromDB.get(j).getId())){
                   if(!tasks.get(i).equals(tasksFromDB.get(j))){
                       updateTaskInProject(tasks.get(i),tasksFromDB.get(j));
                   }
               }
           }

       }

       //add new task in project
       Collection<Task> newTaskForProject = CollectionUtils.removeAll(tasks, tasksFromDB);
        if(!newTaskForProject.isEmpty()){
            for(Task task : newTaskForProject){
                addTaskInProject(task);
            }
        }
        //remove task from project
        Collection<Task> deletedTasksForProject = CollectionUtils.removeAll(tasksFromDB, tasks);
        if(!deletedTasksForProject.isEmpty()){
            for(Task task: deletedTasksForProject) {
                deleteTaskFromProject(task);
            }
        }

    }


    private void updateTaskInProject(Task task, Task taskFromDB) {

        if(!task.getTitle().equals(taskFromDB.getTitle())){
            jdbcTemplate.update("update TASKS set task_name=? where task_id=?", task.getTitle(), task.getId().getValue());
        }
        if(!task.getDescription().equals(taskFromDB.getDescription())){
            jdbcTemplate.update("update TASKS set task_name=? where task_id=?", task.getTitle(), task.getId().getValue());
        }
        //TODO: all fields except is_deleted from project was update in db before, because for this we call updateTask in TaskDao
        if(!CollectionUtils.isEqualCollection(task.getSubTasks(), taskFromDB.getSubTasks())){
            updateTasksInProject(task.getSubTasks(), taskFromDB.getSubTasks());
        }

    }


    private void deleteTaskFromProject(Task task) {
        jdbcTemplate.update(SQL_DELETE_TASK_FROM_PROJECT, task.getId().getValue());
        if(!task.getSubTasks().isEmpty()){
            for(Task subTask : task.getSubTasks()){
                deleteTaskFromProject(subTask);
            }
        }
    }

    private void addTaskInProject(Task task) {
        jdbcTemplate.update(SQL_SAVE_PROJECT_UPDATE_TASKS, task.getProject().getId().getValue(), task.getId().getValue());
        if(!task.getSubTasks().isEmpty()){
            for(Task subTask : task.getSubTasks()){
                addTaskInProject(subTask);
            }
        }
    }
}
