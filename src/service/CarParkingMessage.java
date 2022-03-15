package service;

public class CarParkingMessage {

    public CarParkingMessage() {
    }

    public void welcome() {
        System.out.println("########## Welcome to Car Parking Application ##########");
    }

    public void showMenu() {
        System.out.println("\nMenu");
        System.out.println("1. Entry a Car");
        System.out.println("2. Exit the Car");
        System.out.println("3. Show Floor Maps");
        System.out.println("4. Show Detailed Floor Maps");
        System.out.println("5. Car History");
        System.out.println("6. Billing History");
        System.out.println("7. Quit Application");
        System.out.print("Please Choose any of the above option: ");
    }

    public void quitMessage() {
        System.out.println("\n########## Thank you for using the Application ##########");
    }

}
