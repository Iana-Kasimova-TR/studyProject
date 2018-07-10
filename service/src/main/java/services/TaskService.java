package services;

import dao.TaskDAO;
import entities.Task;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskService {

    Task createTask(String title);

    Task saveTask(Task task);

    boolean deleteTask(Task task);

    Task addSubTask(Task parentTask, String title);

    Task deleteSubTask(Task parentTask, Task subTask);

}
