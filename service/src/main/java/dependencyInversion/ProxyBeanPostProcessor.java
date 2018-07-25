package dependencyInversion;

import entities.Project;
import entities.Task;
import validation.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by anakasimova on 24/07/2018.
 */
public class ProxyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getParameterAnnotations() !=null && method.getParameterAnnotations().length != 0){
                    for(int i = 0; i < method.getParameterAnnotations().length; i++){
                        for( int j = 0; j < method.getParameterAnnotations()[i].length; j++){
                            if(method.getParameterAnnotations()[i][j].annotationType().equals(NonNull.class)){
                                if(args[i] == null){
                                    throw new RuntimeException(" argument " + args[i] + "shouldn't be null!");
                                }
                            }
                        }
                    }
                }
                return method.invoke(bean, args);
            }
        });
    }
}
