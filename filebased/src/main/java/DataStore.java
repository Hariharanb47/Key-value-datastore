import Helper.DataStoreHelper;
import Model.Employee;
import com.google.gson.Gson;

import java.util.Scanner;

public class DataStore {
    public static void main(String[] args) {
        //Data used in the file
        Employee employee = Employee.builder()
                .id(1001)
                .name("Hari")
                .designation("SDE-1")
                .build();

        //Read file path from user
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter file path: ");
        String filePath = sc.nextLine();

        DataStoreHelper dataStoreHelper = new DataStoreHelper(filePath);

        //Creates an element
        System.out.println(dataStoreHelper.create("100", new Gson().toJson(employee),10));
        System.out.println(dataStoreHelper.create("100", new Gson().toJson(employee),0));
        System.out.println(dataStoreHelper.create("100", new Gson().toJson(employee),10));
        System.out.println(dataStoreHelper.create("200", new Gson().toJson(employee),50));

        //Reads an element
        System.out.println(dataStoreHelper.read("100"));
        try {
            // wait for 10 seconds
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(dataStoreHelper.read("100"));
        System.out.println(dataStoreHelper.read("200"));


        //Deletes an element
        try {
            // wait for 10 seconds
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(dataStoreHelper.delete("100"));
        System.out.println(dataStoreHelper.delete("200"));
    }
}
