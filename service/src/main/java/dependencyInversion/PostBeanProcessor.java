package dependencyInversion;

/**
 * Created by anakasimova on 23/07/2018.
 */
public interface PostBeanProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;

}
