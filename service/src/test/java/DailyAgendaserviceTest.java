import dao.ProjectDAO;
import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Project;
import entities.Task;
import mockDao.InMemoryProjectDao;
import mockDao.InMemoryTaskDao;
import org.junit.Before;
import org.junit.Test;
import services.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Iana_Kasimova on 09-Jul-18.
 */
public class DailyAgendaserviceTest {
/*

    private final LocalDateTime localTodayTime = LocalDateTime.now();
    private final LocalDateTime localYesterdayTime = localTodayTime.minusDays(1);
    private TaskService taskService;
    private ProjectService projectService;
    private DailyAgendaService agendaService;
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;


    @Before
    public void init() {
       taskDAO = new InMemoryTaskDao();
       projectDAO = new InMemoryProjectDao();
       taskService  = new TaskServiceImpl();
       projectService = new ProjectServiceImpl();
       projectService.setProjectDAO(projectDAO);
       taskService.setProjectService(projectService);
       taskService.setTaskDAO(taskDAO);
       agendaService = new DailyAgendaServiceImpl();
       agendaService.setTaskDAO(taskDAO);

    }


    @Test
    public void testCreateDailyAgendaWhenWeHaveTasksInThisDate(){

        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");

        task1.setDeadline(localTodayTime);
        task2.setDeadline(localTodayTime);
        task1 = taskService.saveOrUpdateTask(task1);
        task2 = taskService.saveOrUpdateTask(task2);

        DailyAgenda agenda = agendaService.createDailyAgenda(localTodayTime);
        assertThat(agenda.getDailyTasks()).isEqualTo(Arrays.asList(task1, task2));

    }


    @Test
    public void testCreateDailyAgendaWithoutTasksInThisDate(){

        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");

        task1.setDeadline(localYesterdayTime);
        task2.setDeadline(localYesterdayTime);
        task1 = taskService.saveOrUpdateTask(task1);
        task2 = taskService.saveOrUpdateTask(task2);


        DailyAgenda agenda = agendaService.createDailyAgenda(localTodayTime);
        assertThat(agenda.getDailyTasks()).isEqualTo(Collections.emptyList());

    }


    @Test
    public void testCreateDailyAgendaWithSpecificProjectsAndTasks(){
        Project project1 = projectService.createProject("Moscow");
        Project project2 = projectService.createProject("Rome");

        Task task1 = taskService.createTask("buy ticket to Rome");
        task1.setDeadline(localTodayTime);
        project2.getTasks().add(task1);
        project2 = projectService.saveOrUpdateProject(project2);

        Task task2 = taskService.createTask("meeet friends");
        task2.setDeadline(localTodayTime);
        task2 = taskService.saveOrUpdateTask(task2);
        Task task3 = taskService.createTask("go swim");

        DailyAgenda agenda = agendaService.createDailyAgenda(localTodayTime, Arrays.asList(project1, project2), Arrays.asList(task1, task2, task3));
        assertThat(agenda.getDailyTasks().equals(Arrays.asList(task1, task2)));
    }

*/

}
