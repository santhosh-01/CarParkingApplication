package service;

import util.Validation;

import java.util.Scanner;

public class CarParkingMenu {

    static Scanner in = new Scanner(System.in);
    static CarParkingMessage app = new CarParkingMessage();
    public static void showMenu(CarParkingFunctionalities appFunctionalities) {
        while (true) {
            app.showMenu();
            String ch = in.nextLine().trim();
            int choice = Validation.validateInteger(ch,1,6);
            if(choice == 1) {
                appFunctionalities.parkACar();
            }
            else if(choice == 2) {
                appFunctionalities.exitACar();
            }
            else if(choice == 3) {
                appFunctionalities.showAllParkingSlots();
            }
            else if(choice == 4) {
                appFunctionalities.showAllDetailedParkingSlots();
            }
            else if(choice == 5) {
                appFunctionalities.getCarHistory();
            }
            else if(choice == 6) {
                break;
            }
        }
    }

}
