package services;

import dao.TaskDAO;
import entities.Task;

/**
 * Created by anakasimova on 08/07/2018.
 */
public class TaskServiceImpl implements TaskService {

    private TaskDAO taskDAO;

    private ProjectService projectService;

    public TaskServiceImpl(TaskDAO taskDAO, ProjectService projectService) {

        this.taskDAO = taskDAO;
        this.projectService = projectService;
    }

    @Override
    public Task createTask(String title) {
        Task task = new Task(title);
        return taskDAO.saveTask(task);
    }

    @Override
    public Task saveTask(Task task) {
        return taskDAO.saveTask(task);
    }

    @Override
    public boolean deleteTask(Task task) {
        if(task.getParentTask() != null){
            this.deleteSubTask(task.getParentTask(), task);
        }
        if(task.getProject() != null) {
            projectService.deleteTaskFromProject(task.getProject(), task);
        }
        return taskDAO.deleteTask(task);
    }

    @Override
    public Task addSubTask(Task parentTask, String title) {
        Task task = new Task(title);
        parentTask.getSubTasks().add(task);
        if(parentTask.getProject() != null){
            task.setProject(parentTask.getProject());
        }
        taskDAO.saveTask(parentTask);
        projectService.addTaskToProject(parentTask.getProject(), task);
        return taskDAO.saveTask(task);
    }

    @Override
    public Task deleteSubTask(Task parentTask, Task subTask) {
        parentTask.getSubTasks().remove(subTask);
        subTask.setParentTask(null);
        if(parentTask.getProject() != null){
           projectService.deleteTaskFromProject(parentTask.getProject(), subTask);
           subTask.setProject(null);
        }
        taskDAO.saveTask(parentTask);
        return taskDAO.saveTask(subTask);
    }


}
