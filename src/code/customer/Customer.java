package code.customer;


import code.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public abstract class Customer {
    private String id;
    protected LocalDate checkOutDate;
    private int balance;
    private int totalPurchase;
    private int subTotal;
    protected static int orderCounter = 1;
    protected boolean checkedOut = false;
    protected ArrayList<Order> listOrder = new ArrayList<>();
    protected Map<Integer, ArrayList<Order>> orderHistory = new HashMap<>();

    public Customer(String id, int balance){
        this.id = id;
        this.balance = balance;
    }


    public void updateBalance(double d) {
        this.balance += d;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void addToCart(Order order) {
        listOrder.add(order);
        totalPurchase += order.calculatePrice();
        subTotal += order.calculatePrice();
    }

    public void removeFromCart(String menuID, int duration) {
        Order temp = null;
        if (isOrderExist(menuID)) {
            for (Order order : listOrder) {
                if (order.getMenuID().equals(menuID)) {
                    temp = order;
                    break;
                }
            }
            if (temp != null) {
                temp.updateDuration(-duration);
                totalPurchase -= temp.getPricePerDuration() * duration;
                subTotal -= temp.getPricePerDuration() * duration;
                if (temp.getDuration() <= 0) {
                    listOrder.remove(temp);
                    System.out.println("REMOVE_FROM_CART SUCCESS: " + temp.getMenuName() + " " + temp.getNumberPlate() + " IS REMOVED");
                } else {
                    System.out.println("REMOVE_FROM_CART SUCCESS: " + temp.getMenuName() + " " + temp.getNumberPlate() + " DURATION IS DECREMENTED");
                }
            }
        } else {
            System.out.println("REMOVE_FROM_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
        }
    }

    public boolean isOrderExist(String menuID) {
        if (listOrder != null && !listOrder.isEmpty()) {
            for (Order order : listOrder) {
                if (order.getMenuID() != null && order.getMenuID().equals(menuID)) {
                    return true;
                }
            }
        }
        return false;
    }


    public Order getOrder(String menuID) {
        for (Order order : listOrder) {
            if (order.getMenuID().equals(menuID)) {
                return order;
            }
        }
        return null;
    }

    public ArrayList<Order> getOrders() {
        return listOrder;
    }

    public void setTotalPurchase(double d) {
        this.totalPurchase = (int) d;
    }

    public int getTotalPurchase() {
        return totalPurchase;
    }

    public boolean hasCheckedOut() {
        return checkedOut;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getOrderNumber(ArrayList<Order> listOrder) {
        for (Map.Entry<Integer, ArrayList<Order>> history : orderHistory.entrySet()) {
            if (history.getValue().equals(listOrder)) {
                return history.getKey();
            }
        }
        return 0;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void updateTotalPurchase(int totalPurchase) {
        this.totalPurchase += totalPurchase;
        subTotal += totalPurchase;
    }

    public Map<Integer, ArrayList<Order>> getOrderHistory() {
        return orderHistory;
    }
}