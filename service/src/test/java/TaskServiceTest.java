import config.ServiceConfiguration;
import dao.ProjectDAO;
import dao.TaskDAO;
import entities.Task;
import mockDao.InMemoryProjectDao;
import mockDao.InMemoryTaskDao;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class})
@Transactional
public class TaskServiceTest {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    private Task task;
    private String title;

    @Before
    public void init(){

        title = "Buy milk";
        task = taskService.createTask(title);
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
        assertThat(taskService.findTaskById(task.getId()).getSubTasks().contains(subTask)).isTrue();
        assertThat(taskService.findTaskById(subTask.getId()).getParentTask()).isEqualTo(task);
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

    }

  @Test
    public void testListEqual(){
      List<String> listOne = Arrays.asList("Yana", "Kasimova");
      List<String> listTwo = Arrays.asList("Kasimova", "Yana");
    Assert.assertTrue(CollectionUtils.isEqualCollection(listOne,listTwo));
  }
}
