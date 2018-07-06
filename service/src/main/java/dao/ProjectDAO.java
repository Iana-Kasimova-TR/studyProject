package dao;


import entities.Project;
import entities.Task;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface ProjectDAO {

    public void addProject(Project project);

    public void updateProject(Project project);

    public void deleteProject(Project project);

    public Project getProjectByTitle(String title);

    public Project getProjectByTask(Task task);
}
