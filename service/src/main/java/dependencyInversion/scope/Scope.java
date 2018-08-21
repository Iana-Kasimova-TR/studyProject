package dependencyInversion.scope;

/**
 * Created by anakasimova on 03/08/2018.
 */
public interface Scope {

    String getScopeName();

    boolean hasInstance(String id);

    void addInstance(String nameOfInstance, Object instance);

    Object getInstance(String id);

    void removeInstance(String name);
}
