package com.epam.training.servlet;

import com.epam.training.utils.ApplicationContextHandler;
import config.ServiceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import services.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "create-project-servlet",
        urlPatterns = "/createProject"
)
public class CreateProjectServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        applicationContext = ApplicationContextHandler.getContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Input title of project");
        req.setAttribute("description", "Input description of project");
        req.setAttribute("defaultDescription", "");
        req.getRequestDispatcher("/createProject.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectService projectService = applicationContext.getBean(ProjectService.class);
        projectService.createProject(req.getParameter("title"), req.getParameter("description"));
        req.setAttribute("projects", projectService.findAll());
        resp.sendRedirect(req.getContextPath() + "/projects");
    }
}
