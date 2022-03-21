package setup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesClass {

    private int floors;
    private int rows;
    private int columns;
    private int pathWidth;
    private String path;
    private double billingAmountPerHour;

    public PropertiesClass(String filePath) {
        setPropertiesFromFile(filePath);
    }

    private void setPropertiesFromFile(String filePath) {
        FileInputStream file;
        Properties prop = null;
        try {
            file = new FileInputStream(filePath);
            prop = new Properties();
            prop.load(file);
        } catch (FileNotFoundException e) {
            System.out.println("Given Properties File Path is Invalid!");
        } catch (IOException e) {
            System.out.println("Property File is not loaded!");
        }
        if(prop != null) {
            floors = Integer.parseInt(prop.getProperty("noOfFloors"));
            rows = Integer.parseInt(prop.getProperty("noOfRows"));
            columns = Integer.parseInt(prop.getProperty("noOfColumns"));
            pathWidth = Integer.parseInt(prop.getProperty("pathWidth"));
            path = prop.getProperty("path");
            billingAmountPerHour = Integer.parseInt(prop.getProperty("billingAmountPerHour"));
        }
    }

    public int getFloors() {
        return floors;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public String getPath() {
        return path;
    }

    public double getBillingAmountPerHour() {
        return billingAmountPerHour;
    }
}
