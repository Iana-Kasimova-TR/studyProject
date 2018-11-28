package view;

import entities.Project;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProjectView {
    private Project project;
    private int amountOfCompletedProjectTasks;
    private int amountOfInProgressProjectTasks;
    private int amountOfTotalProjectTasks;
    private String status;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getAmountOfCompletedProjectTasks() {
        return amountOfCompletedProjectTasks;
    }

    public void setAmountOfCompletedProjectTasks(int amountOfCompletedProjectTasks) {
        this.amountOfCompletedProjectTasks = amountOfCompletedProjectTasks;
    }

    public int getAmountOfInProgressProjectTasks() {
        return amountOfInProgressProjectTasks;
    }

    public void setAmountOfInProgressProjectTasks(int amountOfInProgressProjectTasks) {
        this.amountOfInProgressProjectTasks = amountOfInProgressProjectTasks;
    }

    public int getAmountOfTotalProjectTasks() {
        return amountOfTotalProjectTasks;
    }

    public void setAmountOfTotalProjectTasks(int amountOfTotalProjecttasks) {
        this.amountOfTotalProjectTasks = amountOfTotalProjecttasks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
