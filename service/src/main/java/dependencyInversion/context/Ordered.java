package dependencyInversion.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anakasimova on 03/08/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Ordered {

    int DEFAULT_ORDER = 0;
    int MIN_ORDER = Integer.MIN_VALUE;
    int MAX_ORDER = Integer.MAX_VALUE;


    int value() default DEFAULT_ORDER;
}
