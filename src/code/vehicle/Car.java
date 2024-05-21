package code.vehicle;

public class Car extends Vehicle {
    String customType;
    public Car(String numberPlate, int price,String customType) {
        super(numberPlate, price);
        this.customType = customType;
    }
}
