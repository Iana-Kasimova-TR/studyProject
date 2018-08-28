package JDBC;

import dependencyInversion.context.Value;
import dependencyInversion.utils.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Iana_Kasimova on 21-Aug-18.
 */
public class DriverManagerDataSource extends AbstractDataSource {

    @Value("jdbc.url")
    private String url;
    @Value("jdbc.username")
    private String username;
    @Value("jdbc.password")
    private String password;
    @Value("jdbc.driver")
    private String driverClass;


    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
