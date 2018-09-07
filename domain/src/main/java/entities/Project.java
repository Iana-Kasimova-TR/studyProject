package entities;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class Project {

    private String title;
    private String description;
    private List<Task> tasks = new ArrayList<>();
    private ProjectId id;

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
                .append(tasks)
                .append(id)
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
                .isEquals();
        if(isEquals){
           isEquals = CollectionUtils.isEqualCollection(tasks, project.getTasks());
        }

        return isEquals;
    }
}
