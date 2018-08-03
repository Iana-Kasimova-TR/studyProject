package dependencyInversion.context;

import dependencyInversion.definition.Definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class AnnotationApplicationContext implements ApplicationContext {

    private final Collection<Definition> definitions = new ArrayList<>();
    private final BeanFactory beanFactory;

    public AnnotationApplicationContext(BeanDefinitionSource source) {
        read(source);
        this.beanFactory = new AnnotationBeanFactory(this, this);
    }

    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return beanFactory.getBean(beanName, beanClass);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return  beanFactory.getBean(beanClass);
    }

    //fill definitions
    @Override
    public Collection<Definition> read(BeanDefinitionSource source) {
        definitions.clear();
        definitions.addAll(source.read());
        return definitions;

    }

    //get definition by name
    @Override
    public Definition getBeanDefinition(String defName) {
        Objects.requireNonNull(defName, () -> "Definition wasn't provided");

        for (Definition definition : definitions) {
            if (isEquals(definition.getId(), defName)) {
                return definition;
            } else if (isContains(definition.getNames(), defName)) {
                return definition;
            } else if (isEquals(definition.getClassName(), defName)) {
                return definition;
            }
        }

        throw new IllegalArgumentException("There is no definition with name " + defName);

    }

    private boolean isContains(Collection<String> names, String defName) {
        for (String item : names) {
            if (isEquals(item, defName)) {
                return true;
            }
        }
        return false;

    }

    private boolean isEquals(String string1, String string2) {
        return string1 != null && string2 != null &&
                string1.equalsIgnoreCase(string2);

    }

    //get definition by class
    @Override
    public <T> Collection<Definition> getBeanDefinition(Class<T> definitionClass) {
        return definitions.stream().filter(definition -> definition.getInterfaces().contains(definitionClass.getCanonicalName()))
                .sorted(new OrderedComparator())
                .collect(Collectors.toList());
    }

    @Override
    public void configure() {
        beanFactory.configure();
    }
}
