package services;

import dao.TaskDAO;
import entities.Task;
import validation.NonNull;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskService {

    Task createTask(String title);

    Task saveTask(@NonNull Task task);

    boolean deleteTask(Task task);

    Task addSubTask(Task parentTask, Task subTask);

    Task deleteSubTask(Task parentTask, Task subTask);

    Task doExecute(Task task);

    void setTaskDAO(TaskDAO taskDAO);

    void setProjectService(ProjectService projectService);


}
