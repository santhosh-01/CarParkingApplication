package app;

import model.PropertiesClass;
import service.CarParkingFunctionalities;
import service.CarParkingFunctionalitiesImpl;
import service.CarParkingMenu;
import service.CarParkingMessage;

public class MainClass {

    public static void main(String[] args) {

        CarParkingMessage app = new CarParkingMessage();
        CarParkingFunctionalities appFunctionalities = new CarParkingFunctionalitiesImpl(
                new PropertiesClass("resources/config.properties"));

        app.welcome();
        CarParkingMenu.showMenu(appFunctionalities);
        app.quitMessage();

    }

}
