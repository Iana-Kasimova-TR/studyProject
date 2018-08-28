package dependencyInversion.context;

import dependencyInversion.definition.Definition;
import dependencyInversion.definition.DefinitionProperty;
import dependencyInversion.definition.MethodMetadata;
import dependencyInversion.utils.ClassUtils;
import dependencyInversion.utils.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class ClassPathAnnotationBeanDefinitionSource implements BeanDefinitionSource {

    ClassPathAnnotationScanner scanner =  new ClassPathAnnotationScanner();

    @Override
    public Collection<Definition> read() {
        Collection<Definition> definitions = new ArrayList<>();
        try {
            ClassLoader loader = Thread.currentThread()
                    .getContextClassLoader();
            scanner.scanFrom(loader);
            List<String> classes = scanner.getClasses();
            for (String clazz : classes) {
                definitions.addAll(readDefinition(clazz));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return definitions;
    }

    private Collection<Definition> readDefinition(String clazz) {
        try {
            Class<?> claz = Class.forName(clazz);
            return readDefinition(claz);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<Definition> readDefinition(Class<?> claz){
        List<Definition> defs = new ArrayList<>();
        Definition def = new Definition();
        def.setId(getBeanId(claz));
        def.setNames(getBeanNames(claz));
        def.setDefProp(getBeanProperties(claz));
        def.setClassName(claz.getCanonicalName());
        def.setInterfaces(getBeanInterfaces(claz));
        def.setScope(getBeanScope(claz));
        def.setOrder(getBeanOrder(claz));
        defs.add(def);
        List<Definition> innerDefinitions = new ArrayList<>();
        if(claz.isAnnotationPresent(Configuration.class)){
            List<Method> innerDefs =  Arrays.stream(claz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Bean.class)).collect(Collectors.toList());
            for (Method method: innerDefs) {
                Definition innerDef = new Definition();
                innerDef.setFactoryBean(method.getReturnType().getSimpleName());
                MethodMetadata metadata = new MethodMetadata(claz.getCanonicalName(), method.getName());
                innerDef.setFactoryMethod(metadata);
            }
        }
        defs.addAll(innerDefinitions);

        return defs;
    }


    private int getBeanOrder(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Ordered.class)) {
            return clazz.getAnnotation(Ordered.class).value();
        }
        return Ordered.DEFAULT_ORDER;
    }

    private String getBeanScope(Class<?> clazz) {
        return "singleton"; // todo
    }

    private Collection<String> getBeanInterfaces(Class<?> clazz) {
        return Arrays.stream(clazz.getInterfaces())
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());
    }


    private String getBeanId(Class<?> clazz){
        if(clazz.isAnnotationPresent(Named.class) && StringUtils.isNotEmpty(clazz.getAnnotation(Named.class).value())){
            return clazz.getAnnotation(Named.class).value();
        }else{
            return ClassUtils.normalizeClassName(clazz);
        }
    }

    private Collection<DefinitionProperty> getBeanProperties(Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredFields()).filter( field -> field.isAnnotationPresent(Inject.class)).map(this :: toPropertyDefinition).collect(Collectors.toList());
    }

    private DefinitionProperty toPropertyDefinition(Field field){
        DefinitionProperty defProp = new DefinitionProperty();
        defProp.setName(field.getName());
        if(field.isAnnotationPresent(Named.class) && field.getAnnotation(Named.class).value() != null){
            defProp.setReference(field.getAnnotation(Named.class).value());
        }else{
            defProp.setType(ClassUtils.normalizeClassName(field.getType()));
        }
        return defProp;
    }

    private Collection<String> getBeanNames(Class<?> clazz){
        return Arrays.stream(clazz.getInterfaces())
                .map(ClassUtils::normalizeClassName)
                .collect(Collectors.toList());
    }

}
