package navigator;

import impl.AppFunctionalities;
import impl.DataPrinterImpl;
import impl.DataProviderImpl;
import core.*;

import java.util.Scanner;

public class CarParkingMenu {

    private final Scanner in;

    private final CarInParking carInParking;

    private final MultiFloorCarParking multiFloorCarParking;
    private final AppFunctionalities appFunctionalities;
    private final ParkingHistory parkingHistory;
    private final DataHandler dataHandler;
    private final CarParking carParking;

    public CarParkingMenu(AppData prop) {
        in = new Scanner(System.in);

        CarTable carTable = new CarTable();
        carInParking = new CarInParking();
        CarEntryExitTable carEntryExitTable = new CarEntryExitTable();

        DataProvider dataProvider = new DataProviderImpl();
        DataPrinter dataPrinter = new DataPrinterImpl();
        multiFloorCarParking = new MultiFloorCarParking(prop);
        parkingHistory = new ParkingHistory(carTable,dataProvider, dataPrinter,carEntryExitTable);

        dataHandler = new DataHandler(multiFloorCarParking,dataProvider, dataPrinter,carTable,carInParking,carEntryExitTable);
        carParking = new CarParking(multiFloorCarParking, dataProvider, dataPrinter, carInParking, carEntryExitTable);
        appFunctionalities = new AppFunctionalities(multiFloorCarParking,carParking);
    }

    public void showMenu() {
        while (true) {
            getMenu();
            String ch = in.nextLine().trim();
            int choice = Validator.validateInteger(ch,1,7);
            if(choice == -1) continue;
            if(choice == 1) {
                if (!multiFloorCarParking.isParkingAvailable()) {
                    System.out.println("\nSorry! Parking Full!");
                    continue;
                }
                Car car = dataHandler.acceptCarDetailsToPark();
                if(car == null) continue;
                if(carParking.checkDuplicateCarNoInParking(car.getCarNumber())) continue;
                if(!dataHandler.confirmCarDetails(car)) continue;
                CarLocation carLocation = carParking.checkAndSuggestLastCarParkingPlace(car);
                if(carLocation != null) {
                    ParkingLot parkingLot = multiFloorCarParking.getParkingLots().get(carLocation.getFloorNo());
                    CarParkingSpot pos = carLocation.getCarParkingSpot();
                    ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos.getRow(),pos.getCol());
                    appFunctionalities.generateReceipt(parkingLot,carLocation.getCarParkingSpot(),car,parkingCell);
                    continue;
                }
                ParkingLot parkingLot = carParking.suggestAndGetCarParkingLot();
                CarParkingSpot pos = carParking.suggestAndGetParkingSpot(parkingLot);
                ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos.getRow(),pos.getCol());
                appFunctionalities.generateReceipt(parkingLot,pos,car,parkingCell);
            }
            else if(choice == 2) {
                Car car = dataHandler.acceptCarDetailsToExit();
                if(car == null) continue;
                String carNo = car.getCarNumber();
                if(!dataHandler.confirmCarDetailsForExit(car)) continue;
                ParkingLot parkingLot = multiFloorCarParking.getParkingLotWithCarNumber(carNo);
                CarParkingSpot pos = parkingLot.getCarNumberParkingSpot(carNo);
                ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos.getRow(),pos.getCol());
                appFunctionalities.generateBill(carInParking,parkingLot,pos,car,parkingCell,parkingHistory);
            }
            else if(choice == 3) {
                appFunctionalities.showAllParkingSlots();
            }
            else if(choice == 4) {
                appFunctionalities.showAllDetailedParkingSlots();
            }
            else if(choice == 5) {
                Car car = parkingHistory.getValidCarInParkingHistory();
                if(car == null) continue;
                appFunctionalities.getCarInfoAndParkingHistory(car,parkingHistory);
            }
            else if(choice == 6) {
                String carNo = parkingHistory.getValidCarNumberInParkingHistory();
                if(carNo == null) continue;
                appFunctionalities.getBillingHistoryByCarNumber(parkingHistory, carNo);
            }
            else if(choice == 7){
                break;
            }
        }
    }

    private void getMenu() {
        System.out.println("\nMenu");
        System.out.println("1. Entry a Car");
        System.out.println("2. Exit the Car");
        System.out.println("3. Show Floor Maps");
        System.out.println("4. Show Detailed Floor Maps");
        System.out.println("5. Car History");
        System.out.println("6. Billing History");
        System.out.println("7. Quit Application");
        System.out.print("Please Choose any of the above option: ");
    }

}
