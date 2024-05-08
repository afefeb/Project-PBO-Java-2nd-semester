package code;

import code.customer.Customer;
import code.promotion.Promotion;
import code.vehicle.*;

import java.time.LocalDate;
import java.util.ArrayList;

enum OrderStatus {
    UNPAID, SUCCESSFUL, CANCELED
}

public class Order {
    private LocalDate bookingDate;
    private int deliveryCost, totalPrice, vehicleQuantity, duration;
    private static int count = 0;
    private int orderNumber;
    private double totalDiscount;
    private Vehicle vehicle;
    private OrderStatus orderStatus;
    //ArrayList<Customer> customers;

    public int getOrderNumber() {
        return orderNumber;
    }

    //1. Milih jenis kendaraan
    //2. Jumlahnya
    //3. Durasi Peminjaman


    public Order(int vehicleQuantity, int duration) {
        count++;
        this.duration = duration;
        this.vehicleQuantity = vehicleQuantity;
        this.orderStatus = OrderStatus.UNPAID;
        ///* this.customers = new ArrayList<>(); */
        
        /* this.deliveryCost = deliveryCost;
        this.vehicle = vehicle; */
    }
    
    // Nomor Order
    // Tanggal pemesanan
    // Tanggal pengembalian
    // Total harga 

    public void checkOut() {
        bookingDate = LocalDate.now();
        System.out.printf("Order ID : %03d\n", orderNumber);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Returning Date: ");
        System.out.printf("Total Price: Rp%,d\n", calculatePrice());
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

    public void applyPromo(Promotion promo) {

    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int calculatePrice(){
        return vehicle.getPrice() * duration * vehicleQuantity;
    }

    public void pay() {
        orderStatus = OrderStatus.SUCCESSFUL;
    }

    
}
