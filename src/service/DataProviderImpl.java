package service;

import model.BillingSystem;
import model.Car;
import model.ParkingLot;
import util.OrdinalNumber;

import java.util.Scanner;

public class DataProviderImpl implements DataProvider{

    private final Scanner in = new Scanner(System.in);

    @Override
    public boolean billingAmountAcceptance() {
        while (true) {
            System.out.println("\nBilling Amount for parking a car: " + BillingSystem.billingAmountPerHour
                    + " " + BillingSystem.moneyAbbr + " per hour");
            System.out.print("Are you going to park a car? (Yes / No): ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("yes")) return true;
            else if(choice.equalsIgnoreCase("no")) return false;
            else if(choice.equals("")) System.out.println("\nYou have not entered any input! Please Enter (Yes / No)");
            else System.out.println("\nInvalid Input! Please Enter (Yes / No)");
        }
    }

    @Override
    public String getCarModel() {
        String carModel;
        System.out.print("Enter Car Model Number: ");
        carModel = in.nextLine().trim();
        if(carModel.equals("")) {
            System.out.println("\nYou have not entered any input! " +
                               "Please enter valid Car Model");
            return null;
        }
        else {
            return carModel;
        }
    }

    @Override
    public String getCarBrand() {
        String carBrand;
        System.out.print("Enter Car Brand: ");
        carBrand = in.nextLine().trim();
        if(carBrand.equals("")) {
            System.out.println("\nYou have not entered any input! " +
                               "Please enter valid Car Brand");
            return null;
        }
        else {
            return carBrand;
        }
    }

    @Override
    public String getCarNumber() {
        String carNo;
        System.out.print("\nEnter Car Number(should be less than or equal to 5 characters): ");
        carNo = in.nextLine().trim();
        if(carNo.equals("")) {
            System.out.println("\nYou have not entered any input! Please enter valid Car Number");
        }
        else if(carNo.length() > 5) {
            System.out.println("\n" + "You have entered more than 5 characters as Car Number.");
            System.out.println("Please Enter valid Car Number(should be less than or equal to 5 characters): ");
        }
        else {
            return carNo;
        }
        return null;
    }

    @Override
    public String getCarConfirmation() {
        System.out.println("If you want to update car details choose the above option (1 or 2 or 3) ");
        System.out.print("Enter your choice: ");
        return in.nextLine().trim();
    }

    @Override
    public String getLastCarParkingConfirmation() {
        return in.nextLine().trim();
    }

    @Override
    public String getSuggestedParkingFloorConfirmation(int floorNo) {
        System.out.println("\nParking Place is Available in " + OrdinalNumber.getOrdinalNo(floorNo) + " floor");
        System.out.print("Do you want to proceed further? (Yes / No): ");
        return in.nextLine().trim();
    }

    @Override
    public String getSuggestedParkingPlaceConfirmation(int position) {
        System.out.println("\nEmpty Parking Place is available at " + position);
        System.out.print("Do you agree to proceed further with above parking location? (Yes / No) ");
        return in.nextLine().trim();
    }

    @Override
    public int getCarParkingPlace(ParkingLot parkingLot) {
        System.out.println("\nDetailed Floor Map of " + OrdinalNumber.getOrdinalNo(parkingLot.getFloorNo()) + " Floor");
        parkingLot.showModifiedParkingLot(true);
        System.out.print("Select any one Empty Parking Place : ");
        return Integer.parseInt(in.nextLine().trim());
    }

    @Override
    public String getCarNumberToExit() {
        System.out.print("\nEnter the Car Number to exit ['b' for Main menu]: ");
        return in.nextLine().trim();
    }

    @Override
    public String givenCarConfirmation(Car car) {
        System.out.println("\nCar Number: " + car.getCarNumber());
        System.out.println("Car Brand: " + car.getCarBrand());
        System.out.println("Car Model Number: " + car.getCarModel());
        System.out.print("Do you want the car with above details to exit? (Yes / No) ");
        return in.nextLine().trim();
    }

    @Override
    public String getBackChoice() {
        System.out.println("If you want to move back to main menu, Enter 'b'");
        return in.nextLine().trim();
    }

    @Override
    public String getCarNumberForHistory() {
        System.out.print("\nEnter Car Number to fetch history ['b' for Main menu]: ");
        return in.nextLine();
    }

    @Override
    public String getFloorNumber() {
        System.out.print("Select any of the above floor number: ");
        return in.nextLine().trim();
    }
}
