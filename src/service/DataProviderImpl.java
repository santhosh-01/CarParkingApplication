package service;

import model.Billing;
import model.Car;
import model.ParkingLot;
import util.OrdinalNumber;

import java.util.Scanner;

public class DataProviderImpl implements DataProvider{

    private final Scanner in = new Scanner(System.in);

    @Override
    public boolean acceptBillingAmount() {
        while (true) {
            System.out.println("\nBilling Amount for parking a car: " + Billing.billingAmountPerHour
                    + " " + Billing.moneyAbbr + " per hour");
            System.out.print("Are you going to park a car? (Yes / No): ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("yes")) return true;
            else if(choice.equalsIgnoreCase("no")) return false;
            else if(choice.equals("")) System.out.println("\nYou have not entered any input! Please Enter (Yes / No)");
            else System.out.println("\nInvalid Input! Please Enter (Yes / No)");
        }
    }

    @Override
    public String validateAndGetCarModel() {
        String carModel;
        while (true) {
            System.out.print("Enter Car Model Number: ");
            carModel = in.nextLine().trim();
            if(carModel.equals("")) System.out.println("\nYou have not entered any input! " +
                    "Please enter valid Car Model");
            else break;
        }
        return carModel;
    }

    @Override
    public String validateAndGetCarBrand() {
        String carBrand;
        while (true) {
            System.out.print("Enter Car Brand: ");
            carBrand = in.nextLine().trim();
            if(carBrand.equals("")) System.out.println("\nYou have not entered any input! " +
                    "Please enter valid Car Brand");
            else break;
        }
        return carBrand;
    }

    @Override
    public String validateAndGetCarNumber() {
        String carNo;
        while (true) {
            System.out.print("\nEnter Car Number(should be less than or equal to 5 characters): ");
            carNo = in.nextLine().trim();
            if(carNo.equals("")) System.out.println("\nYou have not entered any input! Please enter valid Car Number");
            else if(carNo.length() > 5) System.out.println("""

                    You have entered more than 5 characters as Car Number.
                    Please Enter valid Car Number(should be less than or equal to 5 characters):\s""");
            else break;
        }
        return carNo;
    }

    @Override
    public String getCarConfirmation() {
        return in.nextLine().trim();
    }

    @Override
    public String getLastCarParkingConfirmation() {
        return in.nextLine().trim();
    }

    @Override
    public String getSuggestedParkingFloorConfirmation(int floorNo) {
        System.out.println("\nParking Place is Available in " + getOrdinalNumber(floorNo) + " floor");
        System.out.print("Do you want to proceed further? (Yes / No): ");
        return in.nextLine().trim();
    }

    @Override
    public String getSuggestedParkingPlaceConfirmation(int[] position) {
        System.out.println("\nEmpty Parking Place is available at " +
                (position[0] + 1) + "/" + (position[1] + 1));
        System.out.print("Do you agree to proceed further with above parking location? (Yes / No) ");
        return in.nextLine().trim();
    }

    @Override
    public String getCarParkingPlace(ParkingLot parkingLot) {
        System.out.println("\nDetailed Floor Map of " + getOrdinalNumber(parkingLot.getFloorNo()) + " Floor");
        parkingLot.showModifiedParkingLot(true);
        System.out.print("Select any one Empty Parking Place in (R/C) format: ");
        return in.nextLine().trim();
    }

    @Override
    public String getCarNumberToExit() {
        System.out.print("\nEnter the Car Number to exit ['back' for Main menu]: ");
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
        System.out.println("If you want to move back to main menu, Enter 'back'");
        return in.nextLine().trim();
    }

    @Override
    public String getCarNumberForCarHistory() {
        System.out.print("\nEnter Car Number to fetch history ['back' for Main menu]: ");
        return in.nextLine();
    }

    @Override
    public String getFloorNumber() {
        System.out.print("Select any of the above floor number: ");
        return in.nextLine().trim();
    }

    private String getOrdinalNumber(int floorNo) {
        if(floorNo == 0) return "Ground";
        else return OrdinalNumber.getOrdinalNo(floorNo);
    }
}