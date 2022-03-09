package service;

import database.CarEntryExitTable;
import database.CarInParking;
import database.CarTable;
import model.*;
import util.OrdinalNumber;
import util.Validator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class CarParkingFunctionalitiesImpl implements CarParkingFunctionalities {

    private final Scanner in;
    private final CarParking carParking;

    private final MultiFloorCarParking obj;
    private final ArrayList<ParkingLot> arr;

    private final CarEntryExitTable carEntryExitTable;
    private final CarTable carTable;
    private final CarInParking carInParking;

    public CarParkingFunctionalitiesImpl(MultiFloorCarParking obj){
        in = new Scanner(System.in);
        this.obj = obj;
        arr = this.obj.getParkingLots();
        carParking = new CarParkingImpl();
        carTable = new CarTable();
        carInParking = new CarInParking();
        carEntryExitTable = new CarEntryExitTable();
    }

    @Override
    public Car acceptCarDetailsToPark() {
        if (!obj.isParkingAvailable()) {
            System.out.println("\nSorry! Parking Full!");
            return null;
        }
        if (!acceptBillingAmount()) return null;
        String carNo = validateAndGetCarNumber();
        String carBrand, carModel;
        Car car;
        if(carTable.isCarNumberExist(carNo)) {
            car = carTable.getCarByCarNo(carNo);
        }
        else {
            carBrand = validateAndGetCarBrand();
            carModel = validateAndGetCarModel();
            car = carParking.createCar(carNo,carBrand,carModel);
        }
        return car;
    }

    private boolean acceptBillingAmount() {
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

    private String validateAndGetCarModel() {
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

    private String validateAndGetCarBrand() {
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

    private String validateAndGetCarNumber() {
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
    public boolean confirmCarDetails(Car car) {
        while (true) {
            String carNo = car.getCarNumber();
            String carBrand = car.getCarBrand();
            String carModel = car.getCarModel();
            System.out.println("\n1. Car Number: " + carNo);
            System.out.println("2. Car Brand: " + carBrand);
            System.out.println("3. Car Model Number: " + carModel);
            System.out.println("4. Continue Parking");
            System.out.println("5. Cancel Parking");
            System.out.print("If you want to update car details choose the above option (1 or 2 or 3): ");
            String ch = in.nextLine().trim();
            int choice = Validator.validateInteger(ch,1,5);
            if(choice == -1) continue;
            if(choice == 1) {
                car.setCarNumber(validateAndGetCarNumber());
            }
            else if(choice == 2) {
                car.setCarBrand(validateAndGetCarBrand());
            }
            else if(choice == 3) {
                car.setCarModel(validateAndGetCarModel());
            }
            else if(choice == 4) break;
            else if(choice == 5) {
                System.out.println("\nYour Parking is Cancelled!");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkAndSuggestCarParkingFloor(Car car) {
        CarEntryExit carEntryExit = carParking.checkAndGetLastCarParkingPlace(car);
        if(carEntryExit == null) return false;
        int[] pos = carEntryExit.getPosition();
        ParkingLot parkingLot = arr.get(pos[2]);
        System.out.println("\nThis car was parked previously in the following parking place!");
        System.out.println("Last Car Parking Place: " + pos[0] + "/" + pos[1] + " at "
                + getOrdinalNumber(pos[2]) + " floor");
        System.out.println("Last Car Entry Time: " + getTime(carEntryExit.getEntryTime()));
        System.out.println("Last Car Exit Time: " + getTime(carEntryExit.getExitTime()));
        int[] position = new int[3];
        int[] position1 = parkingLot.getNearestParkingPosition(pos[0]-1,pos[1]-1);
        position[0] = position1[0];
        position[1] = position1[1];
        position[2] = pos[2];
        if(position[0] != -1 && pos[1] != -1) {
            System.out.println("\nEmpty Car Parking is available in " + (position[0]+1) + "/" + (position[1]+1) +
                    " on " + getOrdinalNumber(pos[2]) + " floor");
            System.out.print("Do you agree to proceed further with above parking location? ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("yes")) {
                carParking.parkACar(obj,parkingLot,position,car);
                return true;
            }
        }
        else {
            System.out.println("\nBut Sorry! Other cars parked in nearby position already!");
        }
        return false;
    }

    @Override
    public ParkingLot suggestAndGetCarParkingLot() {
        int ind = obj.getLowestFloorWithVacancy();
        while (true) {
            System.out.println("\nParking Place is Available in " + getOrdinalNumber(ind) + " floor");
            System.out.print("Do you want to proceed further? (Yes / No): ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("yes")) {
                return arr.get(ind);
            }
            else if(choice.equalsIgnoreCase("no")){
                while (true) {
                    ArrayList<Integer> indexArray = printAndGetParkingAvailableFloors();
                    System.out.print("Select any of the above floor number: ");
                    String floorNumber = in.nextLine().trim();
                    int floorNo = Validator.validateInteger(floorNumber,0,obj.floors-1);
                    if(floorNo == -1) continue;
                    return arr.get(floorNo);
                }
            }
            else {
                System.out.println("\nYou selected wrong option! Please select valid option (Yes / No)");
            }
        }
    }

    private ArrayList<Integer> printAndGetParkingAvailableFloors() {
        ArrayList<Integer> indexArray = obj.getParkingAvailableFloors();
        System.out.println();
        for (Integer j: indexArray) {
            if(j == 0) System.out.println("Floor " + (j) + " (Ground Floor)");
            else System.out.println("Floor " + (j));
        }
        return indexArray;
    }

    @Override
    public int[] suggestAndGetParkingPlace(ParkingLot parkingLot) {
        int[] position = parkingLot.getFirstParkingPosition();
        while (true) {
            System.out.println("\nEmpty Parking Place is available at " +
                    (position[0] + 1) + "/" + (position[1] + 1));
            System.out.print("Do you agree to proceed further with above parking location? (Yes / No) ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("yes")) {
                return position;
            }
            else if(choice.equalsIgnoreCase("no")){
                while (true) {
                    System.out.println("\nDetailed Floor Map of " + getOrdinalNumber(parkingLot.getFloorNo()) + " Floor");
                    parkingLot.showModifiedParkingLot(true);
                    System.out.print("Select any one Empty Parking Place in (R/C) format: ");
                    String str = in.nextLine().trim();
                    if(!str.contains("/")) {
                        System.out.println("\nPlease Enter correct format (R/C)");
                        continue;
                    }
                    String[] pos = str.split("/");
                    position[0] = Validator.validateInteger(pos[0],"rows") - 1;
                    if(position[0] == -2) continue;
                    position[1] = Validator.validateInteger(pos[1],"columns") - 1;
                    if(position[1] == -2) continue;
                    if(parkingLot.isValidEmptyParkingPlace(position[0], position[1])) {
                        return position;
                    }
                    else {
                        System.out.println("\nGiven Parking Place is Invalid. Please Enter valid Empty Parking Place!");
                    }
                }
            }
            else {
                System.out.println("\nYou have entered wrong option! Please select correct option (Yes / No): ");
            }
        }
    }

    @Override
    public void generateReceipt(ParkingLot parkingLot, int[] pos, Car car) {
        hashLine();
        carParking.parkACar(obj,parkingLot,pos,car);

        carParking.generatePathToParkACar(obj,parkingLot,pos);

        System.out.println("\nCar Parking Place : " + (pos[0] + 1) + "/" + (pos[1] + 1) + " at " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor");

        System.out.println("\nCar Number " + car.getCarNumber() + " parked successfully in " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor ");
        hashLine();
    }

    @Override
    public Car acceptCarDetailsToExit() {
        if(!obj.isCarAvailable()) {
            System.out.println("\nSorry! No Cars are available to exit!");
            return null;
        }
        return getValidCarFromParking();
    }

    private Car getValidCarFromParking() {
        while (true) {
            System.out.print("\nEnter the Car Number to exit ['back' for Main menu]: ");
            String carNo = in.nextLine().trim();
            if(carNo.equalsIgnoreCase("back")) return null;
            CarTable carTable = new CarTable();
            Car car = carTable.getCarByCarNo(carNo);
            if(car == null) {
                System.out.println("\nSorry! Given Car Number doesn't found! Please Enter valid car number");
            }
            else {
                if(carInParking.isCarNumberExist(carNo)) {
                    return car;
                }
                else {
                    System.out.println("\nSorry! Given Car is not in parking");
                }
            }
            System.out.println("If you want to move back to main menu, Enter 'back'");
        }
    }

    @Override
    public boolean confirmCarDetailsForExit(Car car) {
        while (true) {
            String carNo = car.getCarNumber();
            String carBrand = car.getCarBrand();
            String carModel = car.getCarModel();
            System.out.println("\nCar Number: " + carNo);
            System.out.println("Car Brand: " + carBrand);
            System.out.println("Car Model Number: " + carModel);
            System.out.print("Do you want the car with above details to exit? (Yes / No) ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("yes")) return true;
            else if(choice.equalsIgnoreCase("no")) {
                return false;
            }
            else {
                System.out.println("\nInvalid Input! Please Enter (Yes / No)");
            }
        }
    }

    @Override
    public void generateBill(ParkingLot parkingLot, int[] pos, Car car) {
        hashLine();
        System.out.println("\nCar Position : " + (pos[0] + 1) + "/" + (pos[1] + 1) + " at " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor");

        ParkingCell parkingCell = parkingLot.getParkingCellByPosition(pos[0],pos[1]);
        carParking.exitACarFromPosition(obj,parkingLot,pos,car);

        long seconds = carParking.calculateParkingTimeInSeconds(parkingCell);
        System.out.println("\nTotal Car Parking Time: " + seconds + " seconds");

        double bill = carParking.generateBill(parkingCell,car,seconds);
        System.out.printf("\nBill Amount for Parking: %.2f " + Billing.moneyAbbr + "\n", bill);
        parkingCell.setParkingPlaceEmpty();

        carParking.generatePathToExitACar(obj,parkingLot,pos);

        System.out.println("\nCar Number " + car.getCarNumber() + " removed successfully from " +
                getOrdinalNumber(parkingLot.getFloorNo()) + " floor ");

        hashLine();
    }

    @Override
    public void showAllParkingSlots() {
        while (true) {
            System.out.println();
            System.out.println("#".repeat(170));
            for (int i = obj.floors - 1; i >= 0; --i) {
                System.out.println("\nFloor Map of " + getOrdinalNumber(i) + " Floor");
                arr.get(i).showParkingLot();
            }
            System.out.println();
            System.out.println("#".repeat(170));
            System.out.print("\nIf you want to move back to main menu, Enter 'back' : ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("back")) break;
            else System.out.println("\nPlease Enter Valid Input");
        }
    }

    @Override
    public void showAllDetailedParkingSlots() {
        while (true) {
            System.out.println();
            System.out.println("#".repeat(170));
            for (int i = obj.floors - 1; i >= 0; --i) {
                System.out.println("\nDetailed Floor Map of " + getOrdinalNumber(i) + " Floor");
                arr.get(i).showModifiedParkingLot(i == 0);
            }
            System.out.println();
            System.out.println("#".repeat(170));
            System.out.print("\nIf you want to move back to main menu, Enter 'back' : ");
            String choice = in.nextLine().trim();
            if(choice.equalsIgnoreCase("back")) break;
            else System.out.println("\nPlease Enter Valid Input");
        }
    }

    @Override
    public void getCarHistory() {
        Car car = getValidCarNumber();
        if(car == null) return;
        showCarInformation(car);
        showCarParkingHistory(car);
    }

    private Car getValidCarNumber() {
        while (true) {
            System.out.print("\nEnter Car Number ['back' for Main menu]: ");
            String carNumber = in.nextLine();
            if(carNumber.equalsIgnoreCase("back")) return null;
            CarTable carTable = new CarTable();
            if(carNumber.equals("")) {
                System.out.println("\nSorry! Given Car Number doesn't found! Please enter valid " +
                        "Car Number which is in parking");
                System.out.println("If you want to move back to main menu, Enter 'back'");
                continue;
            }
            if(!carTable.isCarNumberExist(carNumber)) {
                System.out.println("\nSorry! Given Car not found!! Please enter valid Car Number");
                System.out.println("If you want to move back to main menu, Enter 'back'");
                continue;
            }
            return carTable.getCarByCarNo(carNumber);
        }
    }

    private void showCarInformation(Car car) {
        System.out.println();
        System.out.println("#".repeat(170));
        String carNo = car.getCarNumber();
        String carBrand = car.getCarBrand();
        String carModel = car.getCarModel();
        System.out.println("\nCar Information:");
        System.out.println("Car Number: " + carNo);
        System.out.println("Car Brand: " + carBrand);
        System.out.println("Car Model Number: " + carModel);
    }

    private void showCarParkingHistory(Car car) {
        System.out.println("\nParking History:");
        CarEntryExitMaster carEntryExitMaster = carEntryExitTable.getCarByCarNumber(car.getCarNumber());
        if(carEntryExitMaster == null) {
            System.out.println("No Parking History Available");
            System.out.println();
            System.out.println("#".repeat(170));
            return;
        }
        for (CarEntryExit carEntryExit:carEntryExitMaster.getCarEntryExits()) {
            LocalTime time1 = carEntryExit.getEntryTime();
            LocalTime time2 = carEntryExit.getExitTime();
            if(time1 == null) System.out.print("00:00:00");
            else System.out.print(getTime(time1));
            System.out.print(" - ");
            if(time2 == null ) System.out.print("00:00:00");
            else System.out.print(getTime(time2));
            System.out.print(" - ");
            int[] pos = carEntryExit.getPosition();
            System.out.print(pos[0] + "/" + pos[1] + " in " + getOrdinalNumber(pos[2]) + " floor - ");
            Billing billing = carEntryExit.getBilling();
            System.out.printf("%.2f" + " " + Billing.moneyAbbr, billing.getBill());
            System.out.println();
        }
        System.out.println();
        System.out.println("#".repeat(170));
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