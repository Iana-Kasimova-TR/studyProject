package dependencyInversion.definition;

import dependencyInversion.context.Ordered;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by anakasimova on 17/07/2018.
 */
public class Definition {

    private String id;
    private String className;
    private Collection<String> names = new ArrayList<>();
    private Collection<DefinitionProperty> defProp = new ArrayList<>();
    private Collection<String> interfaces;
    private String scope;
    private int order = Ordered.DEFAULT_ORDER;

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<String> getNames() {
        return names;
    }

    public void setNames(Collection<String> name) {
        this.names = name;
    }

    public Collection<DefinitionProperty> getDefProp() {
        return defProp;
    }

    public void setDefProp(Collection<DefinitionProperty> defProp) { this.defProp = defProp; }

    public Collection<String> getInterfaces() { return interfaces; }

    public void setInterfaces(Collection<String> interfaces) { this.interfaces = interfaces; }

    public String getScope() { return scope; }

    public void setScope(String scope) { this.scope = scope; }

    public int getOrder() { return order; }

    public void setOrder(int order) { this.order = order; }
}
