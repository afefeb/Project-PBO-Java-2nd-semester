package code.vehicle;

public class Vehicle {
    private String vehicleNumber;
    private int price;

    public Vehicle(String vehicleNumber, int price) {
        this.vehicleNumber = vehicleNumber;
        this.price = price;
    }

    public Vehicle() {
    }

    public String getvehicleNumber() {
        return vehicleNumber;
    }

    public int getPrice() {
        return price;
    }
}






