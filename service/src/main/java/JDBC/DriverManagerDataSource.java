package JDBC;

import dependencyInversion.utils.ClassUtils;
import dependencyInversion.utils.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Iana_Kasimova on 21-Aug-18.
 */
public class DriverManagerDataSource extends AbstractDriverManagerDataSource {

    public DriverManagerDataSource(String url){
        this.setUrl(url);
    }

    public DriverManagerDataSource(String userName, String password, String url){
        this.setUrl(url);
        this.setUsername(userName);
        this.setPassword(password);
    }

    public DriverManagerDataSource(){
    }

    public void setDriverClassName(String driverClassName){

        if(!StringUtils.isNotEmpty(driverClassName)){
            throw new IllegalArgumentException("Property \'driverClassName\' must not be empty");
        }
        String driverClassNameToUse = driverClassName.trim();

        try {
            Class.forName(driverClassNameToUse, true, ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException var4) {
            throw new IllegalStateException("Could not load JDBC driver class [" + driverClassNameToUse + "]", var4);
        }

        if(this.logger.isInfoEnabled()) {
            this.logger.info("Loaded JDBC driver: " + driverClassNameToUse);
        }

    }


    @Override
    protected Connection getConnectionFromDriver(Properties properties) throws SQLException {
        String url = this.getUrl();
        if(this.logger.isDebugEnabled()) {
            this.logger.debug("Creating new JDBC DriverManager Connection to [" + url + "]");
        }

        return DriverManager.getConnection(url, properties);
    }
}
