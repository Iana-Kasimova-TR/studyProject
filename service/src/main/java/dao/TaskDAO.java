package dao;

import entities.Task;

import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskDAO {

    public void addTask(Task task);

    public void updateTask(Task task);

    public void deleteTask(Task task);

    public Task getParentTask(Task task);

    public List<Task> getTasksByDeadline(Date deadline);

    public List<Task> getTasksByRemindDate(Date remindDate);

    public List<Task> getChildTasks(Task task);

    public boolean getReadinessOfTask()
}
