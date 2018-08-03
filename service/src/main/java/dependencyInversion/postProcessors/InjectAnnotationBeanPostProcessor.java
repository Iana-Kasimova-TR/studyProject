package dependencyInversion.postProcessors;

import dependencyInversion.context.*;
import dependencyInversion.context.ApplicationContext;
import dependencyInversion.definition.Definition;
import dependencyInversion.definition.DefinitionProperty;

import java.beans.Statement;

/**
 * Created by anakasimova on 23/07/2018.
 */
@Ordered(Ordered.MIN_ORDER)
public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {


    private ApplicationContext  context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        final Definition definition = context.getBeanDefinition(beanName);

        definition.getDefProp().stream().forEach(defProp -> injectProperty(defProp, bean));

        return bean;
    }

    private void injectProperty(DefinitionProperty defProp, Object bean) {
        Definition defOfInjectionBean;
        if(defProp.getReference() != null) {
            defOfInjectionBean = context.getBeanDefinition(defProp.getReference());
        }else{
            defOfInjectionBean = context.getBeanDefinition(defProp.getType());
        }
        setTheProperty(defProp.getName(), bean, context.getBean(defOfInjectionBean.getClassName()));
    }





    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    private void setTheProperty(String propName, Object bean, Object injectionBean){
        Statement statement = new Statement(bean, "set"+propName.substring(0, 1).toUpperCase() + propName.substring(1), new Object[]{injectionBean});
        try {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
