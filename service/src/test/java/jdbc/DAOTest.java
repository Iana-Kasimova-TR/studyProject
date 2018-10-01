package jdbc;

import dao.ProjectDAO;
import dao.TaskDAO;
import config.ServiceConfiguration;
import entities.Project;
import entities.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.LocalDateTime;


/**
 * Created by anakasimova on 09/09/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class DAOTest {

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Before
    public void testGetDriverManager() throws Exception {
        jdbcTemplate.execute("DROP TABLE PROJECTS IF EXISTS;");
        jdbcTemplate.execute("DROP TABLE TASKS IF EXISTS;");
        jdbcTemplate.execute("CREATE TABLE PROJECTS(ID BIGINT NOT NULL AUTO_INCREMENT, TITLE VARCHAR(255), DESCRIPTION VARCHAR(255), IS_DELETED BOOLEAN, PRIMARY KEY(ID));");
        jdbcTemplate.execute("CREATE TABLE TASKS(ID BIGINT NOT NULL AUTO_INCREMENT, TITLE VARCHAR(255), DESCRIPTION VARCHAR(255)," +
                "IS_DONE BOOLEAN, DEADLINE DATE, REMIND_DATE DATE, PRIORITY VARCHAR(255), PERCENT_OF_READINESS DOUBLE, PARENT_TASK_ID INT REFERENCES TASKS(ID)" +
                ", PROJECT_ID INT REFERENCES PROJECTS(ID), " +
                "IS_DELETED_FROM_PROJECT BOOLEAN, IS_DELETED BOOLEAN, PRIMARY KEY(ID));");
    }

    @Test
    public void createTaskTest(){
        Task task = new Task("new task!");
        Assert.assertTrue(taskDAO.saveOrUpdateTask(task).equals(task));
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
    public void createAndDeleteProject(){
        Project project = new Project("new Project!");
        Task task = new Task("new task for project");
        project.getTasks().add(task);
        task.setProject(project);
        projectDAO.saveOrUpdateProject(project);
        taskDAO.saveOrUpdateTask(task);

        Assert.assertTrue(taskDAO.getTask(task.getId()).getProject() == null);
    }

    @Test
    public void createAndDeleteTask(){
        Project project = new Project("new Project!");
        Task task = new Task("new task for project");
        project.getTasks().add(task);
        task.setProject(project);
        projectDAO.saveOrUpdateProject(project);
        taskDAO.saveOrUpdateTask(task);
        Assert.assertTrue(projectDAO.getProject(project.getId()).getTasks().isEmpty());

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
