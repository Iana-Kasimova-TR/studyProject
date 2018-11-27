package dao.daoImpl;


import dao.ProjectDAO;
import dao.TaskDAO;
import dao.mappers.TaskMapper;
import dao.utils.DaoClassManager;
import entities.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by anakasimova on 24/07/2018.
 */
// @Named
public class TaskDAOImpl implements TaskDAO {

    @Inject
    private ProjectDAO projDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private DaoClassManager daoManager;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public void setProjDAO(ProjectDAO projDAO) {
        this.projDAO = projDAO;
    }

    public void setDaoManager(DaoClassManager daoManager) {daoManager = daoManager;}

    @Override
    public Task getTask(TaskId id) {

        if(id == null){
            throw new RuntimeException("id should not be null!");
        }

        if(!taskExists(id)){
            return null;
        }

        if (isProjectTask(id)) {
            // get project with subtasks
            // return subtask from project
            Project project = projDAO.getProject(getTaskProject(id));
            return getTask(project.getTasks(), id);
        } else {
            // get top-parent task
            // get task from the middle
            TaskId parentId = getTaskParentId(id);
            if (parentId != null) {
                while (getTaskParentId(parentId) != null) {
                    parentId = getTaskParentId(parentId);
                }
            } else {
                parentId = id;
            }
            // parentId - top-parent task id
            Task topParentTask = jdbcTemplate.queryForObject("select * from TASKS where ID=?", new Object[]{parentId.getValue()}, new TaskMapper());
            topParentTask.setSubTasks(getSubtasks(topParentTask));
            if (topParentTask.getId().equals(id)) {
                return topParentTask;
            } else {
                return getTask(topParentTask.getSubTasks(), id);
            }
        }
    }

    @Override
    public List<Task> getTasksByFinishDate(LocalDateTime time) {
        if(time == null){
            throw new RuntimeException("time should not be null!");
        }

        return getTasksByDate("DEADLINE", time);
    }


    @Override
    public List<Task> getTasksByRemindDate(LocalDateTime time) {

        if(time == null){
            throw new RuntimeException("time should not be null!");
        }

        return getTasksByDate("REMIND_DATE", time);
    }

    @Override
    public Collection<Task> getAllTasks() {
        return null;
    }


    @Override
    public Task saveOrUpdateTask(Task task) {
        if(task == null){
            throw new RuntimeException("task should not be null!");
        }

        return daoManager.saveEntity(task);
    }


    //check that row in DB exist and that task is not deleted
    private boolean taskExists(TaskId id) {
        int count = jdbcTemplate.queryForObject("select count(*) from TASKS where ID = ? and IS_DELETED = 0", new Integer[]{id.getValue()}, Integer.class);
        if (count  == 0){
            return false;
        }
        int deleted = jdbcTemplate.queryForObject("select IS_DELETED from TASKS where ID=?", new Integer[]{id.getValue()}, Integer.class);
        if (deleted  == 1){
            return false;
        }
        return true;
    }


    private List<Task> getTasksByDate(String typeOfTime, LocalDateTime time) {

        List<Task> tasks = new ArrayList<>();
        List<Integer> idsOfTasks = jdbcTemplate.queryForList("select ID from TASKS where " + typeOfTime + "=? and ID_DELETED=0", new Object[]{Date.valueOf(time.toLocalDate())}, Integer.class);
        for(int taskId : idsOfTasks){
            tasks.add(getTask(new TaskId(taskId)));
        }

        return tasks;

    }



   public List<Task> getSubTasksForTask(Project project, Task task) {
        List<Task> subTasks = jdbcTemplate.query("select * from TASKS where PARENT_TASK_ID=? and IS_DELETED=0", new TaskMapper(), task.getId().getValue());
        if(!subTasks.isEmpty()){
            for(Task subTask: subTasks){
                subTask.setProject(project);
                subTask.setParentTask(task);
                subTask.setSubTasks(getSubTasksForTask(project, subTask));
            }
        }
        return subTasks;
    }


    private TaskId getTaskParentId(TaskId id) {
        try {
            Integer parentTaskId = jdbcTemplate.queryForObject("select ID from TASKS where ID in (select PARENT_TASK_ID from TASKS where ID=?) and IS_DELETED=0", Integer.class, id.getValue());
            return new TaskId(parentTaskId);
        }
        catch(EmptyResultDataAccessException e){
            return null;

        }

    }

    private Task getTask(List<Task> tasks, TaskId id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
            Task childTask = getTask(task.getSubTasks(), id);
            if (childTask != null) {
                return childTask;
            }
        }
        return null;
    }

    private ProjectId getTaskProject(TaskId id) {
        return new ProjectId(jdbcTemplate.queryForObject("select PROJECT_ID from TASKS where ID=? ", Integer.class, id.getValue()));
    }

    private boolean isProjectTask(TaskId id) {
        if(jdbcTemplate.queryForObject("select IS_DELETED_FROM_PROJECT from TASKS where ID=?", Boolean.class, id.getValue())) {
            return false;
        }else if(jdbcTemplate.queryForObject("select PROJECT_ID from TASKS where ID=?", Integer.class, id.getValue()) == null){
            return false;
        }
        return true;
    }

    private List<Task> getSubtasks(Task taskFromDB) {
        List<Task> subtasks = jdbcTemplate.query("select * from TASKS where PARENT_TASK_ID=? and IS_DELETED=0", new TaskMapper(), taskFromDB.getId().getValue());
        for (Task subtask : subtasks) {
            subtask.setParentTask(taskFromDB);
            subtask.setSubTasks(getSubtasks(subtask));
        }
        return subtasks;
    }

    public List<Task> getTasksFromDBForProject(Project project) {
        List<Task> tasks = jdbcTemplate.query("select * from TASKS where PROJECT_ID=? and IS_DELETED=0 and IS_DELETED_FROM_PROJECT=0",
                new TaskMapper(), project.getId().getValue());
        for(Task task : tasks){
            task.setProject(project);
            task.setSubTasks(getSubTasksForTask(project, task));
        }

        return tasks;
    }

}
