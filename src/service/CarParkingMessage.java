package service;

import model.Car;
import model.ParkingLot;
import util.OrdinalNumber;

public class CarParkingMessage {

    public CarParkingMessage() {
    }

    public void welcome() {
        System.out.println("########## Welcome to Car Parking Application ##########");
    }

    public void showMenu() {
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

    public void showParkingPlace(ParkingLot parkingLot, int[] pos) {
        System.out.println("\nCar Parking Place : " + (pos[0] + 1) + "/" + (pos[1] + 1) + " at " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor");
    }

    public void showParkingSuccess(ParkingLot parkingLot, Car car) {
        System.out.println("\nCar Number " + car.getCarNumber() + " parked successfully in " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor ");
    }

    public void showCarExitSuccess(ParkingLot parkingLot, Car car) {
        System.out.println("\nCar Number " + car.getCarNumber() + " removed successfully from " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor ");
    }

    public void quitMessage() {
        System.out.println("\n########## Thank you for using the Application ##########");
    }

    private String getOrdinalNumber(int floorNo) {
        if(floorNo == 0) return "Ground";
        else return OrdinalNumber.getOrdinalNo(floorNo);
    }

}
