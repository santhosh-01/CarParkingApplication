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
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor");

        System.out.println("\nCar Number " + car.getCarNumber() + " parked successfully in " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    @Override
    public void generateBill(ParkingLot parkingLot, int[] pos, Car car) {
        hashLine();
        System.out.println("\nCar Parking Place : " + (pos[0] + 1) + "/" + (pos[1] + 1) + " at " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor");

        ParkingCell parkingCell = carParking.exitACarFromPosition(parkingLot,pos,car);

        System.out.println();
        System.out.println(carParking.generateBill(parkingCell,car).toString());

        carParking.generatePathToExitACar(parkingLot,pos);

        System.out.println("\nCar Number " + car.getCarNumber() + " removed successfully from " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    @Override
    public void showAllParkingSlots() {
        while (true) {
            hashLine();
            for (int i = obj.floors - 1; i >= 0; --i) {
                System.out.println("\nFloor Map of " + OrdinalNumber.getOrdinalNo(i) + " Floor");
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
                System.out.println("\nDetailed Floor Map of " + OrdinalNumber.getOrdinalNo(i) + " Floor");
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
        System.out.println("\nParking History:");
        if(!carParking.showCarParkingHistory(car)) {
            System.out.println("No Parking History Available");
        }
        hashLine();
    }

    @Override
    public void getBillingHistoryByCarNumber(String carNo) {
        hashLine();
        System.out.println("\nBilling History:");
        System.out.println();
        ArrayList<Billing> billings = carParking.getBillingsByCarNo(carNo);
        for (Billing billing:billings) {
            System.out.println(billing.toString());
        }
        hashLine();
    }


    // Utility Methods

    private void hashLine() {
        System.out.println();
        System.out.println("#".repeat(170));
    }

}