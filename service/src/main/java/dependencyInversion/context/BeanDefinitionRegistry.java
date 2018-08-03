package dependencyInversion.context;

import dependencyInversion.definition.Definition;

import java.util.Collection;

/**
 * Created by anakasimova on 03/08/2018.
 */
public interface BeanDefinitionRegistry {

    Collection<Definition> read(BeanDefinitionSource source);

    Definition getBeanDefinition(String defName);

    <T> Collection<Definition> getBeanDefinition(Class<T> definitionClass);

}
