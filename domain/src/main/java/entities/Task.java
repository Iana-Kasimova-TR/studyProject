package entities;


import entities.equators.TaskEquator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
@Entity
@Table(name = "TASKS")
public class Task {

    @Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(255)")
    private String description;
    @Column(name = "TITLE", columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "IS_DONE", columnDefinition = "BOOLEAN")
    private boolean isDone = false;
    private List<Task> subTasks = new ArrayList<>();
    @Column(name = "DEADLINE", columnDefinition = "DATE")
    private LocalDateTime deadline;
    @Column(name = "REMIND_DATE", columnDefinition = "DATE")
    private LocalDateTime remindDate;
    @Column(name = "PRIORITY", columnDefinition = "VARCHAR(255)")
    private Priority priority;
    @Column(name = "PERCENT_OF_READINESS", columnDefinition = "DOUBLE")
    private double percentOfReadiness;
    @Column(name = "PARENT_TASK_ID", columnDefinition = "INT")
    private Task parentTask;
    @EmbeddedId
    @Column(name = "ID")
    private  TaskId id;
    @Column(name = "PROJECT_ID", columnDefinition = "INT")
    private Project project;
    @Column(name = "IS_DELETED", columnDefinition = "BOOLEAN")
    private boolean deleted = false;
    @Column(name = "IS_DELETED_FROM_PROJECT", columnDefinition = "BOOLEAN")
    private boolean deletedFromProject = false;

    public boolean isDeletedFromProject() {
        return deletedFromProject;
    }

    public void setDeletedFromProject(boolean deletedFromProject) {
        this.deletedFromProject = deletedFromProject;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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

    public double getPercentOfReadiness() {
        return percentOfReadiness;
    }

    public void setPercentOfReadiness(double percentOfReadiness) {
        this.percentOfReadiness = percentOfReadiness;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public Project getProject() { return project; }

    public void setProject(Project project) {
        this.project = project;
        for (Task subTask : getSubTasks()) {
            subTask.setProject(project);
        }
    }

    public void setParentTask(Task parentTask) { this.parentTask = parentTask; }

    public TaskId getId() { return id; }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37)
                .append(id)
                .append(title)
                .append(description)
                .append(isDone)
                .append(deadline)
                .append(remindDate)
                .append(priority)
                .append(parentTask)
                .append(percentOfReadiness)
                .append(deleted)
                .append(deletedFromProject)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o){

        boolean isEquals;

        if(o == this) return true;
        if(!(o instanceof Task)) return  false;

        Task task = (Task) o;

        //позднее связывание, ранее, char unsigned, from long to double, first about test, STRING POOL IN METASPACE, для кадого потока свой стек, массивы хранятся в хипе
        //фантом чтобы логировать удаленные обьекты, софт удаляется когда память кончаестя, а вик сразу
        //iterator in foreach if количество модификаций изменилось(удаление) выкидывает exception

        isEquals =  new EqualsBuilder()
                .append(id, task.id)
                .append(title, task.title)
                .append(description, task.description)
                .append(isDone, task.isDone)
                .append(deadline, task.deadline)
                .append(remindDate, task.remindDate)
                .append(priority, task.priority)
                .append(percentOfReadiness, task.percentOfReadiness)
                .append(parentTask, task.parentTask)
                .append(project, task.project)
                .append(deleted, task.deleted)
                .append(deletedFromProject, task.deletedFromProject)
                .isEquals();

        if(isEquals){
            isEquals = CollectionUtils.isEqualCollection(subTasks, task.getSubTasks(), new TaskEquator());
        }

        return isEquals;
    }
}
