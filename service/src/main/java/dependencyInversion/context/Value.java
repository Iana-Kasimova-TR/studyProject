package dependencyInversion.context;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by anakasimova on 23/08/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {

    String value() default "";
}
