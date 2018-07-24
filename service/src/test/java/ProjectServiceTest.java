import dao.ProjectDAO;
import dao.TaskDAO;
import entities.Project;
import entities.Task;
import mockDao.InMemoryProjectDao;
import mockDao.InMemoryTaskDao;
import org.junit.Before;
import org.junit.Test;
import services.ProjectService;
import services.ProjectServiceImpl;
import services.TaskService;
import services.TaskServiceImpl;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by anakasimova on 10/07/2018.
 */
public class ProjectServiceTest {

    private ProjectDAO projectDAO;
    private TaskDAO taskDAO;
    private ProjectService projectService;
    private TaskService taskService;
    private Project project;
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
        title = "Rome";
        project = projectService.createProject(title);
    }


    @Test
    public void testCreateProject(){

        assertThat(projectDAO.getProject(project.getId())).isEqualTo(project);
    }

    @Test
    public void testSaveProject(){
        String description = "Adventures in Rome";
        project.setDescription(description);
        assertThat(projectService.saveProject(project).getDescription()).isEqualTo(project.getDescription());
    }

    @Test
    public void testDeleteProject(){
        assertThat(projectService.deleteProject(project)).isTrue();
    }

    @Test
    public void testAddTaskToProject(){
        Task task = new Task("task");
        assertThat(projectService.addTaskToProject(project, task).getTasks().contains(task)).isTrue();
        assertThat(taskService.saveTask(task).getProject()).isEqualTo(project);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteTaskFromProject(){
        Task task = new Task("task");
        project = projectService.addTaskToProject(project, task);
        task = taskService.saveTask(task);

        task.setProject(null);
        assertThat(projectService.deleteTaskFromProject(project, task).getTasks().contains(task)).isFalse();
        assertThat(taskService.saveTask(task).getProject()).isNull();
    }
}
