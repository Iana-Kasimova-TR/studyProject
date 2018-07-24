package dependencyInversion;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anakasimova on 21/07/2018.
 */
public class AnnotationBeanFactory implements BeanFactory {

    private Map<String, Object> container = new HashMap<>();

    private Map<String, Object> blankBeans = new HashMap<>();

    private Map<String, PostBeanProcessor> postBeanProcessors = new HashMap<>();

    private Map<String, Definition> definitions = new HashMap<>();

    @Override
    public Map<String, Definition> getDefinitions() { return definitions; }


    @Override
    public Object getBean(String beanName) {
        if(container.get(beanName) != null){
            return container.get(beanName);
        }
        return blankBeans.get(beanName);
    }



    public AnnotationBeanFactory(Map<String, Definition> sourceDefinitions) {
        this.definitions = sourceDefinitions;
        createBeans();
        createPostBeanProccessors();
        beanProcessing();
    }


    private void beanProcessing(){
        for(Map.Entry<String, Object> entryBlankBean: blankBeans.entrySet()){
            Object bean;
            for(PostBeanProcessor postBeanProcessor: postBeanProcessors.values()){
                try {
                    bean = postBeanProcessor.postProcessBeforeInitialization(entryBlankBean.getValue(), entryBlankBean.getKey());
                    bean = postBeanProcessor.postProcessAfterInitialization(bean, entryBlankBean.getKey());
                }catch(Exception e){
                    throw new RuntimeException("cannot process postBeanProcessor " + postBeanProcessor.getClass() + "with bean " + entryBlankBean.getKey());
                }
                container.put(entryBlankBean.getKey(), bean);
            }
        }
    }

    private void createPostBeanProccessors() {
        for(Definition def : definitions.values()){
            if(def.getAliases().contains("PostBeanProcessor")){
                PostBeanProcessor postBeanProcessor;
                try {
                    if(def.getClassName().contains(InjectAnnotationPostBeanProcessor.class.getName())){
                        InjectAnnotationPostBeanProcessor injectPostBeanProc = (InjectAnnotationPostBeanProcessor) def.getClazz().newInstance();
                        injectPostBeanProc.setBeanFactory(this);
                        postBeanProcessor = injectPostBeanProc;
                    }else{
                        postBeanProcessor = (PostBeanProcessor) def.getClazz().newInstance();
                    }
                 postBeanProcessors.put(def.getClassName(), postBeanProcessor);
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
            //if (definitions.get(beanName).getClazz().isAnnotationPresent(Named.class)) {
                if (isInterface(beanName)) {
                    Object bean = instantiateOfImplementationOfInterface(beanName);
                    if (bean == null) {
                        throw new RuntimeException("cannot be found implementation of interface " + beanName);
                    }
                    blankBeans.put(beanName, instantiateOfImplementationOfInterface(beanName));
                    blankBeans.put(bean.getClass().getName(), bean);
                    beanNames.remove(bean.getClass().getName());
                } else {
                    try {
                        blankBeans.put(beanName, definitions.get(beanName).getClazz().newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException("cannot instantiated a class! " + beanName);
                    }
                }
            }
      //  }
    }



    private boolean isInterface(String name){
        boolean isInterface = false;
        try{
            definitions.get(name).getClazz().newInstance();
        }catch (InstantiationException e){
            isInterface = true;
        }catch (Exception e){
            throw  new RuntimeException("cannot instantiated a class! " + name);
        }
        return isInterface;
    }

    private Object instantiateOfImplementationOfInterface(String name){
        Object bean = null;
            for(Definition def: definitions.values()){

                if(def.getAliases().contains(name)){
                    try{
                         bean = def.getClazz().newInstance();
                         return bean;
                    }catch (Exception e){
                        throw  new RuntimeException("cannot instantiated a implementation of inteface! " + name);
                    }

                }
        }
         return bean;
}
}
