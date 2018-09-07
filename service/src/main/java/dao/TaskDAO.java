package dao;

import entities.Task;
import entities.TaskId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskDAO {

    Task saveOrUpdateTask(Task task);

    boolean deleteTask(Task task);

    Task getTask(TaskId id);

    List<Task> getTasksByFinishDate(LocalDateTime time);

    List<Task> getTasksByRemindDate(LocalDateTime time);

}
