package code.customer;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class Customer {
    private String firstName, lastName, id, pin;
    private double balance;
    public boolean isOrdering;
    private boolean active;
    private int countLogin;
    public static SortedMap<Integer, Boolean> IDOrderList = new TreeMap<>();

    public Customer(String id, int balance){
        this.id = id;
        this.balance = balance;
    }
    public Customer() {
        this.firstName = "";
        this.lastName = "";
        this.pin = null;
        this.id = null;
        this.balance = 0;
        this.isOrdering = false;
        this.active = true;
        this.countLogin = 0;
    }

    public void addIDOrder(int id) {
        IDOrderList.put(id, false);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double d) {
        this.balance += d;
    }

    public void minBalance(double d) {
        this.balance -= d;
    }

    public void minLoginCount() {
        this.countLogin--;
    }

    public void addLoginCount() {
        this.countLogin++;
        if (this.countLogin >= 3) {
            this.blockAccount();
        }
    }

    public void blockAccount() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public abstract void makeOrder();

    public abstract boolean confirmPay(int orderNumber);
}
