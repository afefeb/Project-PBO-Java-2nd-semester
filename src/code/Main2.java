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


        //input create member
        // CREATE MEMBER1 ID|NAMA|TANGGAL|SALDOAWAL
        // CREATE GUEST IDTAMU | SALDO AWAL

        String[] input = sc.nextLine().split(" ");
        if(input[0].equals("CREATE")) {
            String[] data = input[2].split("\\|");
            // budget ganti ballance
            if (input[1].equals("MEMBER")) {
                String memberId = data[0];
                String memberName = data[1];
                LocalDate date = LocalDate.parse(data[2], formatter);
                int Memberbudget = Integer.parseInt(data[3]);
                if(!isExistID(listCustomer,memberId)) {
                    listCustomer.add(new Member(memberId, memberName, date, Memberbudget));
                    System.out.println("CREATE MEMBER SUCCESS: " + memberId + " " + memberName);
                }
            } else if (input[1].equals("GUEST")) {
                String guestID = data[0];
                int guestBudget = Integer.parseInt(data[1]);
                if(!isExistID(listCustomer,guestID)){
                    listCustomer.add(new Guest(guestID,guestBudget));
                    System.out.println("CREATE GUEST SUCCESS: " + guestID);
                }
                //input CREATE MENU
            } else if (input[1].equals("MENU")) {
                data = input[3].split("\\|");
                if(input[2].equals("MOBIL")){
                    String menuID = data[0];
                    String menuName = data[1];
                    String numberPlate = data[2];
                    int price = Integer.parseInt(data[3]);
                    String customType = data[4];
                    if(!isExistMenuID(listMenu,menuID) || !isExistNumberPlate(listMenu,numberPlate)){
                        listMenu.add(new Order(menuID,menuName,numberPlate,price,customType));
                        System.out.println("CREATE MENU SUCCESS " + menuID + " "+menuName+ " " + numberPlate );
                    }
                } else if (input[2].equals("MOTOR")) {
                    String menuID = data[0];
                    String menuName = data[1];
                    String numberPlate = data[2];
                    int price = Integer.parseInt(data[3]);
                    if(!isExistMenuID(listMenu,menuID) || !isExistNumberPlate(listMenu,numberPlate)) {
                        listMenu.add(new Order(menuID, menuName, numberPlate, price));
                         System.out.println("CREATE MENU SUCCESS " + menuID + " "+menuName+ " " + numberPlate );
                    }
                }
                // input create promo
            } else if (input[1].equals("PROMO")) {
                if (input[2].equals("CASHBACK")) {
                    data = input[3].split("\\|");
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
                } else if (input[2].equals("DISCOUNT")) {
                    data = input[3].split("\\|");
                    String discountPromoCode = data[0];
                    LocalDate startDateDiscount = LocalDate.parse(data[1], formatter);
                    LocalDate endDateDiscount = LocalDate.parse(data[2], formatter);
                    int percentOff = Integer.parseInt(data[3].replace("%", ""));
                    int maximumDiscount = Integer.parseInt(data[4]);
                    int minPurchase = Integer.parseInt(data[5]);
                    if(!isExistPromo(listPromotion,discountPromoCode)) {
                        listPromotion.add(new Discount(discountPromoCode, startDateDiscount, endDateDiscount, percentOff, maximumDiscount, minPurchase));
                        System.out.println("CREATE PROMO DISCOUNT SUCCESS: " + discountPromoCode);
                    }
                }
            }
        }

        if(input[0].equals("ADD_TO_CART")){
            String addOrderID = input[1];
            String addMenuID = input[2];
            int addQuantity = Integer.parseInt(input[3]);
            LocalDate addStartLoanDate = LocalDate.parse(input[4], formatter);
            if (isCustomerExist(listCustomer, addOrderID) && isExistMenuID(listMenu, addMenuID)) {
                Customer customer = getCustomer(listCustomer, addOrderID);
                listCustomer.remove(customer);
                Order order = customer.getOrder(addOrderID);
                boolean isUpdated;
                if (customer.isOrderExist(addMenuID)) {
                    order.updateDuration(addQuantity);
                    isUpdated = true;
                }
                else {
                    customer.addToCart(new Order(addMenuID, addQuantity, addStartLoanDate));
                    isUpdated = false;
                }
                listCustomer.add(customer);
                System.out.printf("ADD_TO_CART SUCCESS: %d %s %s %s %s", addQuantity, (addQuantity > 1 ? "days" : "day"), order.getMenuName(), order.getNumberPlate(), (isUpdated ? "(UPDATED)" : "(NEW)"));
            }
            else {
                System.out.println("ADD_TO_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
            }
        }
        if(input[0].equals("REMOVE_FROM_CART")){
            String removeOrderID = input[1];
            String removeMenuID = input[2];
            int removeQuantity = Integer.parseInt(input[3]);
        }
        if(input[0].equals("APPLY_PROMO")){
            String applyOrderID = input[1];
            String applyPromoCod = input[2];
        }
        if(input[0].equals("TOPUP")){
            String topUpOrderID = input[1];
            int topUpBudget = Integer.parseInt(input[2]);
        }
        if(input[0].equals("CHECK_OUT")){
            String checkoutOrderID = input[1];
        }
        if(input[0].equals("PRINT")){
            String printOrderID = input[1];
        }
        if(input[0].equals("PRINT_HISTORY")) {
            String printHisOrderID = input[1];

        }






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
}
