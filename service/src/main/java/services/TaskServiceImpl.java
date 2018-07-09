package services;

import dao.TaskDAO;
import entities.Task;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class TaskServiceImpl implements TaskService {

    private TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public Task createTask(String title) {
        return null;
    }

    @Override
    public Task saveTask(Task task) {
        return null;
    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public Task createSubTask(Task parentTask, String title) {
        return null;
    }
}
