package code;

import code.customer.Customer;
import code.vehicle.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private LocalDate bookingDate;
    private int orderNumber, deliveryCost, totalPrice, vehicleQuantity;
    private double totalDiscount;
    private Vehicle vehicle;
    private OrderStatus orderStatus;
    ArrayList<Customer> customers;

    public Order(LocalDate bookingDate, int orderNumber, int deliveryCost, int vehicleQuantity, Vehicle vehicle) {
        this.bookingDate = bookingDate;
        this.orderNumber = orderNumber;
        this.deliveryCost = deliveryCost;
        this.vehicleQuantity = vehicleQuantity;
        this.vehicle = vehicle;
        this.orderStatus = OrderStatus.UNPAID;
        this.customers = new ArrayList<>();
    }

    public void checkOut() {
        orderStatus = OrderStatus.SUCCESSFULL;
    }

    public void printDetails() {
        System.out.println("code.Order Number: " + orderNumber);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("code.vehicle.Vehicle: " + vehicle.getvehicleNumber());
        System.out.println("Quantity: " + vehicleQuantity);
        System.out.println("Delivery Cost: " + deliveryCost);
        System.out.println("Total Discount: " + totalDiscount);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("code.Order Status: " + orderStatus);
    }

    public void applyPromo() {

    }

    public void pay() {

    }
}
