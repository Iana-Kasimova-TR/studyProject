package dao.daoImpl;


import dao.ProjectDAO;
import dao.TaskDAO;
import dao.mappers.TaskMapper;
import entities.Project;
import entities.ProjectId;
import entities.Task;
import entities.TaskId;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public abstract class TaskDAOImpl implements TaskDAO {

    @Inject
    private ProjectDAOImpl projDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;

    private final String SQL_SAVE_TASK = "insert into TASKS(task_title, task_description, is_done, task_deadline, " +
            "task_remind_date, task_priority, percent_Readiness, parent_task_id, project_id, is_deleted_from_project, is_deleted) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_GET_SUBTASKS_FOR_TASK = "select * from TASKS where parent_task_id=?";
    private final String SQL_DELETE_TASK = "update TASKS set is_deleted=1 where task_id=?";


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task saveOrUpdateTask(Task task) {

        //create new task
        if(task.getId() == null){

            TaskId taskId = new TaskId(jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SAVE_TASK, new String[]{"id"});
                ps.setString(2, task.getTitle());
                ps.setString(3, task.getDescription());
                ps.setBoolean(4, Boolean.valueOf(task.isDone()));

                if(task.getDeadline() != null) {
                    ps.setDate(5, Date.valueOf(task.getDeadline().toLocalDate()));
                }
                else {
                    ps.setNull(5, Types.DATE);
                }
                if(task.getRemindDate() != null) {
                    ps.setDate(6, Date.valueOf(task.getRemindDate().toLocalDate()));
                }
                else {
                    ps.setNull(6, Types.DATE);
                }
                if(task.getPriority() != null) {
                    ps.setString(7, task.getPriority().name());
                }else{
                    ps.setNull(7, Types.CHAR);
                }
                ps.setFloat(8, Float.valueOf(task.getPercentOfReadiness()));
                if(task.getParentTask() != null){
                    ps.setInt(9, task.getParentTask().getId().getValue());
                }else{
                    ps.setNull(9, Types.INTEGER);
                }
                if(task.getProject() != null){
                    ps.setInt(10, task.getProject().getId().getValue());
                }else{
                    ps.setNull(10, Types.INTEGER);
                }
                ps.setBoolean(11, (task.getProject() != null));
                ps.setBoolean(12, false);
                return ps;
            }, new GeneratedKeyHolder()));


            //we add parent task in subtask(subtasks of these subtask already have parent task)
            if(!task.getSubTasks().isEmpty()){
                for (Task subTask : task.getSubTasks()){
                    jdbcTemplate.update("update TASKS set parent_task_id=? where task_id=?", subTask.getParentTask().getId().getValue(), subTask.getId().getValue());
                }
            }

            task.setId(taskId);

            return task;
        }

        //if we update existing task
        Task taskFromDB = (Task) jdbcTemplate.query("select task_title, task_description, is_done, task_deadline, " +
                "task_remind_date, task_priority, percent_Readiness where task_id", new TaskMapper(), task.getId().getValue());


        ProjectId projectId = new ProjectId(jdbcTemplate.queryForObject("select project_id from TASKS where task_id=?",
                new Integer[]{task.getId().getValue()} , Integer.class);

        taskFromDB.setProject(projDAO.getProject(projectId));
        taskFromDB.setSubTasks(getSubTasksForTask(taskFromDB.getProject(), task));

        if(!task.equals(taskFromDB)){
            updateTask(task, taskFromDB);
        }

        return task;
    }

    @Override
    public Task getTask(TaskId id) {
        return null;
    }

    @Override
    public List<Task> getTasksByFinishDate(LocalDateTime time) {
        return null;
    }

    @Override
    public List<Task> getTasksByRemindDate(LocalDateTime time) {
        return null;
    }

    @Override
    public boolean deleteTask(Task task){
        jdbcTemplate.update(SQL_DELETE_TASK, task.getId().getValue());
        if(!task.getSubTasks().isEmpty()){
            for(Task subTask : task.getSubTasks()){
                deleteTask(subTask);
            }
        }
        return true;
    }

    public Task getNewTask(){

    };

    private void updateTask(Task task, Task taskFromDB) {
        if(!task.getTitle().equals(taskFromDB.getTitle())){
            jdbcTemplate.update("update TASKS set task_title=? where task_id=?" task.getTitle(), task.getId().getValue() );
        }
        if(!CollectionUtils.isEqualCollection(task.getSubTasks(), taskFromDB.getSubTasks()){
            updateSubtasks(task.getSubTasks(), taskFromDB.getSubTasks());
        }
    }


    public List<Task> getSubTasksForTask(Project project, Task task) {
        List<Task> subTasks = jdbcTemplate.query(SQL_GET_SUBTASKS_FOR_TASK, new TaskMapper(), task.getId().getValue());
        if(!subTasks.isEmpty()){
            for(Task subTask: subTasks){
                subTask.setProject(project);
                subTask.setParentTask(task);
                subTask.setSubTasks(getSubTasksForTask(project, subTask));
            }
        }
        return subTasks;
    }



    private void updateSubtasks(List<Task> subTasks, List<Task> subTasks1) {

    }



}
