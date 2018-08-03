package dependencyInversion.definition;

/**
 * Created by anakasimova on 17/07/2018.
 */
public class DefinitionProperty {

    private String name;
    private String value;
    private String reference;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
