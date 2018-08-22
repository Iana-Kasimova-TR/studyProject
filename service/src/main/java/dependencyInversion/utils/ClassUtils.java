package dependencyInversion.utils;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class ClassUtils {

    public static String normalizeClassName(Class<?> clazz){
        return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable var3) {
            ;
        }

        if(cl == null) {
            cl = ClassUtils.class.getClassLoader();
            if(cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable var2) {
                    ;
                }
            }
        }

        return cl;
    }

}
