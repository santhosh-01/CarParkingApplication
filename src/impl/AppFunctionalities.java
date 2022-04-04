package impl;

import core.*;

import java.util.ArrayList;

public class AppFunctionalities {

    private final CarParking carParking;

    private final MultiFloorCarParking obj;
    private final ArrayList<ParkingLot> arr;

    public AppFunctionalities(MultiFloorCarParking obj, CarParking carParking){
        this.obj = obj;
        arr = this.obj.getParkingLots();
        this.carParking = carParking;
    }

    public void generateReceipt(ParkingLot parkingLot, CarParkingSpot pos, Car car, ParkingCell parkingCell) {
        hashLine();
        carParking.parkACar(parkingLot,pos,car);

        carParking.generatePathToParkACar(parkingLot,pos);

        System.out.println("\nCar Parking Place : " + parkingCell.getParkingSpotNumber() + " at " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor");

        System.out.println("\nCar Number " + car.getCarNumber() + " parked successfully in " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    public void generateBill(CarInParking carInParking, ParkingLot parkingLot, CarParkingSpot pos, Car car,
                             ParkingCell parkingCell ,ParkingHistory parkingHistory) {
        hashLine();
        System.out.println("\nCar Parking Place : " + parkingCell.getParkingSpotNumber()  + " at " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor");

        CarExit carExit = new CarExit();

        carExit.generatePathToExitACar(obj,parkingLot,pos);

        parkingCell = carExit.exitACarFromPosition(carInParking,parkingLot,pos,car);

        CarEntryExit carEntryExit = parkingHistory.getLastCarEntryExitByCar(parkingCell,car);

        System.out.println();
        System.out.println("Billing : ");
        System.out.println("---------");
        System.out.println(carExit.generateBill(carEntryExit,parkingCell,car).toString());

        System.out.println("\nCar Number " + car.getCarNumber() + " removed successfully from " +
                OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    public void showAllParkingSlots() {
        hashLine();
        for (int i = obj.getFloors() - 1; i >= 0; --i) {
            System.out.println("\nFloor Map of " + OrdinalNumber.getOrdinalNo(i) + " Floor\n");
            System.out.println(arr.get(i).getParkingLotMap(i == 0));
        }
        hashLine();
    }

    public void showAllDetailedParkingSlots() {
        hashLine();
        for (int i = obj.getFloors() - 1; i >= 0; --i) {
            System.out.println("\nDetailed Floor Map of " + OrdinalNumber.getOrdinalNo(i) + " Floor\n");
            System.out.println(arr.get(i).getModifiedParkingLotMap(i == 0));
        }
        hashLine();
    }

    public void getCarInfoAndParkingHistory(Car car, ParkingHistory parkingHistory) {
        hashLine();
        showCarInformation(car);
        System.out.println("\nParking History:");
        if(!parkingHistory.showCarParkingHistory(car)) {
            System.out.println("No Parking History Available");
        }
        hashLine();
    }

    private void showCarInformation(Car car) {
        System.out.println("\nCar Information:");
        System.out.println("Car Number: " + car.getCarNumber());
        System.out.println("Car Brand: " + car.getCarBrand());
        System.out.println("Car Model Number: " + car.getCarModel());
    }

    public void getBillingHistoryByCarNumber(ParkingHistory parkingHistory, String carNo) {
        hashLine();
        System.out.println("\nBilling History:");
        ArrayList<BillingSystem> billings = parkingHistory.getBillingsByCarNo(carNo);
        for (BillingSystem billing:billings) {
            System.out.println();
            System.out.println(billing.toString());
        }
        hashLine();
    }


    // Utility Methods

    private void hashLine() {
        System.out.println();
        System.out.println(StringFormatter.getCharLine('#',172));
    }

}