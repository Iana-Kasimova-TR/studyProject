package dependencyInversion.definition;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
public class MethodMetadata {

    private String declaredClassName;
    private String methodName;

    public MethodMetadata(String declaredClassName, String methodName) {
        this.declaredClassName = declaredClassName;
        this.methodName = methodName;
    }

    public MethodMetadata() {
    }

    public String getDeclaredClassName() {
        return declaredClassName;
    }

    public void setDeclaredClassName(String declaredClassName) {
        this.declaredClassName = declaredClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


}
