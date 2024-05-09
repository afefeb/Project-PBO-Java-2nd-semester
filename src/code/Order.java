package code;
import code.customer.Customer;
import code.customer.Guest;
import code.customer.Member;
import code.promotion.CashbackPromo;
import code.promotion.PercentOffPromo;
import code.promotion.Promotion;
import code.vehicle.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

enum OrderStatus {
    UNPAID, SUCCESSFUL, CANCELED
}

public class Order {
    private LocalDate bookingDate ;
    private int deliveryCost, vehicleQuantity, duration;
    private static int count = 0;
    private static int orderNumber = 0 ;
    private double totalDiscount = 0 ,totalPrice;
    private Vehicle vehicle;
    private OrderStatus orderStatus;
    private Customer customer;
    // ArrayList<Customer> customers;

    public int getOrderNumber() {
        return orderNumber;
    }

    // 1. Milih jenis kendaraan
    // 2. Jumlahnya
    // 3. Durasi Peminjaman

    public Order() {
        this.orderStatus = OrderStatus.UNPAID;
        this.vehicle = null;
        /// * this.customers = new ArrayList<>(); */

        /*
         * this.deliveryCost = deliveryCost;
         * this.vehicle = vehicle;
         */
    }


    public void setVehicleQuantity(int vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Customer register(Scanner sc, String subscriptionType) {
        if (subscriptionType.equalsIgnoreCase("member")) {
            System.out.println("Membership Filkom Travel :");
            System.out.println("Please enter the data to create a new member account.");
        } else if (subscriptionType.equalsIgnoreCase("guest")) {
            System.out.println("Guest Filkom Travel :");
            System.out.println("Please enter the data to create a new guest account.");
        } else {
            return null;
        }
        System.out.print("First Name : ");
        String namaDepan = sc.nextLine();
        System.out.print("Last Name : ");
        String namaBelakang = sc.nextLine();
        String pin;
        String id = "";

        count++;
        if (subscriptionType.equals("member")) {
            id = "9" + addZeroNum(count, 3);
        } else {
            id = "1" + addZeroNum(count, 3);
        }

        System.out.println("Create your 6-digit pin: ");
        pin = sc.nextLine();
        Customer akun;
        if (subscriptionType.equals("member")) {
            akun = new Member();
        } else {
            akun = new Guest();
        }
        akun.setFirstName(namaDepan);
        akun.setLastName(namaBelakang);
        akun.setId(id);
        akun.setPin(pin);
        this.customer = akun;
        return akun;
    }

    public String[] login(Scanner sc, ArrayList<Customer> daftarAkun) {
        System.out.println("Masukkan data untuk login!");
        String[] data = new String[3];
        System.out.print("First name : ");
        String a = sc.nextLine();
        System.out.print("Last name : ");
        String b = sc.nextLine();
        data[0] = a + " " + b;
        System.out.print("ID : ");
        data[1] = sc.nextLine();
        System.out.print("Pin : ");
        data[2] = sc.nextLine();
        return data;
    }

    public static String addZeroNum(int number, int length) {
        String numStr = String.valueOf(number);
        int addZero = Math.max(0, length - numStr.length());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < addZero; i++) {
            result.append("0");
        }
        result.append(numStr);
        return result.toString();
    }

    public void checkOut() {
        orderNumber++;
        bookingDate = LocalDate.now();
        System.out.printf("Order ID : %03d\n", orderNumber);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Returning Date: " + returnDate(duration));
        System.out.printf("Total Price: Rp%,.0f\n", totalPrice);
    }

    public void printDetails() {
        System.out.println("Order Number: " + orderNumber);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Returning Date: " + returnDate(duration));
        System.out.println("Quantity: " + vehicleQuantity);
        System.out.println("Total Discount: " + totalDiscount);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Order Status: " + orderStatus);
        System.out.println("Id: " + customer.getId());
    }


    public void applyPromo(Promotion promo) {
        if(promo==null){
            return;
        }
        if (promo instanceof PercentOffPromo) {
            if (promo.isMinimumPriceEligible(this)) {
                try {
                    totalDiscount = promo.calculateDiscount(this);
                    if (totalDiscount > totalPrice || totalDiscount < 0) {
                        throw new IllegalArgumentException("Invalid total discount value: " + totalDiscount);
                    }
                    totalPrice -= totalDiscount;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("This PercentOff promotion is not applicable.");
            }
        }
        else if (promo instanceof CashbackPromo) {
            CashbackPromo cashbackPromo = (CashbackPromo) promo;
            if (cashbackPromo.isMinimumPriceEligible(this)) {
                try {
                    totalDiscount += cashbackPromo.calculateCashback(this);
                    totalPrice -= totalDiscount;
                    if (totalDiscount > totalPrice || totalDiscount < 0) {
                        throw new IllegalArgumentException("Invalid total discount value: " + totalDiscount);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("This CashBack promotion is not applicable.");
            }
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double calculatePrice() {
        totalPrice = vehicle.getPrice() * duration * vehicleQuantity;
        return totalPrice ;
    }

    public LocalDate returnDate(int duration){
        return LocalDate.now().plusDays(duration);
    }

    public void pay() {
        orderStatus = OrderStatus.SUCCESSFUL;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }
}
