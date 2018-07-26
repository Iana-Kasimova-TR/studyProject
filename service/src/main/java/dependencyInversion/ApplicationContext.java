package dependencyInversion;

import dao.TaskDAO;
import entities.Project;
import entities.Task;
import services.TaskService;

/**
 * Created by anakasimova on 17/07/2018.
 */
public class ApplicationContext {
    public static void main(String[] args) throws Exception{
        DefinitionAnnotationReader reader = new DefinitionAnnotationReader();
        reader.read();
        System.out.println(reader.getDefinitions());
        BeanFactory factory = new AnnotationBeanFactory(reader.definitions);
        TaskDAO taskDAO = (TaskDAO)factory.getBean("taskDAO");
        System.out.println(taskDAO);
        taskDAO.saveTask(null);
    }
}
