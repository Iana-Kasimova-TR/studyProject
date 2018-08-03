package dependencyInversion.utils;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class ClassUtils {

    public static String normalizeClassName(Class<?> clazz){
        return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
    }
}
