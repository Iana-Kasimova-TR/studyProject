package dependencyInversion;

import entities.Task;
import services.TaskService;

/**
 * Created by anakasimova on 17/07/2018.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        DefinitionAnnotationReader reader = new DefinitionAnnotationReader();
        reader.read();
     //   System.out.println(reader.getDefinitions().get(16).getDefProp());
        AnnotationBeanFactory factory = new AnnotationBeanFactoryImpl(reader.definitions);
        factory.createBean("services.TaskServiceImpl");
        System.out.println(((TaskService) factory.getContainer().get("services.TaskServiceImpl")).deleteTask(new Task("ih")));
    }
}
