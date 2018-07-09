package services;

import dao.TaskDAO;
import entities.Task;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskService {

    Task createTask(String title);

    Task saveTask(Task task);

    void deleteTask(Task task);

    Task createSubTask(Task parentTask, String title);

}
