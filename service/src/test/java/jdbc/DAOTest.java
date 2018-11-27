package jdbc;

import dao.ProjectDAO;
import dao.TaskDAO;
import config.ServiceConfiguration;
import entities.Project;
import entities.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * Created by anakasimova on 09/09/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class})
@Transactional
public class DAOTest {


    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ProjectDAO projectDAO;


    @Test
    public void createTaskTest(){
        Task task = new Task("new task!");
        taskDAO.saveOrUpdateTask(task);
        Assert.assertTrue(taskDAO.getTask(task.getId()).equals(task));
    }

    @Test
    public void createAndUpdateProject(){
        Project project = new Project("new Project!");
        Task task = new Task("new task for new project!");
        projectDAO.saveOrUpdateProject(project);
        taskDAO.saveOrUpdateTask(task);
        task.setProject(project);
        project.getTasks().add(task);
        taskDAO.saveOrUpdateTask(task);
        Assert.assertTrue(projectDAO.getProject(project.getId()).getTasks().contains(task));
    }

    @Test
    public void createAndUpdateTask(){
        Task task = new Task("new task!");
        taskDAO.saveOrUpdateTask(task);
        LocalDateTime now = LocalDateTime.now();
        task.setDeadline(now);
        task.getSubTasks().add(new Task("new subTask"));
        Assert.assertTrue(taskDAO.saveOrUpdateTask(task).equals(task));
    }


    @Test
    public void createSubTask(){
        Task task = new Task("new task!");
        Task subTask = new Task("new sub task!");
        task.getSubTasks().add(subTask);
        subTask.setParentTask(task);
        taskDAO.saveOrUpdateTask(task);
        taskDAO.saveOrUpdateTask(subTask);
        Assert.assertTrue(taskDAO.getTask(task.getId()).getSubTasks().contains(subTask));
        Assert.assertTrue(taskDAO.getTask(subTask.getId()).getParentTask().equals(task));
    }

}
