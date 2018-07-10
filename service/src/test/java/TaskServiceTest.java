import dao.ProjectDAO;
import dao.TaskDAO;
import entities.Project;
import entities.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import services.ProjectService;
import services.ProjectServiceImpl;
import services.TaskService;
import services.TaskServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by anakasimova on 10/07/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    private TaskDAO taskDAO;
    private TaskService taskService;
    private ProjectService projectService;
    private ProjectDAO projectDAO;
    private Task task;

    @Before
    public void init(){
        taskDAO = mock(TaskDAO.class);
        projectDAO = mock(ProjectDAO.class);
        projectService = new ProjectServiceImpl(projectDAO);
        taskService = new TaskServiceImpl(taskDAO, projectService);

        String title = "buy milk";
        task = new Task(title);
        when(taskDAO.saveTask(any(Task.class))).thenReturn(task);
        task = taskService.createTask(title);

    }

    @Test
    public void testCreateTask(){
        assertThat(task).isEqualTo(task);
    }

    @Test
    public void testSaveTask(){
        String description = "buy cheap milk";
        task.setDescription(description);
        task = taskService.saveTask(task);
        assertThat(task.getDescription()).isEqualTo(description);
    }

    @Test
    public void testDeleteTask(){
        when(taskDAO.deleteTask(task)).thenReturn(true);
        assertThat(taskService.deleteTask(task)).isTrue();
    }


    @Test
    public void testAddSubTask(){
        Task subTask = new Task("subtask");
        task.getSubTasks().add(task);
        subTask.setParentTask(task);
        when(taskDAO.saveTask(subTask)).thenReturn(subTask);
        assertThat(taskService.saveTask(task)).isEqualTo(task);
        assertThat(taskService.saveTask(subTask)).isEqualTo(subTask);
    }

    @Test
    public void testDeleteSubTask(){
        Task subTask = new Task("subtask");
        task.getSubTasks().add(task);
        subTask.setParentTask(task);

        when(taskDAO.saveTask(subTask)).thenReturn(subTask);
        task = taskService.saveTask(task);
        subTask = taskService.saveTask(subTask);

        task.getSubTasks().remove(subTask);
        subTask.setParentTask(null);

        subTask = taskService.saveTask(subTask);
        when(taskService.deleteSubTask(task, subTask)).thenReturn(task);
        task = taskService.deleteSubTask(task, subTask);
        assertThat(task.getSubTasks().contains(subTask)).isFalse();
        assertThat(subTask.getParentTask()).isNull();

    }
}
