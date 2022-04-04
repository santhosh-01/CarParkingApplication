package impl;

import core.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class DataPrinterImpl implements DataPrinter {

    @Override
    public void carDetailsConfirmation(String carInfoUpdateMenu) {
        System.out.println(carInfoUpdateMenu);
    }

    @Override
    public void CarParkingCancelledMessage() {
        System.out.println("\nYour Parking is Cancelled!");
    }

    @Override
    public void LastCarParkingDetails(int carParkingSpotNumber, int floorNo, CarEntryExit carEntryExit) {
        System.out.println("\nThis car was parked previously in the following parking place!");
        System.out.println("Last Car Parking Place: " + carParkingSpotNumber + " at "
                + OrdinalNumber.getOrdinalNo(floorNo) + " floor");
        System.out.println("Last Car Entry Time: " + TimeFormat.getTime(carEntryExit.getEntryTime()));
        System.out.println("Last Car Exit Time: " + TimeFormat.getTime(carEntryExit.getExitTime()));
    }

    @Override
    public void emptyCarParkingPlace(int carParkingSpotNumber, int floorNo) {
        System.out.println("\nEmpty Car Parking is available in " + carParkingSpotNumber
                + " on " + OrdinalNumber.getOrdinalNo(floorNo) + " floor");
    }

    @Override
    public void parkingConfirmation() {
        System.out.print("Do you agree to proceed further with above parking location? (Yes / No) : ");
    }

    @Override
    public void yesOrNoInvalidMessage() {
        System.out.println("Invalid Input! Please Enter (Yes / No)");
    }

    @Override
    public void parkingIsFullInFloor(int floor) {
        System.out.println("\nSorry! Now, Parking is Full in " + OrdinalNumber.getOrdinalNo(floor) + " floor!");
    }

    @Override
    public void printParkingAvailableFloors(ArrayList<Integer> indexArray) {
        System.out.println();
        for (Integer j: indexArray) {
            if(j == 0) System.out.println("Floor " + (j) + " (Ground Floor)");
            else System.out.println("Floor " + (j));
        }
    }

    @Override
    public void invalidParkingPlace() {
        System.out.println("\nGiven Parking Place is Invalid. Please Enter valid Empty Parking Place!");
    }

    @Override
    public void noCarsAvailable() {
        System.out.println("\nSorry! No Cars are available to exit!");
    }

    @Override
    public void carNumberNotFound() {
        System.out.println("\nSorry! Given Car Number doesn't found! Please Enter valid car number");
    }

    @Override
    public void givenCarNotInParking() {
        System.out.println("\nSorry! Given Car is not in parking");
    }

    @Override
    public void askingBackToMainMenu() {
        System.out.println("If you want to move back to main menu, Enter 'b'");
    }

    @Override
    public void carParkingHistory(LocalTime time1, LocalTime time2, CarLocation pos, CarEntryExit carEntryExit) {
        if(time1 == null) System.out.print("00:00:00");
        else System.out.print(TimeFormat.getTime(time1));
        System.out.print(" - ");
        if(time2 == null ) System.out.print("00:00:00");
        else System.out.print(TimeFormat.getTime(time2));
        System.out.print(" - ");
        System.out.print( pos.getCarParkingSpot().getCarParkingSpotNumber() + " in " +
                OrdinalNumber.getOrdinalNo(pos.getFloorNo()) + " floor - ");
        BillingSystem billing = carEntryExit.getBilling();
        System.out.printf(" %s", billing.getBillingSummary());
        System.out.println();
    }

    @Override
    public void duplicateCarExist() {
        System.out.println("\nDuplicate Car! Given Car Number is already in parking");
    }

    @Override
    public void givenCarNumberEmpty() {
        System.out.println("\nGiven Car Number is Empty!");
    }

}
