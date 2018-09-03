package dao;

import dao.mappers.ProjectMapper;
import dao.mappers.TaskMapper;
import entities.Project;
import entities.ProjectId;
import entities.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public class ProjectDAOImpl implements ProjectDAO {

    private final String SQL_SAVE_PROJECT = "insert into PROJECTS(name, description) values(?,?)";
    private final String SQL_UPDATE_PROJECT = "update PROJECT set name=?, description=? where id=?";
    private final String SQL_SAVE_PROJECT_UPDATE_TASKS = "update TASKS set project=? where id=?";
    private final String SQL_DELETE_PROJECT = "delete from PROJECTS where id=?";
    private final String SQL_DELETE_PROJECT_FROM_TASKS = "update TASKS set project=? where id=?";
    private final String SQL_GET_PROJECT = "select * from PROJECTS where id=?";
    private final String SQL_GET_TASKS_FOR_PROJECT = "select * from TASKS where project=?";


    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project saveOrUpdateProject(Project project) {
        if(project.getId() == null){
            ProjectId projectId = new ProjectId(jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(SQL_SAVE_PROJECT, new String[]{"id"});
                    ps.setString(2, project.getName());
                    ps.setString(3, project.getDescription());
                    return ps;
                }
            }, new GeneratedKeyHolder()));
            project.setId(projectId);
            return project;
        }
        Project projectFromDB = jdbcTemplate.queryForObject(SQL_GET_PROJECT, new ProjectMapper<Project>(), project.getId());
        List<Task> tasksForProject = jdbcTemplate.queryForList(SQL_GET_TASKS_FOR_PROJECT, new Integer[]{project.getId().getId()}, Task.class);
        if(!projectFromDB.equals(project)){
            jdbcTemplate.update(SQL_SAVE_PROJECT, project.getName(), project.getDescription(), project.getName(), project.getDescription());
        }


        return project;
    }

    @Override
    public boolean deleteProject(Project project) {
        return false;
    }

    @Override
    public Project getProject(ProjectId id) {
        return null;
    }
}
