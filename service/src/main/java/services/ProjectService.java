package services;

import entities.Project;
import entities.Task;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface ProjectService {

    public void createProject(String title);

    public void addTaskToProject(Task task);

    public void deleteTaskFromProject(Task task);

    public void addDescriptionOfProject(String description);

    public Project getProject(String title);

    public void changeProject(Project project);
}
