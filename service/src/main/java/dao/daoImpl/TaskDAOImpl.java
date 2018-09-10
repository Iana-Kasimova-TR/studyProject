package dao.daoImpl;


import dao.ProjectDAO;
import dao.TaskDAO;
import dao.mappers.TaskMapper;
import dao.utils.TaskAttribute;
import entities.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public class TaskDAOImpl implements TaskDAO {

    @Inject ProjectDAO projDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }


    public void setProjDAO(ProjectDAO projDAO) {
        this.projDAO = projDAO;
    }


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
            Task topParentTask = jdbcTemplate.queryForObject("select TITLE, DEADLINE, DESCRIPTION, IS_DONE, REMIND_DATE, PRIORITY, PERCENT_OF_READINESS" +
                    "IS_DELETED from TASKS where ID=?", new Object[]{parentId.getValue()}, new TaskMapper());
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
    public boolean deleteTask(Task task){
        if(task == null){
            throw new RuntimeException("task should not be null!");
        }
        task.setDeleted(true);
        for (Task subtask : task.getSubTasks()) {
            subtask.setDeleted(true);
            saveOrUpdateTask(subtask);
        }
        saveOrUpdateTask(task);

        return true;
    }


    @Override
    public Task saveOrUpdateTask(Task task) {
        if(task == null){
            throw new RuntimeException("task should not be null!");
        }

        String sqlQuery;
        Map<TaskAttribute, Object> params = new EnumMap<>(TaskAttribute.class);
        params.put(TaskAttribute.ID, task.getId());
        params.put(TaskAttribute.TITLE, task.getTitle());
        params.put(TaskAttribute.DESCRIPTION, task.getDescription());
        params.put(TaskAttribute.IS_DONE, task.isDone());
        params.put(TaskAttribute.DEADLINE, task.getDeadline());
        params.put(TaskAttribute.REMIND_DATE, task.getRemindDate());
        params.put(TaskAttribute.PRIORITY, task.getPriority());
        params.put(TaskAttribute.PERCENT_OF_READINESS, task.getPercentOfReadiness());
        params.put(TaskAttribute.PARENT_TASK_ID, task.getParentTask());
        params.put(TaskAttribute.PROJECT_ID, task.getProject());
        params.put(TaskAttribute.IS_DELETED_FROM_PROJECT, !(task.getProject() != null));
        params.put(TaskAttribute.IS_DELETED, task.isDeleted());


        if(params.get(TaskAttribute.ID)==null){
            sqlQuery = String.format("INSERT INTO TASKS(%s) VALUES (%s)",
                    params.keySet().stream().map(el -> el.name()).collect(Collectors.joining(", ")),
                    params.values().stream().map(el ->"?").collect(Collectors.joining(", "))
            );
        }else{
            sqlQuery = String.format("UPDATE TASKS SET %s where %s=%s",
                    params.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().name(), entry -> "?")).entrySet().stream()
                            .collect(Collectors.toList()).toString().replace("[", "").replace("]", ""),
                    params.keySet().stream().filter( el -> el.name().equals("ID")).findAny().orElse(null),
                    params.get(TaskAttribute.ID)
            );
        }



        TaskId taskId = new TaskId(jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
            int index = 1;
            for (Map.Entry<TaskAttribute, Object> entry: params.entrySet()) {
                if (entry.getValue() == null) {
                    ps.setNull(index, entry.getKey().typeOfAttribute);
                } else if (entry.getValue() instanceof String) {
                    ps.setString(index, (String) entry.getValue());
                }else if(entry.getValue() instanceof Integer){
                    ps.setInt(index, (Integer) entry.getValue());
                }else if(entry.getValue() instanceof Double){
                    ps.setDouble(index, (Double) entry.getValue());
                }else if(entry.getValue() instanceof Boolean){
                    ps.setBoolean(index, (Boolean) entry.getValue());
                }else if(entry.getValue() instanceof LocalDateTime){
                    ps.setDate(index, Date.valueOf(((LocalDateTime) entry.getValue()).toLocalDate()));
                }else if(entry.getValue() instanceof Priority){
                    ps.setString(index, (((Priority) entry.getValue()).name()));
                }
                index++;
            }
            return ps;
        }, new GeneratedKeyHolder()));


        task.setId(taskId);

        return task;
    }


    //check that row in DB exist and that task is not deleted
    private boolean taskExists(TaskId id) {
        int count = jdbcTemplate.queryForObject("select count(*) from TASKS where ID=?", new Integer[]{id.getValue()}, Integer.class);
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
        List<Task> subTasks = jdbcTemplate.query("select TITLE, DEADLINE, DESCRIPTION, IS_DONE, REMIND_DATE, PRIORITY, PERCENT_OF_READINESS" +
                "IS_DELETED from TASKS where PARENT_TASK_ID=? and IS_DELETED=0", new TaskMapper(), task.getId().getValue());
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
        Integer parentTaskId = jdbcTemplate.queryForObject("select ID from TASKS where ID in (select PARENT_TASK_ID from TASKS where ID=?) and IS_DELETED=0", Integer.class, id.getValue());
        if(parentTaskId != null){
            return new TaskId(parentTaskId);
        }
        return null;
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
        return new ProjectId(jdbcTemplate.queryForObject("select PROJECT_ID from TASKS where ID=?", Integer.class, id.getValue()));
    }

    private boolean isProjectTask(TaskId id) {
        return !jdbcTemplate.queryForObject("select IS_DELETED_FROM_PROJECT from TASKS where ID=?", Boolean.class, id.getValue());
    }

    private List<Task> getSubtasks(Task taskFromDB) {
        List<Task> subtasks = jdbcTemplate.query("select TITLE, DEADLINE, DESCRIPTION, IS_DONE, REMIND_DATE, PRIORITY, PERCENT_OF_READINESS" +
                "IS_DELETED from TASKS where PARENT_TASK_ID=? and IS_DELETED=0", new TaskMapper(), taskFromDB.getId().getValue());
        for (Task subtask : subtasks) {
            subtask.setParentTask(taskFromDB);
            subtask.setSubTasks(getSubtasks(subtask));
        }
        return subtasks;
    }

    public List<Task> getTasksFromDBForProject(Project project) {
        List<Task> tasks = jdbcTemplate.query("select TITLE, DEADLINE, DESCRIPTION, IS_DONE, REMIND_DATE, PRIORITY, PERCENT_OF_READINESS" +
                "IS_DELETED from TASKS where PROJECT_ID=? and IS_DELETED=0 and IS_DELETED_FROM_PROJECT=0", new TaskMapper(), project.getId().getValue());
        for(Task task : tasks){
            task.setProject(project);
            task.setSubTasks(getSubTasksForTask(project, task));
        }

        return tasks;
    }

}
