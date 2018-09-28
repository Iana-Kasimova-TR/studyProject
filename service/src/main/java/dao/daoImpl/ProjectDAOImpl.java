package dao.daoImpl;

import dao.ProjectDAO;
import dao.mappers.ProjectMapper;
import dao.utils.DaoClassManager;
import entities.Project;
import entities.ProjectId;
import entities.Task;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public class ProjectDAOImpl implements ProjectDAO {

    @Inject
    private TaskDAOImpl taskDAO;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private DaoClassManager daoManager;


    public void setDaoManager(DaoClassManager daoManager) {this.daoManager = daoManager;}

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTaskDAO(TaskDAOImpl taskDAO) {
        this.taskDAO = taskDAO;
    }


    @Override
    public Project saveOrUpdateProject(Project project) {

        if (project == null) {
            throw new RuntimeException("project should not be null!");
        }

        return daoManager.saveEntity(project);
    }


    @Override
    public boolean deleteProject(Project project){

        if(project == null){
            throw new RuntimeException("project should not be null!");
        }
        //delete project
        project.setDeleted(true);
        saveOrUpdateProject(project);
        //delete task from project
        for(Task task : project.getTasks()){
            task.setProject(null);
            task.setDeletedFromProject(true);
            taskDAO.saveOrUpdateTask(task);
        }

        return true;
    }

    @Override
    public Project getProject(ProjectId id) {
        if(id == null){
            throw new RuntimeException("project should not be null!");
        }

        if(!projectExists(id)){
            return null;
        }

        Project projectFromDB = (Project) jdbcTemplate.queryForObject("select * from PROJECTS WHERE ID=?", new ProjectMapper(), id.getValue());
        projectFromDB.setTasks(taskDAO.getTasksFromDBForProject(projectFromDB));
        return  projectFromDB;

    }

    private boolean projectExists(ProjectId id) {
        int count = jdbcTemplate.queryForObject("select count(*) from PROJECTS where ID=?", new Integer[]{id.getValue()}, Integer.class);
        if (count  == 0){
            return false;
        }
        int deleted = jdbcTemplate.queryForObject("select IS_DELETED from PROJECTS where ID=?", new Integer[]{id.getValue()}, Integer.class);
        if (deleted  == 1){
            return false;
        }
        return true;
    }


}
