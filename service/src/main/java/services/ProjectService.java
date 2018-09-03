package services;

;
import entities.Project;
import entities.Task;
import dependencyInversion.validation.NonNull;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface ProjectService {

    Project createProject(@NonNull String title);

    Project saveProject(@NonNull Project project);

    boolean deleteProject(@NonNull Project project);

    Project addTaskToProject(@NonNull Project project, Task task);

    Project deleteTaskFromProject(@NonNull Project project, @NonNull Task task);

}
