package service;

import model.*;
import util.OrdinalNumber;

import java.util.ArrayList;
import java.util.Scanner;

public class CarParkingFunctionalitiesImpl implements CarParkingFunctionalities {

    private final Scanner in;
    private final CarParking carParking;

    private final MultiFloorCarParking obj;
    private final ArrayList<ParkingLot> arr;

    public CarParkingFunctionalitiesImpl(MultiFloorCarParking obj, CarParking carParking){
        in = new Scanner(System.in);
        this.obj = obj;
        arr = this.obj.getParkingLots();
        this.carParking = carParking;
    }

    @Override
    public void generateReceipt(ParkingLot parkingLot, int[] pos, Car car) {
        hashLine();
        carParking.parkACar(parkingLot,pos,car);

        carParking.generatePathToParkACar(parkingLot,pos);

        System.out.println("\nCar Parking Place : " + (pos[0] + 1) + "/" + (pos[1] + 1) + " at " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor");

        System.out.println("\nCar Number " + car.getCarNumber() + " parked successfully in " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor ");
        hashLine();
    }

    @Override
    public void generateBill(ParkingLot parkingLot, int[] pos, Car car) {
        hashLine();
        System.out.println("\nCar Position : " + (pos[0] + 1) + "/" + (pos[1] + 1) + " at " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor");

        ParkingCell parkingCell = carParking.exitACarFromPosition(parkingLot,pos,car);

        long seconds = carParking.calculateParkingTimeInSeconds(parkingCell);
        System.out.println("\nTotal Car Parking Time: " + seconds + " seconds");

        double bill = carParking.generateBill(parkingCell,car,seconds);
        System.out.printf("\nBill Amount for Parking: %.2f " + Billing.moneyAbbr + "\n", bill);

        carParking.generatePathToExitACar(parkingLot,pos);

        System.out.println("\nCar Number " + car.getCarNumber() + " removed successfully from " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    @Override
    public void showAllParkingSlots() {
        while (true) {
            hashLine();
            for (int i = obj.floors - 1; i >= 0; --i) {
                System.out.println("\nFloor Map of " + getOrdinalNumber(i) + " Floor");
                arr.get(i).showParkingLot();
            }
            hashLine();
            System.out.print("\nIf you want to move back to main menu, Enter 'back' : ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("back")) break;
            else System.out.print("\nPlease Enter Valid Input : ");
        }
    }

    @Override
    public void showAllDetailedParkingSlots() {
        while (true) {
            hashLine();
            for (int i = obj.floors - 1; i >= 0; --i) {
                System.out.println("\nDetailed Floor Map of " + getOrdinalNumber(i) + " Floor");
                arr.get(i).showModifiedParkingLot(i == 0);
            }
            hashLine();
            System.out.print("\nIf you want to move back to main menu, Enter 'back' : ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("back")) break;
            else System.out.print("\nPlease Enter Valid Input : ");
        }
    }

    @Override
    public void getCarInfoAndParkingHistory(Car car, DataPrinter dataPrinter) {
        hashLine();
        dataPrinter.showCarInformation(car);
        carParking.showCarParkingHistory(car);
        hashLine();
    }


    // Utility Methods

    private String getOrdinalNumber(int floorNo) {
        if(floorNo == 0) return "Ground";
        else return OrdinalNumber.getOrdinalNo(floorNo);
    }

    private void hashLine() {
        System.out.println();
        System.out.println("#".repeat(170));
    }

}