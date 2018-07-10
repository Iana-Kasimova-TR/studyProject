package services;

import dao.ProjectDAO;
import entities.Project;
import entities.Task;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO projectDAO;

    public ProjectServiceImpl(ProjectDAO projectDAO){
        this.projectDAO = projectDAO;
    }

    @Override
    public Project createProject(String title) {
      Project project = new Project(title);
      return projectDAO.saveProject(project);
    }

    @Override
    public Project saveProject(Project project) {
        return projectDAO.saveProject(project);
    }

    @Override
    public boolean deleteProject(Project project) {
        return projectDAO.deleteProject(project);
    }

    @Override
    public Project addTaskToProject(Project project, Task task) {
        project.getTasks().add(task);
        task.setProject(project);
        return projectDAO.saveProject(project);
    }

    @Override
    public Project deleteTaskFromProject(Project project, Task task) throws RuntimeException{
        if(project.getTasks().contains(task) && task.getProject().equals(project)){
            project.getTasks().remove(task);
            task.setProject(null);
            return projectDAO.saveProject(project);
        }else{
            throw new RuntimeException("in project not exist this task " + task);
        }
    }
}
