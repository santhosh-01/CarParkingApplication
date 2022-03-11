package service;

import model.*;
import util.Validator;

import java.util.Scanner;

public class CarParkingMenu {

    private final Scanner in;
    private final CarParkingMessage msg;

    private final MultiFloorCarParking obj;
    private final CarParkingFunctionalities appFunctionalities;
    private final DataPrinter dataPrinter;
    private final CarParking carParking;

    public CarParkingMenu(PropertiesClass prop) {
        in = new Scanner(System.in);
        msg = new CarParkingMessage();
        DataProvider dataProvider = new DataProviderImpl();
        dataPrinter = new DataPrinterImpl();
        obj = new MultiFloorCarParking(prop);

        carParking = new CarParkingImpl(obj, dataProvider,dataPrinter);
        appFunctionalities = new CarParkingFunctionalitiesImpl(obj, dataProvider,dataPrinter,carParking);
    }

    public void showMenu() {
        while (true) {
            msg.showMenu();
            String ch = in.nextLine().trim();
            int choice = Validator.validateInteger(ch,1,6);
            if(choice == 1) {
                if (!obj.isParkingAvailable()) {
                    System.out.println("\nSorry! Parking Full!");
                    continue;
                }
                Car car = carParking.acceptCarDetailsToPark();
                if(car == null) continue;
                if(carParking.checkDuplicateCarNoInParking(car.getCarNumber())) continue;
                if(!carParking.confirmCarDetails(car)) continue;
                if(carParking.checkAndSuggestLastCarParkingPlace(car)) continue;
                ParkingLot parkingLot = carParking.suggestAndGetCarParkingLot();
                int[] pos = carParking.suggestAndGetParkingPlace(parkingLot);
                appFunctionalities.generateReceipt(parkingLot,pos,car);
            }
            else if(choice == 2) {
                Car car = carParking.acceptCarDetailsToExit();
                if(car == null) continue;
                String carNo = car.getCarNumber();
                if(!carParking.confirmCarDetailsForExit(car)) continue;
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
                Car car = carParking.getValidCarInParkingHistory();
                if(car == null) continue;
                appFunctionalities.getCarInfoAndParkingHistory(car,dataPrinter);
            }
            else if(choice == 6) {
                String carNo = carParking.getValidCarNumberInParkingHistory();
                if(carNo == null) continue;
                appFunctionalities.getBillingHistoryByCarNumber(carNo);
            }
            else if(choice == 7){
                break;
            }
        }
    }

}
