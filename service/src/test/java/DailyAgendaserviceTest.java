import dao.ProjectDAO;
import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import services.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

/**
 * Created by Iana_Kasimova on 09-Jul-18.
 */
@RunWith(MockitoJUnitRunner.class)
public class DailyAgendaserviceTest {

    private final LocalDateTime localTodayTime = LocalDateTime.now();
    private final LocalDateTime localYesterdayTime = localTodayTime.minusDays(1);
    private TaskService taskService;
    private DailyAgendaService agendaService;
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;

    @Before
    public void init() {
       taskDAO = mock(TaskDAO.class);
       projectDAO = mock(ProjectDAO.class);
       taskService  = new TaskServiceImpl(taskDAO, new ProjectServiceImpl(projectDAO));
       agendaService = new DailyAgendaServiceImpl(taskDAO);
    }


    @Test
    public void testCreateDailyAgendaWhenWeHaveTasksInThisDate(){
        String title = "";
        when(taskDAO.saveTask(new Task(title))).thenReturn(new Task(title));

        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");

        task1.setDeadline(localTodayTime);
        task2.setDeadline(localTodayTime);
        when(taskDAO.saveTask(task1)).thenReturn(task1);
        when(taskDAO.saveTask(task2)).thenReturn(task2);
        task1 = taskService.saveTask(task1);
        task2 = taskService.saveTask(task2);

        when(taskDAO.getTasksByFinishDate(localTodayTime)).thenReturn(Arrays.asList(task1, task2));
        DailyAgenda agenda = agendaService.createDailyAgenda(localTodayTime);
        assertThat(agenda.getDailyTasks()).isEqualTo(Arrays.asList(task1, task2));

    }


    @Test
    public void testCreateDailyAgendaWithoutTasksInThisDate(){
        String title = "";
        when(taskDAO.saveTask(new Task(title))).thenReturn(new Task(title));

        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");

        task1.setDeadline(localYesterdayTime);
        task2.setDeadline(localYesterdayTime);
        when(taskDAO.saveTask(task1)).thenReturn(task1);
        when(taskDAO.saveTask(task2)).thenReturn(task2);
        task1 = taskService.saveTask(task1);
        task2 = taskService.saveTask(task2);

        when(taskDAO.getTasksByFinishDate(localTodayTime)).thenReturn(Collections.emptyList());

        DailyAgenda agenda = agendaService.createDailyAgenda(localTodayTime);
        assertThat(agenda.getDailyTasks()).isEqualTo(Collections.emptyList());

    }


    @Test
    public void testCreateDailyAgendaWithSpecificProjectsAndTasks(){
        //// TODO: 10/07/2018
    }



}
