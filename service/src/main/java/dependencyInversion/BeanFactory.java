package dependencyInversion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anakasimova on 21/07/2018.
 */
public interface BeanFactory {

    Object getBean(String beanName);

    Map<String, Definition> getDefinitions();

}
