package app;

import model.PropertiesDataClass;
import service.CarParkingMenu;
import service.CarParkingMessage;
import setup.PropertiesClass;

public class MainClass {

    public static void main(String[] args) {

        CarParkingMessage message = new CarParkingMessage();
        PropertiesClass propertiesClass = new PropertiesClass("resources/config.properties");
        PropertiesDataClass propertiesDataClass = new PropertiesDataClass(propertiesClass.getFloors(),
                propertiesClass.getRows(),propertiesClass.getColumns(),propertiesClass.getPathWidth(),
                propertiesClass.getPath(),propertiesClass.getBillingAmountPerHour());
        CarParkingMenu carParkingMenu = new CarParkingMenu(propertiesDataClass);

        message.welcome();
        carParkingMenu.showMenu();
        message.quitMessage();

    }

}