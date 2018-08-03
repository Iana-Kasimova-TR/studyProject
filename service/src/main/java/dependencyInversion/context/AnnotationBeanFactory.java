package dependencyInversion.context;

import dependencyInversion.postProcessors.BeanPostProcessor;
import dependencyInversion.definition.Definition;
import dependencyInversion.scope.Scope;
import dependencyInversion.scope.SingletonScope;
import dependencyInversion.utils.ClassUtils;

import java.util.*;

/**
 * Created by anakasimova on 03/08/2018.
 */

public class AnnotationBeanFactory implements BeanFactory {
    private final BeanDefinitionRegistry registry;
    private final ApplicationContext applicationContext;
    private final Set<BeanPostProcessor> postProcessors = new LinkedHashSet<>();
    private final Map<String, Scope> scopes = new HashMap<>();

    public AnnotationBeanFactory(BeanDefinitionRegistry registry, ApplicationContext applicationContext) {
        this.registry = registry;
        this.applicationContext = applicationContext;
    }

    @Override
    public void configure() {
        //configire all scopes
        scopes.put("singleton", new SingletonScope());
        registry.getBeanDefinition(Scope.class)
                .stream()
                .map(this::getBean)
                .map(s -> (Scope) s)
                .forEach(scope -> scopes.put(scope.getScopeName(), scope));

        //configure all bpp
        registry.getBeanDefinition(BeanPostProcessor.class)
                .stream()
                .map(this::getBean)
                .map(bpp -> (BeanPostProcessor) bpp)
                .forEach(bpp -> postProcessors.add(bpp));
    }

    private <T> T instantiate(Definition definition){
        try {
            // todo, don't instantiate twice
            final Class<?> aClass = Class.forName(definition.getClassName());
            return (T) aClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getBean(final Definition definition) {
        // try to get bean from scope's cache
        Object instance = getBeanFromScope(definition);
        if (instance != null) {
            return instance;
        }
        instance = instantiate(definition);
        // add bean to scope's cache
        addBeanToScope(definition, instance);
        // run built-in post processors
        if (instance instanceof ApplicationContextAware) {
            ((ApplicationContextAware) instance).setApplicationContext(applicationContext);
        }
        try {
            // run bean post processors
            for (BeanPostProcessor postProcessor : postProcessors) {
                instance = postProcessor.postProcessBeforeInitialization(instance, definition.getId());
            }
            for (BeanPostProcessor postProcessor : postProcessors) {
                instance = postProcessor.postProcessAfterInitialization(instance, definition.getId());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        // add changed bean to scope's cache
        addBeanToScope(definition, instance);

        return instance;
    }

    private void addBeanToScope(Definition definition, Object instance) {
        final Scope scope = scopes.get(definition.getScope());
        if (scope == null) {
            throw new RuntimeException("There is no scope with name " + definition.getScope());
        }
        scope.addInstance(definition.getId(), instance);
    }

    private Object getBeanFromScope(final Definition definition) {
        final Scope scope = scopes.get(definition.getScope());
        if (scope == null) {
            throw new RuntimeException("There is no scope with name " + definition.getScope());
        }
        if (scope.hasInstance(definition.getId())) {
            return scope.getInstance(definition.getId());
        }
        return null;
    }

    @Override
    public Object getBean(String beanName) {
        final Definition definition = registry.getBeanDefinition(beanName);
        return getBean(definition);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        final Definition definition = registry.getBeanDefinition(ClassUtils.normalizeClassName(beanClass));
        return (T) getBean(definition);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        final Definition definition = registry.getBeanDefinition(beanName);
        return (T) getBean(definition);
    }
}
