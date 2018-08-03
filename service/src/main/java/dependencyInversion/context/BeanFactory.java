package dependencyInversion.context;

/**
 * Created by anakasimova on 03/08/2018.
 */
public interface BeanFactory extends Configurable {

    Object getBean(String beanName);

    <T> T getBean(String beanName, Class<T> beanClass);

    <T> T getBean(Class<T> beanClass);
}
