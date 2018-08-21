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
        return "singleton";
    }

    @Override
    public boolean hasInstance(String id) {
        return instances.containsKey(id);
    }

    @Override
    public void addInstance(String id, Object instance) {
        instances.put(id, instance);
    }

    @Override
    public Object getInstance(String id) {
        return instances.get(id);
    }

    @Override
    public void removeInstance(String id) {
        instances.remove(id);
    }
}
