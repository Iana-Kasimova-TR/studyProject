package services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

/**
 * Created by anakasimova on 31/08/2018.
 */
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ProjectService projectService = context.getBean("projectService", ProjectService.class);
        System.out.println(projectService);

        DataSource dataSource = context.getBean(DataSource.class);
        dataSource.getConnection();
        System.out.println(dataSource);
    }
}
