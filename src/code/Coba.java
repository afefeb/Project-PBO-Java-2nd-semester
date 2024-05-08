package code;
import code.customer.Guest;
import code.customer.Member;
import code.vehicle.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Coba {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //simpan data untuk login

        Data dataUser = new Data();
        dataUser.addUser("Afefeb","12765");


        System.out.println("Are you a guest or a member? (member/guest)");
        String userType = sc.nextLine();

        if(userType.equalsIgnoreCase("member")) {
            // Pertama untuk login member
            System.out.print("Input Username : ");
            String username = sc.nextLine();
            System.out.print("Input Password : ");
            String password = sc.nextLine();
            if (dataUser.isMember(username, password)) {
                System.out.println("Login successful!");
                Member member = new Member();
                System.out.print("Input First Name : ");
                member.setFirstName(sc.nextLine());
                System.out.print("Input Last Name : ");
                member.setLastName(sc.nextLine());


            } else {
                System.out.println("Incorrect username or password.");
            }
            //untuk guest
        } else if (userType.equalsIgnoreCase("guest")) {
            Guest guest = new Guest();
            guest.setFirstName("John");
            guest.setLastName("Doe");

        }


        // Contoh penggunaan kelas-kelas yang telah dibuat
        // Buat kendaraan
        LargeVehicle largeVehicle = new LargeVehicle();
        MediumVehicle mediumVehicle = new MediumVehicle();
        SmallVehicle smallVehicle = new SmallVehicle();

        // Buat order untuk kendaraan tersebut
        Order order = new Order(LocalDate.now(), 1001, 5000, 2, largeVehicle);
        order.checkOut();
        order.printDetails();

        // Buat pelanggan


        // Tampilkan nama lengkap pelanggan
        System.out.println("Full Name: " + guest.getFullName());

        // Buat member


        // Tampilkan nama lengkap member dan durasi keanggotaan
        System.out.println("Full Name: " + member.getFullName());
        System.out.println("Membership Duration: " + member.membershipDuration());
    }
}
