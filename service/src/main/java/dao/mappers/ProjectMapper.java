package dao.mappers;



import entities.Project;
import entities.ProjectId;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Iana_Kasimova on 03-Sep-18.
 */
public class ProjectMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project(rs.getString("project_name"));
        project.setId(new ProjectId(rs.getInt("project_id")));
        project.setDescription(rs.getString("project_description"));
        return project;
    }
}
