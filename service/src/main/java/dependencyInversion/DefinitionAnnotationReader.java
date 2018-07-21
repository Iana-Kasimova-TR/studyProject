package dependencyInversion;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anakasimova on 17/07/2018.
 */
public class DefinitionAnnotationReader {

    ClassPathAnnotationScanner scanner =  new ClassPathAnnotationScanner();

    Map<String, Definition> definitions = new HashMap<>();

    public Map<String, Definition> getDefinitions() {
        return definitions;
    }

    public void read() throws IOException, ClassNotFoundException{
        ClassLoader loader = Thread.currentThread()
                .getContextClassLoader();
        scanner.scanFrom(loader);
        List<String> classes = scanner.getClasses();
        for(String clazz : classes){
            Definition def = new Definition();
            Class<?> claz = Class.forName(clazz);
            def.setName(clazz);
            def.setClazz(claz);
            fillDefenition(def);
            if (def.getId() != null){
                definitions.put(def.getId(), def);
                return;
            }
            definitions.put(def.getName(), def);
        }

    }

    private void fillDefenition(Definition def){
        Class[] interfaces = def.getClazz().getInterfaces();
        if(interfaces.length !=0){
            for(int i = 0; i<interfaces.length; i++){
                def.getAliases().add(interfaces[i].getName());
            }
        }
        if(def.getClazz().isAnnotationPresent(Named.class)){
            def.setId(def.getClazz().getAnnotation(Named.class).value());
        }


        Field[] fields = def.getClazz().getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            if(fields[i].isAnnotationPresent(Inject.class)){
                DefinitionProperty defProp = new DefinitionProperty();
                defProp.setName(fields[i].getName());
                if(fields[i].isAnnotationPresent(Named.class)){
                    defProp.setReference(fields[i].getAnnotation(Named.class).value());
                }else{
                    defProp.setReference(fields[i].getType().getName());
                }
                def.getDefProp().add(defProp);
            }
        }
    }
}
