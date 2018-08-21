package DependencyInversion;

import dependencyInversion.context.AnnotationApplicationContext;
import dependencyInversion.context.ApplicationContext;
import dependencyInversion.context.BeanDefinitionSource;
import dependencyInversion.context.ClassPathAnnotationBeanDefinitionSource;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Iana_Kasimova on 19-Aug-18.
 */
public class AnnotationApplicationContextTest {
    BeanDefinitionSource source = new ClassPathAnnotationBeanDefinitionSource();

    private final ApplicationContext applicationContext = new AnnotationApplicationContext(source);

    @Test
    public void tryGetBeanByName(){
        final NamedBean namedBean = applicationContext.getBean("bean", NamedBean.class);
        Assert.assertNotNull(namedBean);
    }
}
