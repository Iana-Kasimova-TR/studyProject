package dao;

import entities.Project;
import entities.ProjectId;
import entities.Task;
import entities.TaskId;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskDAO {

    Task saveOrUpdateTask(Task task);

    Task getTask(TaskId id);

    List<Task> getTasksByFinishDate(LocalDateTime time);

    List<Task> getTasksByRemindDate(LocalDateTime time);

    Collection<Task> getAllTasks();

    Collection<Task> getTasksForProject(Project project);
}
