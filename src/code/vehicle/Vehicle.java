package code.vehicle;

public class Vehicle {
    private String numberPlate;
    private int price;

    public Vehicle(String numberPlate, int price) {
        this.numberPlate = numberPlate;
        this.price = price;
    }

    public Vehicle() {
    }

    public String getvehicleNumber() {
        return numberPlate;
    }

    public int getPrice() {
        return price;
    }
}






