package dependencyInversion;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anakasimova on 21/07/2018.
 */
public class AnnotationBeanFactory implements BeanFactory {

    private Map<String, Object> container = new HashMap<>();

    private Map<String, Object> blankBeans = new HashMap<>();

    private Map<String, BeanPostProcessor> postBeanProcessors = new HashMap<>();

    private Map<String, Definition> definitions = new HashMap<>();

    @Override
    public Map<String, Definition> getDefinitions() { return definitions; }


    @Override
    public Object getBean(String beanName) {
        if(definitions.values().stream().filter(def -> def.getId() != null).filter(def -> def.getId().equals(beanName)).findAny().orElse(null) != null){
           return getBeanById(beanName);
        }
        else if(definitions.values().stream().filter(def -> def.getAliases().contains(beanName)).findAny().orElse(null) != null) {
            return getBeanByInterfaceName(beanName);
        }

        return getBeanByClassName(beanName);
    }

    private Object getBeanById(String id){
        String classNameOfNeededBean = definitions.values().stream().filter(def -> def.getId() != null).filter(def -> def.getId().equals(id)).findAny().orElse(null).getClassName();
        return getBeanByClassName(classNameOfNeededBean);
    }

    private Object getBeanByClassName(String name){
        if(container.get(name) != null){
            return container.get(name);
        }
        return blankBeans.get(name);
    }

    private Object getBeanByInterfaceName(String name){
        String classNameOfNeededBean = definitions.values().stream().filter(def -> def.getAliases().contains(name)).findAny().orElse(null).getClassName();
        return getBeanByClassName(classNameOfNeededBean);
    }


    public AnnotationBeanFactory(Map<String, Definition> sourceDefinitions) {
        this.definitions = sourceDefinitions;
        createBeans();
        createBeanPostProccessors();
        beanProcessing();
    }


    private void beanProcessing(){
        for(Map.Entry<String, Object> entryBlankBean: blankBeans.entrySet()){
            Object bean;
            for(BeanPostProcessor beanPostProcessor : postBeanProcessors.values()){
                try {
                    bean = beanPostProcessor.postProcessBeforeInitialization(entryBlankBean.getValue(), entryBlankBean.getKey());
                    bean = beanPostProcessor.postProcessAfterInitialization(bean, entryBlankBean.getKey());
                }catch(Exception e){
                    throw new RuntimeException("cannot process beanPostProcessor " + beanPostProcessor.getClass() + " with bean " + entryBlankBean.getKey());
                }
                container.put(entryBlankBean.getKey(), bean);
            }
        }
    }

    private void createBeanPostProccessors() {
        for(Definition def : definitions.values()){
            if(def.getAliases().contains("BeanPostProcessor")){
                BeanPostProcessor beanPostProcessor;
                try {
                    String injectAnnotationPostBeanProcessorClassName = InjectAnnotationBeanPostProcessor.class.getName().substring(InjectAnnotationBeanPostProcessor.class.getName().indexOf(".") + 1);
                    if(def.getClassName().contains(injectAnnotationPostBeanProcessorClassName)){
                        InjectAnnotationBeanPostProcessor injectPostBeanProc = (InjectAnnotationBeanPostProcessor) def.getClazz().newInstance();
                        injectPostBeanProc.setBeanFactory(this);
                        beanPostProcessor = injectPostBeanProc;
                    }else{
                        beanPostProcessor = (BeanPostProcessor) def.getClazz().newInstance();
                    }
                 postBeanProcessors.put(def.getClassName(), beanPostProcessor);
                } catch (InstantiationException e) {
                    throw new RuntimeException( "Cannot instantiate a " + def.getClassName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException( "Cannot get access to a " + def.getClassName());
                }
            }
        }
    }
    
    public void createBeans(){
        Set<String> beanNames = definitions.keySet();
        for(String beanName: beanNames) {
            if (definitions.get(beanName).getClazz().isAnnotationPresent(Named.class)) {
                    try {
                        blankBeans.put(beanName, definitions.get(beanName).getClazz().newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException("cannot instantiated a class! " + beanName);
                    }
            }
        }
    }




}
