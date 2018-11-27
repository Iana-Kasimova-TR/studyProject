package utils;

/**
 * Created by Iana_Kasimova on 01-Oct-18.
 */
public class StringUtil {

    public static boolean isEmpty(String str){

        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String toCheck) {
        return !isEmpty(toCheck);
    }

}
