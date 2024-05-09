//package code;
//import code.customer.Guest;
//import code.customer.Member;
//import code.vehicle.*;
//import java.time.LocalDate;
//import java.util.Scanner;
//
//public class Coba {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        //simpan data untuk login
//
//        Member member = null;
//        Guest guest = null;
//        Data dataUser = new Data();
//        dataUser.addUser("Afefeb","12765");
//
//        System.out.println("Are you a guest or a member? (member/guest)");
//        String userType = sc.nextLine();
//
//        if(userType.equalsIgnoreCase("member")) {
//            // Pertama untuk login member
//            System.out.print("Input Username : ");
//            String username = sc.nextLine();
//            System.out.print("Input Password : ");
//            String password = sc.nextLine();
//            if (dataUser.isMember(username, password)) {
//                member = new Member();
//                System.out.println("Login successful!");
//                System.out.print("Input First Name : ");
//                member.setFirstName(sc.nextLine());
//                System.out.print("Input Last Name : ");
//                member.setLastName(sc.nextLine());
//
//            } else {
//                System.out.println("Incorrect username or password.");
//            }
//            //untuk guest
//        } else if (userType.equalsIgnoreCase("guest")) {
//            guest = new Guest();
//            guest.setFirstName("John");
//            guest.setLastName("Doe");
//
//        }
//
//
//
//        //Buat order vehicle (ini belum rapi)
//        System.out.println("Vehicle list:");
//        System.out.println("+---------------------------------------------------+");
//        System.out.println("| No. | Vehicle           | Capacity  | Price       |");
//        System.out.println("+---------------------------------------------------+");
//        System.out.println("| 1.  | Large Vehicle     | 8         | Rp. 100.000 |");
//        System.out.println("| 2.  | Medium Vehicle    | 6         | Rp. 50.000  |");
//        System.out.println("| 3.  | Small Vehicle     | 4         | Rp. 25.000  |");
//        System.out.println("+---------------------------------------------------+");
//
//        System.out.print("Input Vehicle Type : ");
//        int vehicleType = sc.nextInt();
//        System.out.print("Input Quantity : ");
//        int quantity = sc.nextInt();
//        System.out.print("Duration (day) : ");
//        int duration = sc.nextInt();
//        Order order = new Order(quantity, duration);
//        if (vehicleType == 1) {
//            order.setVehicle(new LargeVehicle());
//        } else if (vehicleType == 2) {
//            order.setVehicle(new MediumVehicle());
//        } else if (vehicleType == 3) {
//            order.setVehicle(new SmallVehicle());
//        } else {
//
//        }
//        order.checkOut();
//
//        // Menyimpan buat order id
//        member.addIDOrder(order.getOrderNumber());
//
//        System.out.println("Input Order ID to confirm the purchase : ");
//        int orderID = sc.nextInt();
//
//
//        // fungsi membandingkan order id dengan inputan
//        if (member.confirmPay(orderID)) {
//            System.out.println("Enter your amount of money : ");
//            int balance = Integer.parseInt(sc.nextLine());
//            if (balance >= order.calculatePrice()) {
//                order.pay();
//            }
//        } else {
//
//        }
//
//        order.printDetails();
//
//        //--> Promosi <--
//        // di promosi nanti ada comparable untuk menentukan diskon mana yang lebih besar
//
//
//
//        // Buat order untuk kendaraan tersebut
//        /* Order order = new Order(1001, 5000, 2, largeVehicle);
//        order.checkOut();
//        order.printDetails(); */
//
//
//        // Tampilkan nama lengkap member dan durasi keanggotaan
//        /* System.out.println("Full Name: " + member.getFullName());
//        System.out.println("Membership Duration: " + member.membershipDuration()); */
//
//    }
//}
