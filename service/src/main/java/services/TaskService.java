package services;

import entities.ProjectId;
import entities.Task;
import dependencyInversion.validation.NonNull;
import entities.TaskId;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskService {

    Task createTask(@NonNull String title);

    Task createTask(@NonNull String title, @NonNull String description, @NonNull String projectId);

    Task saveTask(@NonNull Task task);

    boolean deleteTask(@NonNull Task task);

    Task addSubTask(@NonNull Task parentTask, @NonNull Task subTask);

    Task deleteSubTaskFromTask(@NonNull Task parentTask, @NonNull Task subTask);

    Task doExecute(@NonNull Task task);

    List<Task> findTaskByDeadline(LocalDateTime dateTime);

    List<Task> findTaskByRemind(LocalDateTime dateTime);

    Task findTaskById(TaskId id);

    Collection<Task> findAll();

    Collection<Task> findAllForProject(String id);
}
