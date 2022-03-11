package service;

import model.Car;
import model.ParkingLot;

public interface CarParkingFunctionalities {

    void generateReceipt(ParkingLot parkingLot, int[] pos, Car car);

    void generateBill(ParkingLot parkingLot, int[] pos, Car car);

    void showAllParkingSlots();

    void showAllDetailedParkingSlots();

    void getCarInfoAndParkingHistory(Car car, DataPrinter dataPrinter);

    void getBillingHistoryByCarNumber(String carNo);

}
