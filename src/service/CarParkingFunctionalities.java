package service;

import model.Car;
import model.CarParkingPlace;
import model.ParkingLot;

public interface CarParkingFunctionalities {

    void generateReceipt(ParkingLot parkingLot, CarParkingPlace pos, Car car);

    void generateBill(ParkingLot parkingLot, CarParkingPlace pos, Car car);

    void showAllParkingSlots();

    void showAllDetailedParkingSlots();

    void getCarInfoAndParkingHistory(Car car, DataPrinter dataPrinter);

    void getBillingHistoryByCarNumber(String carNo);

}
