package service;

import model.*;
import util.OrdinalNumber;

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
        System.out.print("If you want to update car details choose the above option (1 or 2 or 3): ");
    }

    @Override
    public void CarParkingCancelledMessage() {
        System.out.println("\nYour Parking is Cancelled!");
    }

    @Override
    public void LastCarParkingDetails(int[] pos, CarEntryExit carEntryExit) {
        System.out.println("\nThis car was parked previously in the following parking place!");
        System.out.println("Last Car Parking Place: " + pos[0] + "/" + pos[1] + " at "
                + getOrdinalNumber(pos[2]) + " floor");
        System.out.println("Last Car Entry Time: " + getTime(carEntryExit.getEntryTime()));
        System.out.println("Last Car Exit Time: " + getTime(carEntryExit.getExitTime()));
    }

    @Override
    public void emptyCarParkingPlace(int[] position) {
        System.out.println("\nEmpty Car Parking is available in " + (position[0]+1) + "/" + (position[1]+1) +
                " on " + getOrdinalNumber(position[2]) + " floor");
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
        System.out.println("\nSorry! Now, Parking is Full in " + getOrdinalNumber(floor) + " floor!");
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
    public void generateReceipt(CarParking carParking, MultiFloorCarParking obj,
                                ParkingLot parkingLot, int[] pos, Car car) {
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
        System.out.println("If you want to move back to main menu, Enter 'back'");
    }

    @Override
    public void carParkingHistory(LocalTime time1, LocalTime time2, int[] pos, CarEntryExit carEntryExit) {
        if(time1 == null) System.out.print("00:00:00");
        else System.out.print(getTime(time1));
        System.out.print(" - ");
        if(time2 == null ) System.out.print("00:00:00");
        else System.out.print(getTime(time2));
        System.out.print(" - ");
        System.out.print(pos[0] + "/" + pos[1] + " in " + getOrdinalNumber(pos[2]) + " floor - ");
        Billing billing = carEntryExit.getBilling();
        System.out.printf("%.2f" + " " + Billing.moneyAbbr, billing.getBill());
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


    private String getTime(LocalTime time) {
        return time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }

    private String getOrdinalNumber(int floorNo) {
        if(floorNo == 0) return "Ground";
        else return OrdinalNumber.getOrdinalNo(floorNo);
    }

    private void hashLine() {
        System.out.println();
        System.out.println("#".repeat(170));
    }

}
