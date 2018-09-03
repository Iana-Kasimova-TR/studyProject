package dependencyInversion.postProcessors;

import dependencyInversion.context.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 23/08/2018.
 */
@Ordered(Ordered.MIN_ORDER)
public class ValueInjectionBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Value.class)).collect(Collectors.toList());
        PropertyPlaceConfigurer placeConfigurer = applicationContext.getBean(PropertyPlaceConfigurer.class);
        for(Field field: fields){
            field.setAccessible(true);
            field.set(bean, placeConfigurer.getProperty(field.getAnnotation(Value.class).value()));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return null;
    }
}
