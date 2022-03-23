package service;

import database.CarEntryExitTable;
import database.CarInParking;
import model.Car;
import model.CarParkingPlace;
import model.ParkingCell;
import model.ParkingLot;

public interface CarParkingFunctionalities {

    void generateReceipt(ParkingLot parkingLot, CarParkingPlace pos, Car car, ParkingCell parkingCell);

    void generateBill(CarInParking carInParking, ParkingLot parkingLot, CarParkingPlace pos, Car car,
                      ParkingCell parkingCell, BillingFunctionalities billingFunctionalities);

    void showAllParkingSlots();

    void showAllDetailedParkingSlots();

    void getCarInfoAndParkingHistory(Car car, DataPrinter dataPrinter, ParkingHistory parkingHistory);

    void getBillingHistoryByCarNumber(BillingFunctionalities billingFunctionalities, String carNo);

}
