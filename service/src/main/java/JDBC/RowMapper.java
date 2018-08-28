package JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
@FunctionalInterface
public interface RowMapper <T> {
    T map(ResultSet rs) throws SQLException;
}
