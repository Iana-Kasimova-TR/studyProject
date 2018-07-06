package services;

import entities.Task;

/**
 * Created by anakasimova on 06/07/2018.
 */
public interface TaskService {

    public void createTask(String title);

    public void changeTask(Task task);

    public void deleteTask(Task task);

}
