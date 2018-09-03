package dao;


import entities.Task;
import entities.TaskId;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public abstract class TaskDAOImpl implements TaskDAO {

    private JdbcTemplate jdbcTemplate;

    private String taskTitle;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    @Override
    public Task saveTask(Task task) {
        return null;
    }

    @Override
    public boolean deleteTask(Task task) {
        return false;
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

    public abstract Task getNewTask();

}
