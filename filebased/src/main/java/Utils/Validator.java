package Utils;

public class Validator {

    public static boolean isNullOrEmpty(final CharSequence cs)
    {
        if (cs == null || cs.length() == 0)
        {
            return true;
        }
        return false;
    }

    public static boolean isValidKeyName(String key){
        if (key.length() > Constants.KEY_MAX_LENGTH){
            return false;
        }
        return true;
    }
}
