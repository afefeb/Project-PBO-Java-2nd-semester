package code;

import code.customer.*;
import code.promotion.*;
import code.vehicle.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        ArrayList<Customer> listCustomer = new ArrayList<>();
        ArrayList<Order> listMenu = new ArrayList<>();
        ArrayList<Promotion> listPromotion = new ArrayList<>();
        String[] inputSplit;
        String input;

        do {
            input = sc.nextLine();
            inputSplit = input.split(" ", 3);
            if (inputSplit[0].equals("CREATE")) {
                String[] data = inputSplit[2].split("\\|");
                if (inputSplit[1].equals("MEMBER")) {
                    String memberId = data[0];
                    String memberName = data[1];
                    LocalDate date = LocalDate.parse(data[2], formatter);
                    int Memberbudget = Integer.parseInt(data[3]);
                    if (!isIDExist(listCustomer, memberId)) {
                        listCustomer.add(new Member(memberId, memberName, date, Memberbudget));
                        System.out.println("CREATE MEMBER SUCCESS: " + memberId + " " + memberName);
                    } else {
                        System.out.println("CREATE MEMBER FAILED: " + memberId + " IS EXISTS");
                    }
                } else if (inputSplit[1].equals("GUEST")) {
                    String guestID = data[0];
                    int guestBudget = Integer.parseInt(data[1]);
                    if (!isIDExist(listCustomer, guestID)) {
                        listCustomer.add(new Guest(guestID, guestBudget));
                        System.out.println("CREATE GUEST SUCCESS: " + guestID);
                    } else {
                        System.out.println("CREATE GUEST FAILED: " + guestID + " IS EXISTS");
                    }
                } else if (inputSplit[1].equals("MENU")) {
                    inputSplit = input.split(" ", 4);
                    data = inputSplit[3].split("\\|");
                    String menuID = data[0];
                    String menuName = data[1];
                    String numberPlate = data[2];
                    int price = Integer.parseInt(data[3]);
                    if (isMenuIDExist(listMenu, menuID)) {
                        System.out.println("CREATE MENU FAILED: " + menuID + " IS EXISTS");
                    } else if (isNumberPlateExist(listMenu, numberPlate)) {
                        System.out.println("CREATE MENU FAILED: " + numberPlate + " IS EXISTS");
                    } else {
                        if (inputSplit[2].equals("MOBIL")) {
                            String customType = data[4];
                            if (!isMenuIDExist(listMenu, menuID) || !isNumberPlateExist(listMenu, numberPlate)) {
                                listMenu.add(new Order(menuID, menuName, numberPlate, price, customType));
                            }
                        } else if (inputSplit[2].equals("MOTOR")) {
                            if (!isMenuIDExist(listMenu, menuID) || !isNumberPlateExist(listMenu, numberPlate)) {
                                listMenu.add(new Order(menuID, menuName, numberPlate, price));
                            }
                        }
                        System.out.println("CREATE MENU SUCCESS " + menuID + " " + menuName + " " + numberPlate);
                    }
                } else if (inputSplit[1].equals("PROMO")) {
                    inputSplit = input.split(" ");
                    data = inputSplit[3].split("\\|");
                    String promoCode = data[0];
                    LocalDate startDate = LocalDate.parse(data[1], formatter);
                    LocalDate endDate = LocalDate.parse(data[2], formatter);
                    int percentOff = Integer.parseInt(data[3].replace("%", ""));
                    int maxPromoAmount = Integer.parseInt(data[4]);
                    int minPurchase = Integer.parseInt(data[5]);
                    if (!isPromoExist(listPromotion, promoCode)) {
                        if (inputSplit[2].equals("CASHBACK")) {
                            listPromotion.add(new CashbackPromo(promoCode, startDate, endDate, percentOff, maxPromoAmount, minPurchase));
                            System.out.println("CREATE PROMO CASHBACK SUCCESS: " + promoCode);
                        } else if (inputSplit[2].equals("DISCOUNT")) {
                            listPromotion.add(new Discount(promoCode, startDate, endDate, percentOff, maxPromoAmount, minPurchase));
                            System.out.println("CREATE PROMO DISCOUNT SUCCESS: " + promoCode);
                        }
                    } else {
                        if (inputSplit[2].equals("CASHBACK")) {
                            System.out.println("CREATE PROMO CASHBACK FAILED: " + promoCode + " IS EXISTS");
                        } else if (inputSplit[2].equals("DISCOUNT")) {
                            System.out.println("CREATE PROMO DISCOUNT FAILED: " + promoCode + " IS EXISTS");
                        }
                    }
                }
            }

            if (inputSplit[0].equals("ADD_TO_CART")) {
                inputSplit = input.split(" ");
                String addOrderID = inputSplit[1];
                String addMenuID = inputSplit[2];
                int addQuantity = Integer.parseInt(inputSplit[3]);
                LocalDate addStartLoanDate = LocalDate.parse(inputSplit[4], formatter);
                if (isCustomerExist(listCustomer, addOrderID) && isMenuIDExist(listMenu, addMenuID)) {
                    Customer customer = getCustomer(listCustomer, addOrderID);
                    if (customer != null) {
                        listCustomer.remove(customer);
                        Order newOrder = getOrder(listMenu, addMenuID);
                        boolean isUpdated = false;
                        if (customer.isOrderExist(addMenuID)) {
                            Order order = customer.getOrder(addMenuID);
                            if (order != null) {
                                order.updateDuration(addQuantity);
                                isUpdated = true;
                                customer.updateTotalPurchase(addQuantity * order.getPricePerDuration());
                            }
                        } else {
                            newOrder.setBookingDate(addStartLoanDate);
                            newOrder.setDuration(addQuantity);
                            customer.addToCart(newOrder);
                        }
                        listCustomer.add(customer);
                        System.out.printf("ADD_TO_CART SUCCESS: %d %s %s %s %s\n", addQuantity, (addQuantity > 1 ? "days" : "day"), newOrder.getMenuName(), newOrder.getNumberPlate(), (isUpdated ? "(UPDATED)" : "(NEW)"));
                    } else {
                        System.out.println("ADD_TO_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
                    }
                } else {
                    System.out.println("ADD_TO_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
                }
            }

            if (inputSplit[0].equals("REMOVE_FROM_CART")) {
                inputSplit = input.split(" ");
                String removeOrderID = inputSplit[1];
                String removeMenuID = inputSplit[2];
                int removeDuration = Integer.parseInt(inputSplit[3]);
                if (isCustomerExist(listCustomer, removeOrderID)) {
                    Customer customer = getCustomer(listCustomer, removeOrderID);
                    if (customer.isOrderExist(removeMenuID)) {
                        listCustomer.remove(customer);
                        customer.removeFromCart(removeMenuID, removeDuration);
                        listCustomer.add(customer);
                    } else {
                        System.out.println("REMOVE_FROM_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
                    }
                } else {
                    System.out.println("REMOVE_FROM_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
                }
            }

            if (inputSplit[0].equals("APPLY_PROMO")) {
                String applyOrderID = inputSplit[1];
                String applyPromoCode = inputSplit[2];
                if (isCustomerExist(listCustomer, applyOrderID)){
                    if (isPromoExist(listPromotion, applyPromoCode)) {
                        Customer customer = getCustomer(listCustomer, applyOrderID);
                        if (customer instanceof Member) {
                            Member temp = (Member) customer;
                            listCustomer.remove(temp);
                            temp.applyPromo(listPromotion, applyPromoCode);
                            listCustomer.add(temp);
                        } else {
                            System.out.println("APPLY_PROMO FAILED: " + applyPromoCode);
                        }
                    } else {
                        System.out.println("APPLY_PROMO FAILED: " + applyPromoCode);
                    }
                } else {
                    System.out.println("APPLY_PROMO FAILED: " + applyPromoCode);
                }
            }

            if (inputSplit[0].equals("TOPUP")) {
                String topUpOrderID = inputSplit[1];
                int topUpBudget = Integer.parseInt(inputSplit[2]);
                if (isCustomerExist(listCustomer, topUpOrderID)){
                    Customer customer = getCustomer(listCustomer, topUpOrderID);
                    if (customer != null) {
                        int initBalance = customer.getBalance();
                        listCustomer.remove(customer);
                        customer.updateBalance(topUpBudget);
                        listCustomer.add(customer);
                        if (customer instanceof Member){
                            Member member = (Member) customer;
                            System.out.printf("TOPUP SUCCESS: %s %d => %d\n", member.getMemberName(), initBalance, member.getBalance());
                        } else if(customer instanceof Guest){
                            Guest guest = (Guest) customer;
                            System.out.printf("TOPUP SUCCESS: GUEST %d => %d\n", initBalance, guest.getBalance());
                        }
                    } else {
                        System.out.println("TOPUP FAILED: NON EXISTENT CUSTOMER");
                    }
                } else {
                    System.out.println("TOPUP FAILED: NON EXISTENT CUSTOMER");
                }
            }

            if (inputSplit[0].equals("CHECK_OUT")) {
                String checkoutOrderID = inputSplit[1];
                if (isCustomerExist(listCustomer, checkoutOrderID)){
                    Customer customer = getCustomer(listCustomer, checkoutOrderID);
                    if (!customer.isOrdering()) {
                        System.out.println("CHECK_OUT FAILED");
                    } else {
                        if (customer instanceof Member) {
                            Member temp = (Member) customer;
                            listCustomer.remove(temp);
                            temp.checkOut();
                            listCustomer.add(temp);
                        } else if (customer instanceof Guest) {
                            Guest temp = (Guest) customer;
                            listCustomer.remove(temp);
                            temp.checkOut();
                            listCustomer.add(temp);
                        }
                    }
                } else {
                    System.out.println("NON EXISTENT CUSTOMER");
                }
            }

            if (inputSplit[0].equals("PRINT")) {
                String printOrderID = inputSplit[1];
                if (isCustomerExist(listCustomer, printOrderID)) {
                    Customer customer = getCustomer(listCustomer, printOrderID);
                    print(customer, printOrderID);
                }
            }

            if (inputSplit[0].equals("PRINT_HISTORY")) {
                String printHistoryOrderID = inputSplit[1];
                if (isCustomerExist(listCustomer, printHistoryOrderID)) {
                    Customer customer = getCustomer(listCustomer, printHistoryOrderID);
                    printHistory(customer, printHistoryOrderID);
                }
            }
        } while (!inputSplit[0].equals("EXIT"));
        sc.close();
    }

    public static boolean isIDExist(ArrayList<Customer> listCustomer, String id){
        for(Customer customer : listCustomer){
            if(customer.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public static boolean isMenuIDExist(ArrayList<Order> listOrder, String menuID){
        for(Order order : listOrder){
            if(order.getMenuID().equals(menuID)){
                return true;
            }
        }
        return false;
    }
    public static boolean isNumberPlateExist(ArrayList<Order> listOrder, String numberPlate){
        for(Order order : listOrder){
            if(order.getVehicle().getvehicleNumber().equals(numberPlate)){
                return true;
            }
        }
        return false;
    }
    public static boolean isPromoExist(ArrayList<Promotion> listPromo, String promoCode){
        for(Promotion promo : listPromo){
            if(promo.getPromoCode().equals(promoCode)){
                return true;
            }
        }
        return false;
    }

    public static boolean isCustomerExist(ArrayList<Customer> customers, String customerID) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerID)) {
                return true;
            }
        }
        return false;
    }

    public static Customer getCustomer(ArrayList<Customer> customers, String customerID) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    public static Order getOrder(ArrayList<Order> listOrder ,String menuID){
        for(Order order : listOrder){
            if(order.getMenuID().equals(menuID)){
                return order;
            }
        }
        return null;
    }

    public static void print(Customer customer, String printOrderID) {
        int counter = 1;
        if (customer instanceof Member) {
            Member temp = (Member) customer;
            System.out.println("Kode Pemesan: " + temp.getId());
            System.out.println("Nama: " + temp.getFirstName());
            ArrayList<Order> listOrder = new ArrayList<>();
            if (temp.isOrdering()) {
                listOrder = temp.getOrders();
            } else {
                if (!temp.getOrderHistory().isEmpty()) {
                    listOrder = temp.getOrdersFromHistory();
                } else {
                    listOrder = temp.getOrders();
                }
            }
            if (temp.getOrderNumber(listOrder) != 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("id-ID"));
                System.out.println("Nomor Pesanan: " + temp.getOrderNumber(listOrder));
                System.out.println("Tanggal Pesanan: " + temp.getCheckOutDate().format(formatter));
            }
            System.out.printf("%3s | %-25s | %4s | %10s \n", "No", "Menu", "Dur.", "Subtotal");
            System.out.println("=====================================================");
            for (Order orders : listOrder) {
                System.out.printf("%3d | %-25s | %4d | %10s \n", counter, orders.getMenuName(), orders.getDuration(), String.format("%,d", orders.calculatePrice()).replace(",", "."));
                System.out.printf("%5s %s - %s\n", " ", orders.getStartDate(), orders.getEndDate());
                counter++;
            }
            System.out.println("=====================================================");
            System.out.printf("%-32s: %17s\n", "Sub Total", String.format("%,d", temp.getSubTotal()).replace(",", "."));
            if (!temp.isOrdering()) {
                if (temp.isPromoFromHistoryApplied() && temp.getPromoFromHistory() instanceof Discount) {
                    System.out.printf("%-32s: %17s\n", "PROMO: " + temp.getPromoFromHistory().getPromoCode(), String.format("-%,.0f", temp.calculateDiscount()).replace(",", "."));
                }
            } else {
                if (temp.isPromoApplied() && temp.getPromo() instanceof Discount) {
                    System.out.printf("%-32s: %17s\n", "PROMO: " + temp.getPromo().getPromoCode(), String.format("-%,.0f", temp.calculateDiscount()).replace(",", "."));
                }
            }
            System.out.println("=====================================================");
            System.out.printf("%-32s: %17s\n", "Total", String.format("%,d", temp.calculateTotalPurchase()).replace(",", "."));
            if (!temp.isOrdering()) {
                if (temp.isPromoFromHistoryApplied() && temp.getPromoFromHistory() instanceof CashbackPromo) {
                    System.out.printf("%-32s: %17s\n", "PROMO: " + temp.getPromoFromHistory().getPromoCode(), String.format("%,.0f", temp.calculateDiscount()).replace(",", "."));
                }
            } else {
                if (temp.isPromoApplied() && temp.getPromo() instanceof CashbackPromo) {
                    System.out.printf("%-32s: %17s\n", "PROMO: " + temp.getPromo().getPromoCode(), String.format("%,.0f", temp.calculateDiscount()).replace(",", "."));
                }
            }
            System.out.printf("%-32s: %17s\n", "Saldo", String.format("%,d", temp.getBalance()).replace(",", "."));
        } else if (customer instanceof Guest) {
            Guest temp = (Guest) customer;
            System.out.println("Kode Pemesan: " + temp.getId());
            System.out.println("Nama: NON_MEMBER");
            ArrayList<Order> listOrder = new ArrayList<>();
            if (temp.isOrdering()) {
                listOrder = temp.getOrders();
            } else {
                if (!temp.getOrderHistory().isEmpty()) {
                    listOrder = temp.getOrdersFromHistory();
                } else {
                    listOrder = temp.getOrders();
                }
            }
            if (temp.getOrderNumber(listOrder) != 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("id-ID"));
                System.out.println("Nomor Pesanan: " + temp.getOrderNumber(listOrder));
                System.out.println("Tanggal Pesanan: " + temp.getCheckOutDate().format(formatter));
            }
            System.out.printf("%3s | %-25s | %4s | %10s \n", "No", "Menu", "Dur.", "Subtotal");
            System.out.println("=====================================================");
            for (Order orders : listOrder) {
                System.out.printf("%3d | %-25s | %4d | %10s \n", counter, orders.getMenuName(), orders.getDuration(), String.format("%,d", orders.calculatePrice()).replace(",", "."));
                System.out.printf("%5s %s - %s\n", " ", orders.getStartDate(), orders.getEndDate());
                counter++;
            }
            System.out.println("=====================================================");
            System.out.printf("%-32s: %17s\n", "Sub Total", String.format("%,d", temp.getSubTotal()).replace(",", "."));
            System.out.println("=====================================================");
            System.out.printf("%-32s: %17s\n", "Total", String.format("%,d", temp.calculateTotalPurchase()).replace(",", "."));
            System.out.printf("%-32s: %17s\n", "Saldo", String.format("%,d", temp.getBalance()).replace(",", "."));
        }
    }

    public static void printHistory(Customer customer, String printOrderID) {
        int counter = 1;
        Map<Integer, ArrayList<Order>> orderHistory = customer.getOrderHistory();

        if (customer instanceof Member) {
            Member temp = (Member) customer;
            int carAmount, motorAmount;
            System.out.println("Kode Pemesan: " + temp.getId());
            System.out.println("Nama: " + temp.getFirstName());
            System.out.println("Saldo: " + temp.getBalance());
            System.out.printf("%4s | %10s | %5s | %5s | %8s | %-8s\n", "No", "No. Pesanan", "Motor", "Mobil", "Subtotal", "PROMO");
            System.out.println("===========================================================");
            for (Entry<Integer, ArrayList<Order>> orders : orderHistory.entrySet()) {
                carAmount = 0;
                motorAmount = 0;
                ArrayList<Order> order = orders.getValue();
                for (Order pointer : order) {
                    if (pointer.getVehicle() instanceof Car) {
                        carAmount++;
                    } else if (pointer.getVehicle() instanceof Motorcycle) {
                        motorAmount++;
                    }
                }
                System.out.printf("%4d | %11d | %5d | %5d | %8d | %-8s\n", counter, orders.getKey(), motorAmount, carAmount, temp.getSubTotalFromHistory(orders.getKey()), (temp.getPromoByIndex(orders.getKey()) != null ? temp.getPromoByIndex(orders.getKey()).getPromoCode() : "-"));
                counter++;
            }
            System.out.println("===========================================================");
        } else if (customer instanceof Guest) {
            Guest temp = (Guest) customer;
            int carAmount = 0, motorAmount = 0;
            System.out.println("Kode Pemesan: " + temp.getId());
            System.out.println("Nama: NON_MEMBER");
            System.out.println("Saldo: " + temp.getBalance());
            System.out.printf("%4s | %10s | %5s | %5s | %8s | %-8s\n", "No", "No. Pesanan", "Motor", "Mobil", "Subtotal", "PROMO");
            System.out.println("===========================================================");
            for (Entry<Integer, ArrayList<Order>> orders : orderHistory.entrySet()) {
                ArrayList<Order> order = orders.getValue();
                for (Order pointer : order) {
                    if (pointer.getVehicle() instanceof Car) {
                        carAmount++;
                    } else if (pointer.getVehicle() instanceof Motorcycle) {
                        motorAmount++;
                    }
                }
                System.out.printf("%4d | %11d | %5d | %5d | %8d | %-8s\n", counter, orders.getKey(), motorAmount, carAmount, temp.getSubTotal(), "-");
                counter++;
            }
            System.out.println("===========================================================");
        }
    }
}