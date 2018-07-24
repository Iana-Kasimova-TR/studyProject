import dao.ProjectDAO;
import dao.TaskDAO;
import entities.Task;
import mockDao.InMemoryProjectDao;
import mockDao.InMemoryTaskDao;
import org.junit.Before;
import org.junit.Test;
import services.ProjectService;
import services.ProjectServiceImpl;
import services.TaskService;
import services.TaskServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by anakasimova on 10/07/2018.
 */
public class TaskServiceTest {
    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;
    private ProjectService projectService;
    private TaskService taskService;
    private Task task;
    private String title;

    @Before
    public void init(){
        taskDAO = new InMemoryTaskDao();
        projectDAO = new InMemoryProjectDao();
        taskService = new TaskServiceImpl();
        projectService = new ProjectServiceImpl();
        taskService.setTaskDAO(taskDAO);
        projectService.setProjectDAO(projectDAO);
        taskService.setProjectService(projectService);

        title = "Buy milk";
        task = taskService.createTask(title);
    }

    @Test
    public void testCreateTask(){
        assertThat(taskDAO.getTask(task.getId())).isEqualTo(task);
    }


    @Test
    public void testSaveTask(){
        task.setDescription("milk should be without laktoza");
        taskService.saveTask(task);
        assertThat(taskService.saveTask(task).getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void deleteTask(){
        assertThat(taskService.deleteTask(task)).isTrue();
    }

    @Test
    public void addSubTask(){
        Task subTask = taskService.createTask("buy eggs");
        task = taskService.addSubTask(task, subTask);
        assertThat(task.getSubTasks().contains(subTask)).isTrue();
        assertThat(taskDAO.getTask(subTask.getId()).getParentTask()).isEqualTo(task);
    }

    @Test
    public void deleteSubTask(){
        Task subTask = taskService.createTask("buy eggs");
        task = taskService.addSubTask(task, subTask);

        task = taskService.deleteSubTask(task, subTask);

        assertThat(task.getSubTasks().contains(subTask)).isFalse();
        assertThat(subTask.getParentTask()).isNull();
    }

    @Test
    public void testDoExecute(){
        task = taskService.doExecute(task);
        assertThat(task.isDone()).isTrue();
        assertThat(task.getPercentOfReadiness()).isEqualTo(100);

    }
}
