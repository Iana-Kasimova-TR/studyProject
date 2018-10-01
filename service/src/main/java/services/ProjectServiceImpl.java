package services;

import dao.ProjectDAO;
import entities.Project;
import entities.Task;
import org.springframework.transaction.annotation.Transactional;
import utils.StringUtil;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private TaskService taskService;

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public Project createProject(String title) {
      if(StringUtil.isEmpty(title)){
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
        project.setDeleted(true);
        for(Task task : project.getTasks()){
            task.setDeleted(true);
            taskService.saveTask(task);
        }
        saveProject(project);
        return true;
    }

    @Override
    public Project addTaskToProject(Project project, Task task) {
        if(task.getParentTask() != null){
            if(!task.getParentTask().getProject().equals(project)){
                throw new RuntimeException("parent task and sub task should be have the same project!");
            }
        }
        project.getTasks().add(task);
        task.setProject(project);
        taskService.saveTask(task);
        return saveProject(project);
    }

    @Override
    public Project deleteTaskFromProject(Project project, Task task) throws RuntimeException{
        project.getTasks().remove(task);
        task.setProject(null);
        task.setDeletedFromProject(true);
        taskService.saveTask(task);
        return saveProject(project);

    }
}
