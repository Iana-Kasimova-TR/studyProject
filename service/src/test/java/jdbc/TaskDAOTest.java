package jdbc;

import dao.ProjectDAO;
import dao.TaskDAO;
import entities.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;


/**
 * Created by anakasimova on 09/09/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationTestContext.xml")
public class TaskDAOTest {

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
        jdbcTemplate.execute("CREATE TABLE PROJECTS(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, TITLE VARCHAR(255), DESCRIPTION VARCHAR(255), IS_DELETED BOOLEAN);");
        jdbcTemplate.execute("CREATE TABLE TASKS(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, TITLE VARCHAR(255), DESCRIPTION VARCHAR(255)," +
                "IS_DONE BOOLEAN, DEADLINE DATE, REMIND_DATE DATE, PRIORITY VARCHAR(255), PERCENT_OF_READINESS DOUBLE, PARENT_TASK_ID INT REFERENCES TASKS(ID)" +
                ", PROJECT_ID INT REFERENCES PROJECTS(ID), " +
                "IS_DELETED_FROM_PROJECT BOOLEAN, IS_DELETED BOOLEAN);");
    }

    @Test
    public void createTaskTest(){
        Task task = new Task("new task!");
        System.out.println(taskDAO.saveOrUpdateTask(task).getId().getValue());
        //Assert.assertTrue(taskDAO.saveOrUpdateTask(task).equals(task));

    }
}
