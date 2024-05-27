package code.customer;


import code.Order;
import code.promotion.Discount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public abstract class Customer {
    private String id;
    protected LocalDate checkOutDate;
    private int balance;
    protected boolean ordering = false;
    private int totalPurchase;
    private int subTotal;
    protected static int orderCounter = 1;
    private int currentOrderNumber;
    protected ArrayList<Order> listOrder = new ArrayList<>();
    protected Map<Integer, ArrayList<Order>> orderHistory = new HashMap<>();
    protected Map<Integer, Integer> subTotalHistory = new HashMap<>();

    public Customer(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public abstract void makeOrder();

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
        ordering = true;
        listOrder.add(order);
        totalPurchase += order.calculatePrice();
        currentOrderNumber = orderCounter;
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
                if (temp.getDuration() <= 0) {
                    listOrder.remove(temp);
                    System.out.println("REMOVE_FROM_CART SUCCESS: " + temp.getMenuName() + " " + temp.getNumberPlate() + " IS REMOVED");
                    if (listOrder.isEmpty()) {
                        ordering = false;
                        currentOrderNumber = 0;
                    }
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

    public ArrayList<Order> getOrdersFromHistory() {
        ArrayList<Order> orders = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<Order>> history : orderHistory.entrySet()) {
            orders = history.getValue();
        }
        return orders;
    }

    public ArrayList<Order> getOrders() {
        return listOrder;
    }

    public void setTotalPurchase(double d) {
        this.totalPurchase = (int) d;
    }

    public int calculateTotalPurchase() {
        totalPurchase = 0;
        ArrayList<Order> temp = new ArrayList<>();
        if (!orderHistory.isEmpty()) {
            temp = getOrdersFromHistory();
        } else if (orderHistory.isEmpty() && isOrdering()) {
            temp = getOrders();
        }

        for (Order orders : temp) {
            totalPurchase += orders.calculatePrice();
        }

        if (this instanceof Member) {
            Member member = (Member) this;
            if (member.getPromoFromHistory() instanceof Discount) {
                totalPurchase -= member.calculateDiscount();
            }
        }

        return totalPurchase;
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
        subTotal = 0;
        ArrayList<Order> temp = new ArrayList<>();
        if (!orderHistory.isEmpty()) {
            temp = getOrdersFromHistory();
        } else if (orderHistory.isEmpty() && isOrdering()) {
            temp = getOrders();
        }

        for (Order orders : temp) {
            subTotal += orders.calculatePrice();
        }

        return subTotal;
    }

    public int getSubTotalFromHistory(int key) {
        return subTotalHistory.get(key);
    }

    public void updateTotalPurchase(int totalPurchase) {
        this.totalPurchase += totalPurchase;
    }

    public Map<Integer, ArrayList<Order>> getOrderHistory() {
        return orderHistory;
    }

    public void reset() {
        this.ordering = false;
        this.totalPurchase = 0;
        this.subTotal = 0;
        this.listOrder = new ArrayList<>();
    }

    public boolean isOrdering() {
        return ordering;
    }

    public int getCurrentOrderNumber() {
        return currentOrderNumber;
    }
}