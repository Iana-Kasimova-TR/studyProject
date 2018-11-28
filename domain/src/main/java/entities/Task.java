package entities;


import entities.equators.TaskEquator;
import entities.hibernateusertype.TaskIdHibernateUserType;
import entities.hibernateusertype.TaskIdUserType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
@Entity
@Table(name = "Task")
@TypeDef(typeClass = TaskIdHibernateUserType.class, defaultForType = TaskId.class)
public class Task  implements Serializable {

    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "IS_DONE")
    private boolean isDone = false;
    @OneToMany(mappedBy = "parentTask", fetch = FetchType.EAGER)
    private List<Task> subTasks = new ArrayList<>();
    @Column(name = "DEADLINE")
    private LocalDateTime deadline;
    @Column(name = "REMIND_DATE")
    private LocalDateTime remindDate;
    @Column(name = "PRIORITY")
    private Priority priority;
    @Column(name = "PERCENT_OF_READINESS")
    private double percentOfReadiness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_TASK_ID")
    private Task parentTask;
    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(name = "custom-generator",
            strategy = "utils.CustomIdGenerator")
    private TaskId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Column(name = "IS_DELETED")
    private boolean deleted = false;
    @Column(name = "IS_DELETED_FROM_PROJECT")
    private boolean deletedFromProject = false;

    public Task() {
        this.percentOfReadiness = 0;
    }

    public Task(String description, String title) {
        this.description = description;
        this.title = title;
        this.percentOfReadiness = 0;
    }

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
        this.percentOfReadiness = 0;
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
