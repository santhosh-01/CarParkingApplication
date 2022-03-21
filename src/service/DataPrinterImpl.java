package service;

import model.*;
import util.OrdinalNumber;
import util.TimeFormat;

import java.time.LocalTime;
import java.util.ArrayList;

public class DataPrinterImpl implements DataPrinter {

    @Override
    public void showCarDetails(String carNo, String carBrand, String carModel) {
        System.out.println("\n1. Car Number: " + carNo);
        System.out.println("2. Car Brand: " + carBrand);
        System.out.println("3. Car Model Number: " + carModel);
        System.out.println("4. Continue Parking");
        System.out.println("5. Cancel Parking");
    }

    @Override
    public void CarParkingCancelledMessage() {
        System.out.println("\nYour Parking is Cancelled!");
    }

    @Override
    public void LastCarParkingDetails(CarLocation pos, CarEntryExit carEntryExit) {
        System.out.println("\nThis car was parked previously in the following parking place!");
        System.out.println("Last Car Parking Place: " + pos.getCarParkingPlace().getRow() + "/"
                + pos.getCarParkingPlace().getCol() + " at "
                + OrdinalNumber.getOrdinalNo(pos.getFloorNo()) + " floor");
        System.out.println("Last Car Entry Time: " + TimeFormat.getTime(carEntryExit.getEntryTime()));
        System.out.println("Last Car Exit Time: " + TimeFormat.getTime(carEntryExit.getExitTime()));
    }

    @Override
    public void emptyCarParkingPlace(CarLocation position) {
        System.out.println("\nEmpty Car Parking is available in " + (position.getCarParkingPlace().getRow()+1)
                + "/" + (position.getCarParkingPlace().getCol()+1) + " on "
                + OrdinalNumber.getOrdinalNo(position.getFloorNo()) + " floor");
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
    public void printParkingAvailableFloors(MultiFloorCarParking obj) {
        ArrayList<Integer> indexArray = obj.getParkingAvailableFloors();
        System.out.println();
        for (Integer j: indexArray) {
            if(j == 0) System.out.println("Floor " + (j) + " (Ground Floor)");
            else System.out.println("Floor " + (j));
        }
    }

    @Override
    public void askCorrectFormatOfParkingPlace() {
        System.out.println("\nPlease Enter correct format (R/C)");
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
        System.out.print( pos.getCarParkingPlace().getCarParkingSpotNumber() + " in " + OrdinalNumber.getOrdinalNo(pos.getFloorNo()) + " floor - ");
        BillingSystem billing = carEntryExit.getBilling();
        System.out.printf("%.2f" + " " + BillingSystem.moneyAbbr, billing.getBill());
        System.out.println();
    }

    @Override
    public void showCarInformation(Car car) {
        String carNo = car.getCarNumber();
        String carBrand = car.getCarBrand();
        String carModel = car.getCarModel();
        System.out.println("\nCar Information:");
        System.out.println("Car Number: " + carNo);
        System.out.println("Car Brand: " + carBrand);
        System.out.println("Car Model Number: " + carModel);
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
