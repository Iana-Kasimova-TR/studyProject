package entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class Task {

    private String description;
    private String title;
    private boolean isDone;
    private List<Task> subTasks = new ArrayList<>();
    private Date deadline;
    private Date remindDate;
    private Priority priority;
    private float percentOfReadiness;
    private Task parentTask;
    private final TaskId id;
    private Project project;

    public Task(String title) {

        this.title = title;
        this.id = new TaskId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public float getPercentOfReadiness() {
        return percentOfReadiness;
    }

    public void setPercentOfReadiness(float percentOfReadiness) {
        this.percentOfReadiness = percentOfReadiness;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }

    public void setParentTask(Task parentTask) { this.parentTask = parentTask; }

    public TaskId getId() { return id; }
}
