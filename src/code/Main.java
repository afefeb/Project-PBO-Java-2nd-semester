package code;

import code.customer.*;
import code.promotion.CashbackPromo;
import code.promotion.PercentOffPromo;
import code.vehicle.*;

import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        int choice, countLogin = 0;
        Customer current = null;
        boolean isLogin = false;
        do {
            Order order = new Order();
            System.out.println("WELCOME TO FILKOM TRAVEL");
            showMenu();
            System.out.print("PLEASE ENTER YOUR CHOICE : ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0:
                    System.out.println("Thank You ! ");
                    break;
                case 1:
                    if (!isLogin) {
                        order = new Order();
                        Customer a = null;
                        while (a == null) {
                            System.out.println("Are you a guest or a member? (member/guest)");
                            String subscriptionType = sc.nextLine();
                            a = (order.register(sc, subscriptionType));
                            if (a == null) {
                                System.out.println("The option is not available. Please select again.");
                            }
                        }
                        customers.add(a);
                        System.out.println("Account successfully created. Here is your account information : ");
                        showInfoAccount(a);
                        System.out.println("Please login!");
                    } else {
                        System.out.println("Please logout first!");
                    }
                    break;
                case 2:
                    if (!isLogin) {
                        order = new Order();
                        String[] dataLogin = (order.login(sc, customers));
                        boolean found = false;
                        for (Customer a1 : customers) {
                            if (a1.getFullName().equalsIgnoreCase(dataLogin[0])) {
                                if (a1.getId().equals(dataLogin[1])) {
                                    if (a1.getPin().equals(dataLogin[2])) {
                                        found = true;
                                        current = a1;
                                        isLogin = true;
                                        countLogin = 0;
                                        System.out.println("Successful Login");
                                        break;
                                    } else {
                                        System.out
                                                .println("Your pin is incorrect. You only have " + (2 - countLogin)
                                                        + " login opportunities left");
                                        countLogin++;
                                        if (countLogin >= 3) {
                                            a1.blockAccount();
                                            System.out.println(
                                                    "Your account has been banned due to repeated login errors.");
                                        }
                                    }
                                }
                            }
                        }
                        if (!found) {
                            System.out.println("Account not found. Please register first!");
                        }
                    } else {
                        System.out.println("Please logout first!");
                    }
                    break;
                case 3:
                    if (!isLogin) {
                        System.out.println("Please login first!");
                        break;
                    } else {
                        showInfoAccount(current);
                    }
                    break;
                case 4:
                    if (!isLogin) {
                        System.out.println("Please login first!");
                        break;
                    } else {
                        System.out.println("You are doing a top up.");
                        System.out.println("Enter the amount : ");
                        double x = sc.nextDouble();
                        sc.nextLine();
                        current.addBalance(x);
                        System.out.println("Balance successfully increased. Thank you!");
                    }
                    break;
                case 5:
                    if (!isLogin) {
                        System.out.println("Please login first!");
                        break;
                    } else {
                        tampilkanVehicle();
                        Vehicle vehicle = null;
                        boolean lanjut = false;
                        do {
                            System.out.print("Input Vehicle Type Number : ");
                            int vehicleType = sc.nextInt();
                            switch (vehicleType) {
                                case 1:
                                    vehicle = new LargeVehicle();
                                    lanjut = true;
                                    break;
                                case 2:
                                    vehicle = new MediumVehicle();
                                    lanjut = true;
                                    break;
                                case 3:
                                    vehicle = new SmallVehicle();
                                    lanjut = true;
                                    break;
                                default:
                                    System.out.println("Invalid selection. Please select again!");
                                    break;
                            }
                        } while (!lanjut);

                        System.out.print("Input Vehicle Quantity : ");
                        int quantity = sc.nextInt();
                        System.out.print("Input Duration (day) : ");
                        int duration = sc.nextInt();
                        order = new Order();
                        order.setVehicle(vehicle);
                        order.setVehicleQuantity(quantity);
                        order.setDuration(duration);
                        double totalPrice = order.calculatePrice();
                        if (totalPrice > current.getBalance()) {
                            System.out.println("Sorry, you don't have enough balance.");
                        } else {
                            System.out.println("Total price : " + DecimalFormat.getCurrencyInstance().format(totalPrice));
                            order.applyPromo(new CashbackPromo());
                            order.applyPromo(new PercentOffPromo());
                            current.addIDOrder(order.getOrderNumber());
                            order.checkOut();
                        }
                    }
                    break;
                case 6:
                    if (!isLogin) {
                        System.out.println("Please login first!");
                        break;
                    }
                    System.out.println("Input Order ID to confirm the purchase : ");
                    int orderID = sc.nextInt();
                    current.confirmPay(orderID);
                    if(current.confirmPay(orderID)){
                        System.out.print("Enter to Pay 1 (yes) or 0 (no) : ");
                        int choicePay = sc.nextInt();
                        if(choicePay == 1){
                            current.minBalance(order.getTotalPrice());
                            System.out.println("Current balance : " + DecimalFormat.getCurrencyInstance().format(current.getBalance()));
                            System.out.println("Purchase successful!");
                            order.printDetails();
                        }else{
                            System.out.println("Failed to Purchase");
                        }
                    }
                case 7:
                    if (!isLogin) {
                        System.out.println("Please login first!");
                        break;
                    } else {
                        current = null;
                        isLogin = false;
                    }
                    break;
                default:
                    System.out.println("Invalid selection. Please select again!");
                    break;
            }
        } while (choice != 0);
        sc.close();
    }

    public static void showMenu() {
        System.out.println("MENU : ");
        System.out.println("0. Exit");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Show Account Information");
        System.out.println("4. Top Up");
        System.out.println("5. Order");
        System.out.println("6. Pay");
        System.out.println("7. Log out");
    }

    public static void showInfoAccount(Customer account) {
        System.out.println("Name : " + account.getFullName());
        System.out.println("ID : " + account.getId());
        System.out.println("Balance : " + DecimalFormat.getCurrencyInstance().format(account.getBalance()));
    }

    public static void tampilkanVehicle() {
        System.out.println("Vehicle list:");
        System.out.println("+----------------------------------------------------+");
        System.out.println("| No. | Vehicle           | Capacity  | Price        |");
        System.out.println("+----------------------------------------------------+");
        System.out.println("| 1.  | Large Vehicle     | 8         | Rp. 250.000  |");
        System.out.println("| 2.  | Medium Vehicle    | 6         | Rp. 150.000  |");
        System.out.println("| 3.  | Small Vehicle     | 4         | Rp. 100.000  |");
        System.out.println("+----------------------------------------------------+");

    }
}