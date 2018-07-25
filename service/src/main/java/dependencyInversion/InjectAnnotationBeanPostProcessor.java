package dependencyInversion;

import java.beans.Statement;

/**
 * Created by anakasimova on 23/07/2018.
 */
public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor {


    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
       Definition beanDef = beanFactory.getDefinitions().get(beanName);
       Definition defOfInjectionBean;

        for(DefinitionProperty defProp : beanDef.getDefProp()){
            if(defProp.getReference() != null) {
                defOfInjectionBean = beanFactory.getDefinitions().values().stream().filter(def -> def.getId().equals(defProp.getReference())).findAny().orElse(null);
            }else{
                String typeOfIngectionBean = defProp.getType().substring(defProp.getType().indexOf(".") + 1);
                defOfInjectionBean = beanFactory.getDefinitions().values().stream().filter(def -> def.getAliases().contains(typeOfIngectionBean)).findAny().orElse(null);

            }
            setTheProperty(defProp.getName(), bean, beanFactory.getBean(defOfInjectionBean.getClassName()));
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
