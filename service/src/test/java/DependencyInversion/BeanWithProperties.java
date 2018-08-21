package DependencyInversion;

import javax.inject.Inject;

/**
 * Created by Iana_Kasimova on 19-Aug-18.
 */
public class BeanWithProperties {

    @Inject
    private NamedBean bean;

    public NamedBean getBean() {
        return bean;
    }

    public void setBean(NamedBean bean) {
        this.bean = bean;
    }

}
