package services;

;
import dao.ProjectDAO;
import entities.Project;
import entities.Task;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface ProjectService {

    Project createProject(String title);

    Project saveProject(Project project);

    boolean deleteProject(Project project);

    Project addTaskToProject(Project project, Task task);

    Project deleteTaskFromProject(Project project, Task task);

    void setProjectDAO(ProjectDAO projectDAO);

}
