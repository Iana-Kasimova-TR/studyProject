package dependencyInversion.context;

import dependencyInversion.definition.Definition;

import java.util.Collection;

/**
 * Created by anakasimova on 03/08/2018.
 */
public interface BeanDefinitionSource {

    Collection<Definition> read();
}
