package controllers;

import entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import services.ProjectService;
import view.ProjectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Lookup
    public ProjectView getProjectView(){
        return null;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void transform(HttpServletRequest req, HttpServletResponse resp) {
        List<ProjectView> views = new ArrayList<>();
        for(Project project : projectService.findAll()){
            ProjectView view = getProjectView();
            view.setProject(project);
            view.setAmountOfCompletedProjectTasks(getAmountOfCompletedProjectTasks(project));
            view.setAmountOfInProgressProjectTasks(getAmountOfInProgressProjectTasks(project));
            view.setAmountOfTotalProjectTasks(getAmountOfTotalProjectTasks(project));
            view.setStatus(project.isDeleted() ? "Archived" : "Active");
            views.add(view);
        }
        req.setAttribute("projectViews", views);
    }

    private int getAmountOfTotalProjectTasks(Project project) {
        return Math.toIntExact(project.getTasks().stream().count());
    }

    private int getAmountOfInProgressProjectTasks(Project project) {
        return Math.toIntExact(project.getTasks().stream().filter(task -> task.getPercentOfReadiness() < 100).filter(task -> task.getPercentOfReadiness() > 0).count());
    }

    private int getAmountOfCompletedProjectTasks(Project project) {
        return Math.toIntExact(project.getTasks().stream().filter(task -> task.getPercentOfReadiness() == 100).count());
    }

}
