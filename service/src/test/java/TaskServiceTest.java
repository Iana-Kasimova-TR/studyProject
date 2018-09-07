import dao.ProjectDAO;
import dao.TaskDAO;
import mockDao.InMemoryProjectDao;
import mockDao.InMemoryTaskDao;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.ProjectService;
import services.ProjectServiceImpl;
import services.TaskService;
import services.TaskServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by anakasimova on 10/07/2018.
 */
public class TaskServiceTest {
  /*  private ProjectDAO projectDAO;
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
        assertThat(taskDAO.getTask(task.getValue())).isEqualTo(task);
    }


    @Test
    public void testSaveTask(){
        task.setDescription("milk should be without laktoza");
        taskService.saveOrUpdateTask(task);
        assertThat(taskService.saveOrUpdateTask(task).getDescription()).isEqualTo(task.getDescription());
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
        assertThat(taskDAO.getTask(subTask.getValue()).getParentTask()).isEqualTo(task);
    }

    @Test
    public void deleteSubTaskFromTask(){
        Task subTask = taskService.createTask("buy eggs");
        task = taskService.addSubTask(task, subTask);

        task = taskService.deleteSubTaskFromTask(task, subTask);

        assertThat(task.getSubTasks().contains(subTask)).isFalse();
        assertThat(subTask.getParentTask()).isNull();
    }

    @Test
    public void testDoExecute(){
        task = taskService.doExecute(task);
        assertThat(task.isDone()).isTrue();
        assertThat(task.getPercentOfReadiness()).isEqualTo(100);

    }*/

  @Test
    public void testListEqual(){
      List<String> listOne = Arrays.asList("Yana", "Kasimova");
      List<String> listTwo = Arrays.asList("Kasimova", "Yana");
    Assert.assertTrue(CollectionUtils.isEqualCollection(listOne,listTwo));
  }
}
