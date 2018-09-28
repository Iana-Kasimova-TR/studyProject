package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by anakasimova on 27/09/2018.
 */
@Configuration
@PropertySource("classpath:configuration.properties")
@ComponentScan("dao")
public class ServiceConfiguration {

    @Inject
    private Environment environment;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("jdbc.url"));
        driverManagerDataSource.setUsername(environment.getProperty("jdbc.username"));
        driverManagerDataSource.setPassword(environment.getProperty("jdbc.password"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
