package Helper;

import Model.Data;
import Utils.Constants;
import Utils.Validator;

import java.lang.management.ManagementFactory;
import java.util.Date;

public class DataStoreHelper {
    private String dataLocation;
    private DataStoreCRDHelper dataStoreCRDHelper;

    public DataStoreHelper(String filePath){
        generateDataLocation(filePath);
        dataStoreCRDHelper = new DataStoreCRDHelper(dataLocation);
    }

    /**
     * Generates dataLocation
     * @param filePath
     */
    private void generateDataLocation(String filePath){
        filePath = getFilePath(filePath);
        dataLocation = String.format("%s%s.json",filePath,getProcessName());
    }

    /**
     * Validates file path. If the file path is null or empty, filePath = default path
     * @param filePath
     * @return filePath
     */
    private String getFilePath(String filePath){
        if (Validator.isNullOrEmpty(filePath)){
            filePath = Constants.DEFAULT_FILE_LOCATION;
        }
        return filePath;
    }

    /**
     * Gets the current processName
     * @return Process name as a string
     */
    private String getProcessName() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        return String.format("file-%s",processName);
    }



    /**
     * Creates an element in the DataStore
     * @param key - key of an element
     * @param value - value of an element
     * @param timeToLive - Time in seconds after which the key-value has to be destroyed
     * @return status of the operation
     */
    public synchronized String create(String key, String value, int timeToLive) {
        if (!Validator.isValidKeyName(key)) {
            return Constants.KEY_LENGTH_EXCEEDED;
        }

        if (dataStoreCRDHelper.doesKeyExists(key)) {
            return Constants.KEY_ALREADY_TAKEN;
        }

        Data data = Data.builder()
                .key(key)
                .value(value)
                .timeToLive(timeToLive)
                .creationDateTimeMillis(new Date().getTime())
                .build();

        if(dataStoreCRDHelper.createData(data)){
            return Constants.CREATION_SUCCEED;
        }else {
            return Constants.CREATION_FAILED;
        }
    }

    /**
     * Reads an element from the DataStore
     * @param key - key of an element
     * @return status of the operation
     */
    public synchronized String read(String key){
        if (!Validator.isValidKeyName(key)) {
            return Constants.KEY_LENGTH_EXCEEDED;
        }

        if (!dataStoreCRDHelper.doesKeyExists(key)) {
            return Constants.KEY_NOT_AVAILABLE;
        }

        Data data = dataStoreCRDHelper.readData(key);
        if (data != null){
            return data.getValue();
        }else{
            return Constants.READ_FAILED;
        }
    }

    /**
     * Delets an element from DataStore
     * @param key - key of an element
     * @return status of the operation
     */
    public synchronized String delete(String key){
        if (!Validator.isValidKeyName(key)) {
            return Constants.KEY_LENGTH_EXCEEDED;
        }

        if (!dataStoreCRDHelper.doesKeyExists(key)) {
            return Constants.KEY_NOT_AVAILABLE;
        }

        if (dataStoreCRDHelper.deleteData(key)){
            return Constants.DELETION_SUCCEED;
        }else {
            return Constants.DELETION_FAILED;
        }
    }
}
