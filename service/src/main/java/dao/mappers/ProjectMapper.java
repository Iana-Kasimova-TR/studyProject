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
        Project project = new Project(rs.getString("TITLE"));
        project.setId(new ProjectId(rs.getInt("ID")));
        project.setDescription(rs.getString("DESCRIPTION"));
        project.setDeleted(rs.getBoolean("IS_DELETED"));
        return project;
    }
}
