package setup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    private int floors;
    private int rows;
    private int columns;
    private int pathWidth;
    private String drivingSide;
    private double billingAmountPerHour;

    public AppProperties(String filePath) {
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
            try {
                floors = Integer.parseInt(prop.getProperty("noOfFloors"));
            }
            catch (NumberFormatException e) {
                floors = 1;
            }
            try {
                rows = Integer.parseInt(prop.getProperty("noOfRows"));
            }
            catch (NumberFormatException e) {
                rows = 1;
            }
            try {
                columns = Integer.parseInt(prop.getProperty("noOfColumns"));
            }
            catch (NumberFormatException e) {
                columns = 1;
            }
            try {
                pathWidth = Integer.parseInt(prop.getProperty("pathWidth"));
            }
            catch (NumberFormatException e) {
                pathWidth = 1;
            }
            drivingSide = prop.getProperty("drivingSide");
            if(drivingSide == null) drivingSide = "L";
            try {
                billingAmountPerHour = Integer.parseInt(prop.getProperty("billingAmountPerHour"));
            }
            catch (NumberFormatException e) {
                billingAmountPerHour = 100.0;
            }
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

    public String getDrivingSide() {
        return drivingSide;
    }

    public double getBillingAmountPerHour() {
        return billingAmountPerHour;
    }
}
