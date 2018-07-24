package dependencyInversion;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.beans.Statement;

/**
 * Created by anakasimova on 23/07/2018.
 */
public class InjectAnnotationPostBeanProcessor implements PostBeanProcessor {


    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
       Definition beanDef = beanFactory.getDefinitions().get(beanName);
        for(DefinitionProperty defProp : beanDef.getDefProp()){
            if(defProp.getReference() != null) {
                Definition defOfInjectionBean = beanFactory.getDefinitions().values().stream().filter(def -> def.getId().equals(defProp.getReference())).findAny().orElse(null);
                setTheProperty(defProp.getName(), bean, beanFactory.getBean(defOfInjectionBean.getClassName()));
            }
            setTheProperty(defProp.getName(), bean, beanFactory.getBean(defProp.getType()));
        }

        return bean;
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