package entities;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
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
    private LocalDateTime deadline;
    private LocalDateTime remindDate;
    private Priority priority;
    private float percentOfReadiness;
    private Task parentTask;
    private  TaskId id;
    private Project project;


    public void setId(TaskId id) {
        this.id = id;
    }

    public Task(String title) {

        this.title = title;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(LocalDateTime remindDate) {
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

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37)
                .append(id)
                .append(title)
                .append(description)
                .append(isDone)
                .append(subTasks)
                .append(deadline)
                .append(remindDate)
                .append(priority)
                .append(percentOfReadiness)
                .append(parentTask)
                .append(project)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof Task)) return  false;

        Task task = (Task) o;

        return new EqualsBuilder()
                .append(id, task.id)
                .append(title, task.title)
                .append(description, task.description)
                .append(isDone, task.isDone)
                .append(subTasks, task.subTasks)
                .append(deadline, task.deadline)
                .append(remindDate, task.remindDate)
                .append(priority, task.priority)
                .append(percentOfReadiness, task.percentOfReadiness)
                .append(parentTask, task.parentTask)
                .append(project, task.project)
                .isEquals();
    }
}
