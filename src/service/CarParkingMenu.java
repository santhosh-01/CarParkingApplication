package service;

import database.CarEntryExitTable;
import database.CarInParking;
import database.CarTable;
import model.*;
import util.Validator;

import java.util.Scanner;

public class CarParkingMenu {

    private final Scanner in;
    private final CarParkingMessage message;

    private final CarInParking carInParking;

    private final MultiFloorCarParking multiFloorCarParking;
    private final CarParkingFunctionalities appFunctionalities;
    private final DataPrinter dataPrinter;
    private final CarParking carParking;

    public CarParkingMenu(PropertiesDataClass prop) {
        in = new Scanner(System.in);

        CarTable carTable = new CarTable();
        carInParking = new CarInParking();
        CarEntryExitTable carEntryExitTable = new CarEntryExitTable();

        message = new CarParkingMessage();
        DataProvider dataProvider = new DataProviderImpl();
        dataPrinter = new DataPrinterImpl();
        multiFloorCarParking = new MultiFloorCarParking(prop);

        carParking = new CarParkingImpl(multiFloorCarParking, dataProvider,dataPrinter, carTable, carInParking, carEntryExitTable);
        appFunctionalities = new CarParkingFunctionalitiesImpl(multiFloorCarParking,carParking);
    }

    public void showMenu() {
        while (true) {
            message.showMenu();
            String ch = in.nextLine().trim();
            int choice = Validator.validateInteger(ch,1,7);
            if(choice == -1) continue;
            if(choice == 1) {
                if (!multiFloorCarParking.isParkingAvailable()) {
                    System.out.println("\nSorry! Parking Full!");
                    continue;
                }
                Car car = carParking.acceptCarDetailsToPark();
                if(car == null) continue;
                if(carParking.checkDuplicateCarNoInParking(car.getCarNumber())) continue;
                if(!carParking.confirmCarDetails(car)) continue;
                CarLocation carLocation = carParking.checkAndSuggestLastCarParkingPlace(car);
                if(carLocation != null) {
                    ParkingLot parkingLot = multiFloorCarParking.getParkingLots().get(carLocation.getFloorNo());
                    CarParkingPlace pos = carLocation.getCarParkingPlace();
                    ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos.getRow(),pos.getCol());
                    appFunctionalities.generateReceipt(parkingLot,carLocation.getCarParkingPlace(),car,parkingCell);
                    continue;
                }
                ParkingLot parkingLot = carParking.suggestAndGetCarParkingLot();
                CarParkingPlace pos = carParking.suggestAndGetParkingPlace(parkingLot);
                ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos.getRow(),pos.getCol());
                carLocation = new CarLocation(pos,parkingLot.getFloorNo());
                appFunctionalities.generateReceipt(parkingLot,carLocation.getCarParkingPlace(),car,parkingCell);
            }
            else if(choice == 2) {
                Car car = carParking.acceptCarDetailsToExit();
                if(car == null) continue;
                String carNo = car.getCarNumber();
                if(!carParking.confirmCarDetailsForExit(car)) continue;
                ParkingLot parkingLot = multiFloorCarParking.getParkingLotWithCarNumber(carNo);
                CarParkingPlace pos = parkingLot.getCarNumberPosition(carNo);
                ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos.getRow(),pos.getCol());
                appFunctionalities.generateBill(carInParking,parkingLot,pos,car,parkingCell);
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
