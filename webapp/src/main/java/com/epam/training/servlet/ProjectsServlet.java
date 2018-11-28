package com.epam.training.servlet;

import com.epam.training.utils.ApplicationContextHandler;
import org.springframework.context.ApplicationContext;
import services.ProjectService;
import controllers.ProjectController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "projects-servlet",
        urlPatterns = "/projects"
)
public class ProjectsServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        applicationContext = ApplicationContextHandler.getContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectController controller = applicationContext.getBean(ProjectController.class);
        controller.transform(req, resp);
        req.getRequestDispatcher("/projects.jsp").forward(req, resp);
    }
}
