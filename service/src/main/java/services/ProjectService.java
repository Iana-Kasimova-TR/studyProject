package services;

;
import entities.Project;
import entities.ProjectId;
import entities.Task;
import dependencyInversion.validation.NonNull;

import java.util.Collection;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface ProjectService {

    Project createProject(@NonNull String title);

    Project createProject(@NonNull String title, @NonNull String description);

    Project saveProject(@NonNull Project project);

    boolean deleteProject(@NonNull Project project);

    Project addTaskToProject(@NonNull Project project, Task task);

    Project deleteTaskFromProject(@NonNull Project project, @NonNull Task task);

    Project findProjectById(ProjectId id);

    Collection<Project> findAll();
}
