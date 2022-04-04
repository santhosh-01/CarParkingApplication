package app;

import navigator.CarParkingMenu;
import core.AppData;
import core.StringFormatter;
import setup.AppProperties;

public class Main {

    private static final int SCREEN_MAX_CHAR = 172;

    public static void main(String[] args) {

        System.out.println(StringFormatter.center(" Welcome to Car Parking Application ",SCREEN_MAX_CHAR,'*'));

        AppData appData = loadPropertyFileAndGetObject("src/resources/config.properties");

        CarParkingMenu carParkingMenu = new CarParkingMenu(appData);

        carParkingMenu.showMenu();

        System.out.println("\n" + StringFormatter.center(" Thank you for using the Application ",SCREEN_MAX_CHAR,'*'));

    }

    private static AppData loadPropertyFileAndGetObject(String filePath) {
        AppProperties appProperties = new AppProperties(filePath);
        return new AppData(appProperties.getFloors(),
                appProperties.getRows(),appProperties.getColumns(),appProperties.getPathWidth(),
                appProperties.getDrivingSide(),appProperties.getBillingAmountPerHour());
    }
}
