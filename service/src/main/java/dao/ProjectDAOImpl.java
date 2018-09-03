package dao;

import entities.Project;
import entities.ProjectId;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Named;

/**
 * Created by anakasimova on 24/07/2018.
 */
@Named
public class ProjectDAOImpl implements ProjectDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project saveProject(Project project) {


        return null;
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
