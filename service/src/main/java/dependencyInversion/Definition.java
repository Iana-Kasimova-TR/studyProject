package dependencyInversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anakasimova on 17/07/2018.
 */
public class Definition {

    private String id;
    private String className;
    private List<String> aliases = new ArrayList<>();
    private Class<?> clazz;
    private List<DefinitionProperty> defProp = new ArrayList<>();

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> name) {
        this.aliases = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<DefinitionProperty> getDefProp() {
        return defProp;
    }

    public void setDefProp(List<DefinitionProperty> defProp) {
        this.defProp = defProp;
    }
}
