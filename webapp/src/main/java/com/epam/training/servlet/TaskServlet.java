package com.epam.training.servlet;

import com.epam.training.utils.ApplicationContextHandler;
import org.springframework.context.ApplicationContext;
import services.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "task-servlet",
        urlPatterns = "/tasks"
)
public class TaskServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        applicationContext = ApplicationContextHandler.getContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskService taskService = applicationContext.getBean(TaskService.class);
        req.setAttribute("tasks", taskService.findAll());
        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }
}
