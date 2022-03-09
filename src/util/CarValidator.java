package util;

import database.CarInParking;
import model.Car;

public class CarValidator {

    private CarValidator() {}

    public static boolean checkDuplicateCarNoInParking(String carNo) {
        CarInParking carInParking = new CarInParking();
        if(carInParking.isCarNumberExist(carNo)) {
            System.out.println("\nDuplicate Car! Given Car Number is already in parking");
            return true;
        }
        else {
            return false;
        }
    }



}
