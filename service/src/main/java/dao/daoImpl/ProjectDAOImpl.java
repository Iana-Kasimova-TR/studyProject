package dao.daoImpl;

import dao.ProjectDAO;
import dao.mappers.ProjectMapper;
import dao.utils.DaoClassManager;
import entities.Project;
import entities.ProjectId;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

/**
 * Created by anakasimova on 24/07/2018.
 */
// @Named
public class ProjectDAOImpl implements ProjectDAO {

    private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class.getName());

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
    public Project getProject(ProjectId id) {
        if(id == null){
            throw new RuntimeException("project should not be null!");
        }

        if(!projectExists(id)){
            return null;
        }

        Project projectFromDB = (Project) jdbcTemplate.queryForObject("select count(*) from PROJECTS where ID = ? and IS_DELETED = 0", new ProjectMapper(), id.getValue());
        Project proxiedProject = (Project) Proxy.newProxyInstance(getClass().getClassLoader(), projectFromDB.getClass().getInterfaces(),
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getTasks")) {
                    if (projectFromDB.getTasks().isEmpty()) {
                        projectFromDB.setTasks(taskDAO.getTasksFromDBForProject(projectFromDB));
                    }
                }
                return method.invoke(projectFromDB, args);
            }
        });
        return  proxiedProject;

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

    @Override
    public Collection<Project> findAll() {
        LOGGER.info("ProjectDAOImpl");
        return null;
    }
}
