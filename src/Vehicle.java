public class Vehicle {
    private String vehicleNumber;
    private int price;

    public Vehicle(String vehicleNumber, int price) {
        this.vehicleNumber = vehicleNumber;
        this.price = price;
    }

    public String getvehicleNumber() {
        return vehicleNumber;
    }

    public int getPrice() {
        return price;
    }
}

class LargeVehicle extends Vehicle {
    public LargeVehicle() {
        super("101", 100000);
    }
}

class MediumVehicle extends Vehicle {
    public MediumVehicle() {
        super("102", 50000);
    }
}

class SmallVehicle extends Vehicle {
    public SmallVehicle() {
        super("103", 25000);
    }
}
