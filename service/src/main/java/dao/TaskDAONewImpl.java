package dao;

import entities.Task;
import entities.TaskId;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by anakasimova on 26/07/2018.
 */
@Named("taskDAO")
public class TaskDAONewImpl implements TaskDAO {
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
}
