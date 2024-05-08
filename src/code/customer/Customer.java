package code.customer;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import code.Order;

public abstract class Customer {
    private String firstName, lastName;
    public boolean isOrdering;
    ArrayList<Order> order;
    public static SortedMap<Integer, Boolean> IDOrderList = new TreeMap<>();
    
    public Customer() {
        this.firstName = "";
        this.lastName = "";
        this.isOrdering = false;
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
    
    public abstract void makeOrder();

    public abstract boolean confirmPay(int orderNumber);
}
