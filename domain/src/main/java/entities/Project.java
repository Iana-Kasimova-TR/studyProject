package entities;

import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class Project {

    private String name;
    private String description;
    private List<Task> tasks;


    private Task parentTask;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getParentTask() { return parentTask; }

    public void setParentTask(Task parentTask) { this.parentTask = parentTask; }
}
