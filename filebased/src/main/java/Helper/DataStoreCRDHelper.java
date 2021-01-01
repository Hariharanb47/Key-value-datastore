package Helper;

import Model.Data;
import Utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataStoreCRDHelper {
    private Map<String,String> dataMap = new HashMap<>();
    private String dataLocation;

    public DataStoreCRDHelper(String dataLocation){
        this.dataLocation = dataLocation;
        loadDataFromFile();
    }

    /**
     * Loads the data from the file into hash map.
     */
    public void loadDataFromFile(){
        File file = new File(dataLocation);
        if (file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                dataMap = (HashMap<String, String>) objectInputStream.readObject();

                fileInputStream.close();
                objectInputStream.close();
            } catch (FileNotFoundException e) {
                System.err.println(Constants.FILE_NOT_FOUND);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(Constants.READ_FAILED);
            }
        }
    }

    /**
     * Checks whether key already exists in the exizting file.
     * @param key
     */
    public boolean doesKeyExists(String key){
        loadDataFromFile();

        if (dataMap != null || !dataMap.isEmpty()){
            if(dataMap.containsKey(key)){
                if (doesKeyAlive(key)){
                    return false;
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the key is still alive if not, it will delete.
     * @param key
     */
    public boolean doesKeyAlive(String key){
        String dataStr = dataMap.get(key);
        Data data = new Gson().fromJson(dataStr, Data.class);

        long currentDateTimeMillis = new Date().getTime();
        if (data.getTimeToLive() > 0
                && (currentDateTimeMillis - data.getCreationDateTimeMillis()) >= (data.getTimeToLive() * Constants.MILLISECONDS)) {
            return deleteData(key);
        }

        return false;
    }

    /**
     * Creates new data in the file
     * @param data
     * @return
     */
    public boolean createData(Data data){
        // add new element
        dataMap.put(data.getKey(), new Gson().toJson(data));
        return writeData();
    }

    /**
     * Reads data from the file
     * @param key - key of the data
     * @return data
     */
    public Data readData(String key){
        String dataStr = dataMap.get(key);
        Data data = new Gson().fromJson(dataStr, Data.class);
        return data;
    }

    /**
     * Deletes data from the file
     * @param key - key of the data
     */
    public boolean deleteData(String key){
        dataMap.remove(key);
        return writeData();
    }

    /**
     * Writes data into the file
     * @return Status of the write operation
     */
    public boolean writeData() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(dataLocation);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(dataMap);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
