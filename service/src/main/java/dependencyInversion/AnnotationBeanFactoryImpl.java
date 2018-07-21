package dependencyInversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anakasimova on 21/07/2018.
 */
public class AnnotationBeanFactoryImpl implements AnnotationBeanFactory {

    private Map<String, Object> container = new HashMap<>();

    private Map<String, Definition> definitions = new HashMap<>();

    public AnnotationBeanFactoryImpl(Map<String, Definition> sourceDefinitions) {
        this.definitions = sourceDefinitions;
    }

    public Map<String, Object> getContainer() { return container; }

    @Override
    public void createBean(String name){
        if(isInterface(name)){
            if(instantiateOfImplementationOfInterface(name) == null){
                throw new RuntimeException("cannot be found implementation of interface " + name);
            }
        container.put(name, instantiateOfImplementationOfInterface(name));
        }

        try {
            container.put(name, definitions.get(name).getClazz().newInstance());
        }catch (Exception e){
            throw  new RuntimeException("cannot instantiated a class! " + name);
        }

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
        try{
            for(Definition def: definitions.values()){

                if(def.getAliases().contains(name)){
                    bean = def.getClazz().newInstance();
                    return bean;
                }
        }
        }catch (Exception e){
            throw  new RuntimeException("cannot instantiated a implementation of inteface! " + name);
         }
         return bean;
}
}
