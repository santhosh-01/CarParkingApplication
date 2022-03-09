package service;

import model.Car;
import model.ParkingLot;

public interface CarParkingFunctionalities {

    Car acceptCarDetailsToPark();

    boolean confirmCarDetails(Car car);

    boolean checkAndSuggestCarParkingFloor(Car car);

    ParkingLot suggestAndGetCarParkingLot();

    int[] suggestAndGetParkingPlace(ParkingLot parkingLot);

    void generateReceipt(ParkingLot parkingLot, int[] pos, Car car);

    Car acceptCarDetailsToExit();

    boolean confirmCarDetailsForExit(Car car);

    void generateBill(ParkingLot parkingLot, int[] pos, Car car);

    void showAllParkingSlots();

    void showAllDetailedParkingSlots();

    void getCarHistory();
}
