package dependencyInversion;

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
        System.out.println((TaskService)factory.getBean("TaskServiceImpl"));
        //System.out.println(((services.TaskService) factory.getBlankBeans().get("services.TaskServiceImpl")).deleteTask(new Task("ih")));

    }
}
