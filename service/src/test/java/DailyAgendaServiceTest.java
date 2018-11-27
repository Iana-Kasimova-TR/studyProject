import config.ServiceConfiguration;
import dao.ProjectDAO;
import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Project;
import entities.Task;
import mockDao.InMemoryProjectDao;
import mockDao.InMemoryTaskDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import services.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Iana_Kasimova on 09-Jul-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class})
@Transactional
public class DailyAgendaServiceTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime LOCAL_DATE_TIME_YESTERDAY = LocalDateTime.now().minusDays(1);

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DailyAgendaService agendaService;


    @Test
    public void testCreateDailyAgendaWhenWeHaveTasksInThisDate(){

        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");

        task1.setDeadline(LOCAL_DATE_TIME);
        task2.setDeadline(LOCAL_DATE_TIME);
        taskService.saveTask(task1);
        taskService.saveTask(task2);

        DailyAgenda agenda = agendaService.createDailyAgenda(LOCAL_DATE_TIME);
        assertThat(agenda.getDailyTasks()).isEqualTo(Arrays.asList(task1, task2));

    }


    @Test
    public void testCreateDailyAgendaWithoutTasksInThisDate(){

        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");

        task1.setDeadline(LOCAL_DATE_TIME_YESTERDAY );
        task2.setDeadline(LOCAL_DATE_TIME_YESTERDAY );
        taskService.saveTask(task1);
        taskService.saveTask(task2);


        DailyAgenda agenda = agendaService.createDailyAgenda(LOCAL_DATE_TIME);
        assertThat(agenda.getDailyTasks()).isEqualTo(Collections.emptyList());

    }


    @Test
    public void testCreateDailyAgendaWithSpecificProjectsAndTasks(){
        Project project1 = projectService.createProject("Moscow");
        Project project2 = projectService.createProject("Rome");

        Task task1 = taskService.createTask("buy ticket to Rome");
        task1.setDeadline(LOCAL_DATE_TIME);
        project2.getTasks().add(task1);
        projectService.saveProject(project2);

        Task task2 = taskService.createTask("meeet friends");
        task2.setDeadline(LOCAL_DATE_TIME);
        taskService.saveTask(task2);
        Task task3 = taskService.createTask("go swim");

        DailyAgenda agenda = agendaService.createDailyAgenda(LOCAL_DATE_TIME, Arrays.asList(project1, project2), Arrays.asList(task1, task2, task3));
        assertThat(agenda.getDailyTasks().equals(Arrays.asList(task1, task2)));
    }


}
