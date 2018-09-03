package dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Iana_Kasimova on 03-Sep-18.
 */
public class TaskMapper<P> implements RowMapper<P>{

    @Override
    public P mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
