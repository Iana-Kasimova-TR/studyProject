package entities;

import entities.equators.TaskEquator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
@Entity
@Table(name = "PROJECTS")
public class Project {

    @Column(name = "TITLE", columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(255)")
    private String description;
    @Column(name = "IS_DELETED", columnDefinition = "BOOLEAN")
    private boolean deleted;
    @OneToMany( cascade = CascadeType)
    private List<Task> tasks = new ArrayList<>();
    @EmbeddedId
    @Column(name = "ID")
    private ProjectId id;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setId(ProjectId id) {
        this.id = id;
    }

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ProjectId getId() {
        return id;
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(description)
                .append(id)
                .append(deleted)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {

        boolean isEquals;

        if (o == this) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        isEquals = new EqualsBuilder()
                .append(title, project.title)
                .append(description, project.description)
                .append(id, project.id)
                .append(deleted, project.deleted)
                .isEquals();

        if(isEquals){
           isEquals = CollectionUtils.isEqualCollection(tasks, project.getTasks(), new TaskEquator());
        }

        return isEquals;
    }
}
