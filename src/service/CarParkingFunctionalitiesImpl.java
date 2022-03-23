package service;

import database.CarEntryExitTable;
import database.CarInParking;
import model.*;
import util.OrdinalNumber;

import java.util.ArrayList;

public class CarParkingFunctionalitiesImpl implements CarParkingFunctionalities {

    private final CarParking carParking;

    private final MultiFloorCarParking obj;
    private final ArrayList<ParkingLot> arr;

    public CarParkingFunctionalitiesImpl(MultiFloorCarParking obj, CarParking carParking){
        this.obj = obj;
        arr = this.obj.getParkingLots();
        this.carParking = carParking;
    }

    @Override
    public void generateReceipt(ParkingLot parkingLot, CarParkingPlace pos, Car car, ParkingCell parkingCell) {
        hashLine();
        carParking.parkACar(parkingLot,pos,car);

        carParking.generatePathToParkACar(parkingLot,pos);

        System.out.println("\nCar Parking Place : " + parkingCell.getPosition() + " at " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor");

        System.out.println("\nCar Number " + car.getCarNumber() + " parked successfully in " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    @Override
    public void generateBill(CarInParking carInParking, ParkingLot parkingLot, CarParkingPlace pos, Car car,
                             ParkingCell parkingCell, BillingFunctionalities billingFunctionalities) {
        hashLine();
        System.out.println("\nCar Parking Place : " + parkingCell.getPosition()  + " at " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor");

        CarExit carExit = new CarExit();
        parkingCell = carExit.exitACarFromPosition(carInParking,parkingLot,pos,car);

        System.out.println();
        System.out.println("Billing : ");
        System.out.println("---------");
        System.out.println(billingFunctionalities.generateBill(parkingCell,car).toString());

        carExit.generatePathToExitACar(obj,parkingLot,pos);

        System.out.println("\nCar Number " + car.getCarNumber() + " removed successfully from " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    @Override
    public void showAllParkingSlots() {
        hashLine();
        for (int i = obj.floors - 1; i >= 0; --i) {
            System.out.println("\nFloor Map of " + OrdinalNumber.getOrdinalNo(i) + " Floor");
            arr.get(i).showParkingLot(i == 0);
        }
        hashLine();
    }

    @Override
    public void showAllDetailedParkingSlots() {
        hashLine();
        for (int i = obj.floors - 1; i >= 0; --i) {
            System.out.println("\nDetailed Floor Map of " + OrdinalNumber.getOrdinalNo(i) + " Floor");
            arr.get(i).showModifiedParkingLot(i == 0);
        }
        hashLine();
    }

    @Override
    public void getCarInfoAndParkingHistory(Car car, DataPrinter dataPrinter, ParkingHistory parkingHistory) {
        hashLine();
        dataPrinter.showCarInformation(car);
        System.out.println("\nParking History:");
        if(!parkingHistory.showCarParkingHistory(car)) {
            System.out.println("No Parking History Available");
        }
        hashLine();
    }

    @Override
    public void getBillingHistoryByCarNumber(BillingFunctionalities billingFunctionalities, String carNo) {
        hashLine();
        System.out.println("\nBilling History:");
        System.out.println();
        ArrayList<BillingSystem> billings = billingFunctionalities.getBillingsByCarNo(carNo);
        for (BillingSystem billing:billings) {
            System.out.println(billing.toString());
            System.out.println();
        }
        hashLine();
    }


    // Utility Methods

    private void hashLine() {
        System.out.println();
        System.out.println("#".repeat(170));
    }

}