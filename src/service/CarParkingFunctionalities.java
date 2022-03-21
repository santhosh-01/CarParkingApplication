package service;

import database.CarInParking;
import model.Car;
import model.CarParkingPlace;
import model.ParkingCell;
import model.ParkingLot;

public interface CarParkingFunctionalities {

    void generateReceipt(ParkingLot parkingLot, CarParkingPlace pos, Car car, ParkingCell parkingCell);

    void generateBill(CarInParking carInParking, ParkingLot parkingLot, CarParkingPlace pos, Car car,
                      ParkingCell parkingCell);

    void showAllParkingSlots();

    void showAllDetailedParkingSlots();

    void getCarInfoAndParkingHistory(Car car, DataPrinter dataPrinter);

    void getBillingHistoryByCarNumber(String carNo);

}
