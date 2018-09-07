package services;

import dao.TaskDAO;
import entities.Task;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 08/07/2018.
 */
@Named
public class TaskServiceImpl implements TaskService {

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private ProjectService projectService;

    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public Task createTask(String title) {
        if(title.trim().isEmpty()){
            throw new RuntimeException("you cannot create task without title!");
        }
        Task task = new Task(title);
        return taskDAO.saveOrUpdateTask(task);
    }

    @Override
    public Task saveTask(Task task) {
        return taskDAO.saveOrUpdateTask(task);
    }

    @Override
    public boolean deleteTask(Task task) {
        if(task.getParentTask() != null){
            this.deleteSubTaskFromTask(task.getParentTask(), task);
        }
        if(task.getProject() != null) {
            projectService.deleteTaskFromProject(task.getProject(), task);
        }
        return taskDAO.deleteTask(task);
    }

    @Override
    public Task addSubTask(Task parentTask, Task subTask) {
        parentTask.getSubTasks().add(subTask);
        subTask.setParentTask(parentTask);
        if(parentTask.getProject() != null){
            subTask.setProject(parentTask.getProject());
            projectService.addTaskToProject(parentTask.getProject(), subTask);
        }
        saveTask(subTask);
        return saveTask(parentTask);
    }

    @Override
    public Task deleteSubTaskFromTask(Task parentTask, Task subTask) {
        parentTask.getSubTasks().remove(subTask);
        subTask.setParentTask(null);
        if(parentTask.getProject() != null){
           projectService.deleteTaskFromProject(parentTask.getProject(), subTask);
           subTask.setProject(null);
        }
        taskDAO.saveOrUpdateTask(parentTask);
        return taskDAO.saveOrUpdateTask(subTask);
    }

    @Override
    public Task doExecute(Task task) {
        task.setDone(true);
        task.setPercentOfReadiness(100);
        taskDAO.saveOrUpdateTask(task);
        return task;
    }



}
