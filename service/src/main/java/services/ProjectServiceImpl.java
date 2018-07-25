package services;

import dao.ProjectDAO;
import entities.Project;
import entities.Task;
import validation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectDAO projectDAO;

    @Override
    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public Project createProject(@NonNull String title) {
      if(title.trim().isEmpty()){
          throw new RuntimeException("you cannot create project without title!");
      }
      Project project = new Project(title);
      return projectDAO.saveProject(project);
    }

    @Override
    public Project saveProject(@NonNull Project project) {
        return projectDAO.saveProject(project);
    }

    @Override
    public boolean deleteProject( @NonNull Project project) {
        return projectDAO.deleteProject(project);
    }

    @Override
    public Project addTaskToProject(@NonNull Project project, @NonNull Task task) {
        project.getTasks().add(task);
        task.setProject(project);
        return projectDAO.saveProject(project);
    }

    @Override
    public Project deleteTaskFromProject(@NonNull Project project, @NonNull Task task) throws RuntimeException{
        project.getTasks().remove(task);
        task.setProject(null);
        return projectDAO.saveProject(project);

    }
}
