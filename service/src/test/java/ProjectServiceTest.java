import dao.ProjectDAO;
import dao.TaskDAO;
import entities.Project;
import entities.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import services.ProjectService;
import services.ProjectServiceImpl;
import services.TaskService;
import services.TaskServiceImpl;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by anakasimova on 10/07/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;
    private ProjectService projectService;
    private TaskService taskService;
    private Project project;
    private String title;

    @Before
    public void init(){
        taskDAO = mock(TaskDAO.class);
        projectDAO = mock(ProjectDAO.class);
        taskService = new TaskServiceImpl(taskDAO, new ProjectServiceImpl(projectDAO));
        projectService = new ProjectServiceImpl(projectDAO);

        title = "Rome";
        project = new Project(title);
        when(projectDAO.saveProject(any(Project.class))).thenReturn(project);
        project = projectService.createProject(title);
    }


    @Test
    public void testCreateProject(){
        assertThat(project).isEqualTo(project);
    }

    @Test
    public void testSaveProject(){
        String description = "Adventures in Rome";
        project.setDescription(description);
        when(projectDAO.saveProject(project)).thenReturn(project);
        project = projectService.saveProject(project);
        assertThat(project.getDescription()).isEqualTo(description);
    }

    @Test
    public void testDeleteProject(){
        when(projectService.deleteProject(project)).thenReturn(true);
        assertThat(projectService.deleteProject(project)).isTrue();
    }

    @Test
    public void testAddTaskToProject(){
        Task task = new Task("task");
        project.getTasks().add(task);
        task.setProject(project);
        when(projectDAO.saveProject(project)).thenReturn(project);
        when(taskDAO.saveTask(task)).thenReturn(task);
        project = projectService.addTaskToProject(project, task);
        task = taskService.saveTask(task);
        assertThat(project.getTasks().contains(task)).isTrue();
        assertThat(task.getProject()).isEqualTo(project);
    }

    @Test
    public void testDeleteTaskFromProject(){
        Task task = new Task("task");
        project.getTasks().add(task);
        task.setProject(project);

        when(projectDAO.saveProject(project)).thenReturn(project);
        when(taskDAO.saveTask(task)).thenReturn(task);
        project = projectService.addTaskToProject(project, task);
        task = taskService.saveTask(task);

        project.getTasks().remove(task);
        task.setProject(null);

        when(projectDAO.saveProject(project)).thenReturn(project);
        when(taskDAO.saveTask(task)).thenReturn(task);

        project = projectService.deleteTaskFromProject(project, task);
        assertThat(project.getTasks().contains(task)).isFalse();
        assertThat(task.getProject()).isEqualTo(null);
    }
}
