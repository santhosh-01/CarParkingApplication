package app;

import model.PropertiesClass;
import service.CarParkingMenu;
import service.CarParkingMessage;

public class MainClass {

    public static void main(String[] args) {

        CarParkingMessage app = new CarParkingMessage();
        CarParkingMenu carParkingMenu = new CarParkingMenu(new PropertiesClass("resources/config.properties"));

        app.welcome();
        carParkingMenu.showMenu();
        app.quitMessage();

    }

}
