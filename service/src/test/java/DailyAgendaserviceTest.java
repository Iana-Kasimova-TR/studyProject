import dao.TaskDAO;
import entities.DailyAgenda;
import entities.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import services.DailyAgendaService;
import services.DailyAgendaServiceImpl;
import services.TaskService;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import services.TaskServiceImpl;

/**
 * Created by Iana_Kasimova on 09-Jul-18.
 */
@RunWith(MockitoJUnitRunner.class)
public class DailyAgendaserviceTest {

    private final LocalDateTime localTodayTime = LocalDateTime.now();
    private final LocalDateTime localYesterdayTime = localTodayTime.minusDays(1);
    private TaskService taskService;
    private DailyAgendaService agendaService;

    @Before
    public void init() {
       taskService  = new TaskServiceImpl(mock(TaskDAO.class));
       agendaService = new DailyAgendaServiceImpl(mock(TaskDAO.class));
        when(taskService.
    }


    @Test
    public void createDailyAgendaTest(){
        Task task1 = taskService.createTask("buy milk");
        Task task2 = taskService.createTask("buy fuits");
        task1.setDeadline(localTodayTime);
        task2.setDeadline(localTodayTime);
        taskService.saveTask(task2);
        taskService.saveTask(task1);

        DailyAgenda agenda = agendaService.createDailyAgenda(localTodayTime);
        assertThat(agenda.getDailyTasks()).isEqualTo(Arrays.asList(task1, task2));

    }

}
