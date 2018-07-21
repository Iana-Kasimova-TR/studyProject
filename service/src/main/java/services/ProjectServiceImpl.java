package services;

import dao.ProjectDAO;
import entities.Project;
import entities.Task;

import javax.inject.Inject;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectDAO projectDAO;

    @Override
    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public Project createProject(String title) {
      if(title == null || title.trim().isEmpty()){
          throw new RuntimeException("you cannot create project without title!");
      }
      Project project = new Project(title);
      return projectDAO.saveProject(project);
    }

    @Override
    public Project saveProject(Project project) {
        if(project == null || !(project instanceof Project)){
            throw new RuntimeException("you cannot save something, which is not a project!");
        }
        return projectDAO.saveProject(project);
    }

    @Override
    public boolean deleteProject(Project project) {
        if(project == null || !(project instanceof Project)){
            throw new RuntimeException("you cannot delete something, which is not a project!");
        }
        return projectDAO.deleteProject(project);
    }

    @Override
    public Project addTaskToProject(Project project, Task task) {
        if(project == null || !(project instanceof Project)
                || task == null || !(task instanceof Task)){
            throw new RuntimeException("you can add only task in project!");
        }
        project.getTasks().add(task);
        task.setProject(project);
        return projectDAO.saveProject(project);
    }

    @Override
    public Project deleteTaskFromProject(Project project, Task task) throws RuntimeException{
        if(project == null
                || !(project instanceof Project)
                || task == null
                || !(task instanceof Task)
                || !project.getTasks().contains(task)
                || task.getProject() != project){
            throw new RuntimeException("you can delete only task in project and only if project contains this task!");
        }
        project.getTasks().remove(task);
        task.setProject(null);
        return projectDAO.saveProject(project);

    }
}
