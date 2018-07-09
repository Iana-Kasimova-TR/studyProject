package dao;

import entities.Task;
import entities.TaskId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by anakasimova on 09/07/2018.
 */
public class TaskDAOImpl implements TaskDAO {
    @Override
    public Task saveTask(Task task) {
        return null;
    }

    @Override
    public void deleteTask(Task task) {

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
}
