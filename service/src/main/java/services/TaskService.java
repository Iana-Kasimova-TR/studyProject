package services;

import entities.Task;
import dependencyInversion.validation.NonNull;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskService {

    Task createTask(@NonNull String title);

    Task saveTask(@NonNull Task task);

    boolean deleteTask(@NonNull Task task);

    Task addSubTask(@NonNull Task parentTask, @NonNull Task subTask);

    Task deleteSubTaskFromTask(@NonNull Task parentTask, @NonNull Task subTask);

    Task doExecute(@NonNull Task task);


}
