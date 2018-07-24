package dependencyInversion;

/**
 * Created by anakasimova on 24/07/2018.
 */
public class ProxyBeanPostProcessor implements PostBeanProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return null;
    }
}
