package code;

import code.customer.Customer;
import code.customer.Guest;
import code.customer.Member;
import code.promotion.CashbackPromo;
import code.promotion.Discount;
import code.promotion.Promotion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        ArrayList<Customer> listCustomer = new ArrayList<>();
        ArrayList<Order> listMenu = new ArrayList<>();
        ArrayList<Promotion> listPromotion = new ArrayList<>();
        String[] inputSplit;
        String input;

        //input create member
        // CREATE MEMBER1 ID|NAMA|TANGGAL|SALDOAWAL
        // CREATE GUEST IDTAMU | SALDO AWAL
        do {
            input = sc.nextLine();
            inputSplit = input.split(" ", 3);
            if (inputSplit[0].equals("CREATE")) {
                String[] data = inputSplit[2].split("\\|");
                // budget ganti ballance
                if (inputSplit[1].equals("MEMBER")) {
                    String memberId = data[0];
                    String memberName = data[1];
                    LocalDate date = LocalDate.parse(data[2], formatter);
                    int Memberbudget = Integer.parseInt(data[3]);
                    if (!isExistID(listCustomer, memberId)) {
                        listCustomer.add(new Member(memberId, memberName, date, Memberbudget));
                        System.out.println("CREATE MEMBER SUCCESS: " + memberId + " " + memberName);
                    }
                } else if (inputSplit[1].equals("GUEST")) {
                    String guestID = data[0];
                    int guestBudget = Integer.parseInt(data[1]);
                    if (!isExistID(listCustomer, guestID)) {
                        listCustomer.add(new Guest(guestID, guestBudget));
                        System.out.println("CREATE GUEST SUCCESS: " + guestID);
                    }
                    //input CREATE MENU
                } else if (inputSplit[1].equals("MENU")) {
                    inputSplit = input.split(" ");
                    data = inputSplit[3].split("\\|");
                    if (inputSplit[2].equals("MOBIL")) {
                        String menuID = data[0];
                        String menuName = data[1];
                        String numberPlate = data[2];
                        int price = Integer.parseInt(data[3]);
                        String customType = data[4];
                        if (!isExistMenuID(listMenu, menuID) || !isExistNumberPlate(listMenu, numberPlate)) {
                            listMenu.add(new Order(menuID, menuName, numberPlate, price, customType));
                            System.out.println("CREATE MENU SUCCESS " + menuID + " " + menuName + " " + numberPlate);
                        }
                    } else if (inputSplit[2].equals("MOTOR")) {
                        String menuID = data[0];
                        String menuName = data[1];
                        String numberPlate = data[2];
                        int price = Integer.parseInt(data[3]);
                        if (!isExistMenuID(listMenu, menuID) || !isExistNumberPlate(listMenu, numberPlate)) {
                            listMenu.add(new Order(menuID, menuName, numberPlate, price));
                            System.out.println("CREATE MENU SUCCESS " + menuID + " " + menuName + " " + numberPlate);
                        }
                    }
                    // input create promo
                } else if (inputSplit[1].equals("PROMO")) {
                    inputSplit = input.split(" ");
                    if (inputSplit[2].equals("CASHBACK")) {
                        data = inputSplit[3].split("\\|");
                        String cashbackPromoCode = data[0];
                        LocalDate startDateCashback = LocalDate.parse(data[1], formatter);
                        LocalDate endDateCashback = LocalDate.parse(data[2], formatter);
                        int percentOff = Integer.parseInt(data[3].replace("%", ""));
                        int maximumCashback = Integer.parseInt(data[4]);
                        int minPurchase = Integer.parseInt(data[5]);
                        if (!isExistPromo(listPromotion, cashbackPromoCode)) {
                            listPromotion.add(new CashbackPromo(cashbackPromoCode, startDateCashback, endDateCashback, percentOff, maximumCashback, minPurchase));
                            System.out.println("CREATE PROMO CASHBACK SUCCESS: " + cashbackPromoCode);
                        }
                    } else if (inputSplit[2].equals("DISCOUNT")) {
                        data = inputSplit[3].split("\\|");
                        String discountPromoCode = data[0];
                        LocalDate startDateDiscount = LocalDate.parse(data[1], formatter);
                        LocalDate endDateDiscount = LocalDate.parse(data[2], formatter);
                        int percentOff = Integer.parseInt(data[3].replace("%", ""));
                        int maximumDiscount = Integer.parseInt(data[4]);
                        int minPurchase = Integer.parseInt(data[5]);
                        if (!isExistPromo(listPromotion, discountPromoCode)) {
                            listPromotion.add(new Discount(discountPromoCode, startDateDiscount, endDateDiscount, percentOff, maximumDiscount, minPurchase));
                            System.out.println("CREATE PROMO DISCOUNT SUCCESS: " + discountPromoCode);
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
                if (isCustomerExist(listCustomer, addOrderID) && isExistMenuID(listMenu, addMenuID)) {
                    Customer customer = getCustomer(listCustomer, addOrderID);
                    if (customer != null) {
                        listCustomer.remove(customer);
                        Order newOrder = getOrder(listMenu, addMenuID);
                        Order order;
                        boolean isUpdated = false;
                        if (customer.isOrderExist(addMenuID)) {
                            order = customer.getOrder(addMenuID);
                            if(order!=null) {
                                order.updateDuration(addQuantity);
                                isUpdated = true;
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
                        System.out.println("REMOVE_FROM_CART FAILED: NON EXISTENT MENU IN CART");
                    }
                } else {
                    System.out.println("REMOVE_FROM_CART FAILED: NON EXISTENT CUSTOMER");
                }
            }
            if (inputSplit[0].equals("APPLY_PROMO")) {
                String applyOrderID = inputSplit[1];
                String applyPromoCode = inputSplit[2];
                if(isCustomerExist(listCustomer,applyOrderID)){
                    Customer customer = getCustomer(listCustomer, applyOrderID);
                    // cek tanggal pesan - tanggal daftar akun , lebih dari 30 hari || minimum pembelian terpenuhi,
                    // DISC - > discount
                    // CASHBACK10
                }


            }
            if (inputSplit[0].equals("TOPUP")) {
                String topUpOrderID = inputSplit[1];
                int topUpBudget = Integer.parseInt(inputSplit[2]);
                if(isCustomerExist(listCustomer, topUpOrderID)){
                    Customer customer = getCustomer(listCustomer, topUpOrderID);
                    if(customer!=null) {
                        double initBalance = customer.getBalance();
                        listCustomer.remove(customer);
                        customer.addBalance(topUpBudget);
                        listCustomer.add(customer);
                        if(customer instanceof Member){
                            Member member = (Member) customer;
                            System.out.printf("TOPUP SUCCESS: %s %.0f => %.0f\n", member.getMemberName(), initBalance, member.getBalance());
                        }else if(customer instanceof Guest){
                            Guest guest = (Guest) customer;
                            System.out.printf("TOPUP SUCCESS: GUEST %.0f => %.0f\n", initBalance, guest.getBalance());
                        }
                    }else {
                        System.out.println("TOPUP FAILED: NON EXISTENT CUSTOMER");
                    }
                }else {
                    System.out.println("TOPUP FAILED: NON EXISTENT CUSTOMER");
                }

            }
            if (inputSplit[0].equals("CHECK_OUT")) {
                String checkoutOrderID = inputSplit[1];
                if(isCustomerExist(listCustomer,checkoutOrderID)){
                    Customer customer = getCustomer(listCustomer, checkoutOrderID);

                }
            }
            if (inputSplit[0].equals("PRINT")) {
                String printOrderID = inputSplit[1];
            }
            if (inputSplit[0].equals("PRINT_HISTORY")) {
                String printHisOrderID = inputSplit[1];

            }
        }while (!inputSplit[0].equals("exit"));






        //ADD_TO_CART A001
        //REMOVE_FROM_CART

        //APPLY_PROMO
        //TOPUP
        //CHECK OUT
        //PRINT








    }
    public static boolean isExistID(ArrayList<Customer> listCustomer, String id){
        for(Customer customer : listCustomer){
            if(customer.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public static boolean isExistMenuID(ArrayList<Order> listOrder, String menuID){
        for(Order order : listOrder){
            if(order.getMenuID().equals(menuID)){
                return true;
            }
        }
        return false;
    }
    public static boolean isExistNumberPlate(ArrayList<Order> listOrder, String numberPlate){
        for(Order order : listOrder){
            if(order.getVehicle().getvehicleNumber().equals(numberPlate)){
                return true;
            }
        }
        return false;
    }
    public static boolean isExistPromo(ArrayList<Promotion> listPromo, String promoCode){
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
}