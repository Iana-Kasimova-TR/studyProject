package entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 05/07/2018.
 */
public class Project {

    private String name;
    private String description;
    private List<Task> tasks = new ArrayList<>();
    private ProjectId id;

    public void setId(ProjectId id) {
        this.id = id;
    }

    public Project(String name) {
        this.name = name;
    }

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

    public ProjectId getId() {
        return id;
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(description)
                .append(tasks)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        return new EqualsBuilder()
                .append(name, project.name)
                .append(description, project.description)
                .append(tasks, project.tasks)
                .append(id, project.id)
                .isEquals();
    }
}
