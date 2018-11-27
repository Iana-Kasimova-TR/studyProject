package com.epam.training.servlet;

import com.epam.training.utils.ApplicationContextHandler;
import config.ServiceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import services.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(
        name = "create-task-servlet",
        urlPatterns = "/createTask"
)
public class CreateTaskServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        applicationContext = ApplicationContextHandler.getContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("defaultDescription", "");
        req.setAttribute("title", "Input title");
        req.setAttribute("description", "Input description");
        req.getRequestDispatcher("/createTask.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskService taskService = applicationContext.getBean(TaskService.class);
        taskService.createTask(req.getParameter("title"), req.getParameter("description"));
        req.setAttribute("tasks", taskService.findAll());
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
}
