package services;

import dao.ProjectDAO;
import entities.Project;
import entities.Task;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectDAO projectDAO;

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public Project createProject(String title) {
      if(title.trim().isEmpty()){
          throw new RuntimeException("you cannot create project without title!");
      }
      Project project = new Project(title);
      return projectDAO.saveOrUpdateProject(project);
    }

    @Override
    public Project saveProject(Project project) {
        return projectDAO.saveOrUpdateProject(project);
    }

    @Override
    public boolean deleteProject(Project project) {
        return projectDAO.deleteProject(project);
    }

    @Override
    public Project addTaskToProject(Project project, Task task) {
        project.getTasks().add(task);
        task.setProject(project);
        return projectDAO.saveOrUpdateProject(project);
    }

    @Override
    public Project deleteTaskFromProject(Project project, Task task) throws RuntimeException{
        project.getTasks().remove(task);
        task.setProject(null);
        return projectDAO.saveOrUpdateProject(project);

    }
}
