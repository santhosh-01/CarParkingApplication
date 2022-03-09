package service;

import model.Car;
import model.MultiFloorCarParking;
import model.ParkingLot;
import model.PropertiesClass;
import util.CarValidator;
import util.Validator;

import java.util.Scanner;

public class CarParkingMenu {

    private final Scanner in;
    private final CarParkingMessage msg;

    private final MultiFloorCarParking obj;
    private final CarParkingFunctionalities appFunctionalities;

    public CarParkingMenu(PropertiesClass prop) {
        in = new Scanner(System.in);
        msg = new CarParkingMessage();
        obj = new MultiFloorCarParking(prop);
        appFunctionalities = new CarParkingFunctionalitiesImpl(obj);
    }

    public void showMenu() {
        while (true) {
            msg.showMenu();
            String ch = in.nextLine().trim();
            int choice = Validator.validateInteger(ch,1,6);
            if(choice == 1) {
                Car car = appFunctionalities.acceptCarDetailsToPark();
                if(car == null) continue;
                if(CarValidator.checkDuplicateCarNoInParking(car.getCarNumber())) continue;
                if(!appFunctionalities.confirmCarDetails(car)) continue;
                if(appFunctionalities.checkAndSuggestCarParkingFloor(car)) continue;
                ParkingLot parkingLot = appFunctionalities.suggestAndGetCarParkingLot();
                int[] pos = appFunctionalities.suggestAndGetParkingPlace(parkingLot);
                appFunctionalities.generateReceipt(parkingLot,pos,car);
            }
            else if(choice == 2) {
                Car car = appFunctionalities.acceptCarDetailsToExit();
                if(car == null) continue;
                String carNo = car.getCarNumber();
                if(!appFunctionalities.confirmCarDetailsForExit(car)) continue;
                ParkingLot parkingLot = obj.getParkingLotWithCarNumber(carNo);
                int[] pos = parkingLot.getCarNumberPosition(carNo);
                appFunctionalities.generateBill(parkingLot,pos,car);
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

    private void hashLine() {
        System.out.println();
        System.out.println("#".repeat(170));
    }

}
