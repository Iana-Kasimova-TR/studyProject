package dependencyInversion.scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class SingletonScope implements Scope {

    private final Map<String, Object> instances = new ConcurrentHashMap<>();

    @Override
    public String getScopeName() {
        return null;
    }

    @Override
    public boolean hasInstance(String id) {
        return false;
    }

    @Override
    public void addInstance(String nameOfInstance, Object instance) {

    }

    @Override
    public Object getInstance(String id) {
        return null;
    }
}
