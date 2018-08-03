package dependencyInversion.utils;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class StringUtils {

    public static boolean isNotEmpty(String str){
        if(str.isEmpty() || str == null || str.trim().isEmpty()){
            return false;
        }
        return true;
    }
}
