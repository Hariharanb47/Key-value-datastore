package Utils;

public class Constants {

    public static final String DEFAULT_FILE_LOCATION = "../filebased/src/main/java/";
    public static final int KEY_MAX_LENGTH = 32;
    public static final int MILLISECONDS = 1000;

    //Error messges
    public static final String FILE_NOT_FOUND = "[FAILED][ERROR] File not found.";
    public static final String KEY_LENGTH_EXCEEDED = "[FAILED][ERROR]key length exceeded the limit(32chars)";
    public static final String KEY_ALREADY_TAKEN = "[FAILED][ERROR]key already available";
    public static final String KEY_NOT_AVAILABLE = "[FAILED][ERROR]No such key available";
    public static final String CREATION_SUCCEED = "[SUCCESS] Element created";
    public static final String CREATION_FAILED = "[FAILED][ERROR] Element failed to create";
    public static final String READ_FAILED = "[FAILED][ERROR] Failed to read a element from data store";
    public static final String DELETION_SUCCEED = "[SUCCESS] Element deleted";
    public static final String DELETION_FAILED = "[FAILED][ERROR]Element deletion failed";

}
