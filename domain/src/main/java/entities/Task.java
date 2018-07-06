package entities;


import utils.Priority;

import java.util.Date;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class Task {

    private String description;
    private String title;
    private boolean isDone;
    private List<Task> subTasks;
    private Date deadline;
    private Date remindDate;
    private Priority priority;
    private float percentOfReadiness;

    public Task(String title, Date deadline) {
        this.title = title;
        this.deadline = deadline;
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
}
