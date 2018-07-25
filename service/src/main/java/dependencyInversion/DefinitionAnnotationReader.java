package dependencyInversion;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.lang.reflect.Field;
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
            def.setClassName(clazz.substring(clazz.indexOf(".") + 1));
            def.setClazz(claz);
            fillDefenition(def);
            definitions.put(def.getClassName(), def);
        }

    }

    private void fillDefenition(Definition def){
        Class[] interfaces = def.getClazz().getInterfaces();
        if(interfaces.length !=0){
            for(int i = 0; i<interfaces.length; i++){
                String nameOfImplInterface = interfaces[i].getName().substring(interfaces[i].getName().indexOf(".") + 1);
                def.getAliases().add(nameOfImplInterface);
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
                if(fields[i].isAnnotationPresent(Named.class) && fields[i].getAnnotation(Named.class).value() != null){
                    defProp.setReference(fields[i].getAnnotation(Named.class).value());
                }else{
                    defProp.setType(fields[i].getType().getName());
                }
                def.getDefProp().add(defProp);
            }
        }
    }
}
