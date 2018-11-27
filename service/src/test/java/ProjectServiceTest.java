import config.ServiceConfiguration;
import dao.ProjectDAO;
import dao.TaskDAO;
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
import services.ProjectService;
import services.ProjectServiceImpl;
import services.TaskService;
import services.TaskServiceImpl;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by anakasimova on 10/07/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class})
@Transactional
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    private Project project;
    private String title;

    @Before
    public void init(){
        title = "Rome";
        project = projectService.createProject(title);
    }


    @Test
    public void testSaveProject(){
        String description = "Adventures in Rome";
        project.setDescription(description);
        projectService.saveProject(project);
        projectService.findAll();
        assertThat(projectService.findProjectById(project.getId()).getDescription()).isEqualTo(project.getDescription());
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

    @Test
    public void testDeleteTaskFromProject(){
        Task task = new Task("task");
        projectService.addTaskToProject(project, task);
        task.setProject(null);
        assertThat(projectService.deleteTaskFromProject(project, task).getTasks().contains(task)).isFalse();
        assertThat(taskService.saveTask(task).getProject()).isNull();
    }
}
