package dependencyInversion.postProcessors;

import dependencyInversion.context.ApplicationContext;
import dependencyInversion.context.ApplicationContextAware;

/**
 * Created by anakasimova on 23/08/2018.
 */
public class ValueInjectionBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return null;
    }
}
