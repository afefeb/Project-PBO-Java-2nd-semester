import java.time.LocalDate;

public class Coba {
    public static void main(String[] args) {
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
        Guest guest = new Guest();
        guest.setFirstName("John");
        guest.setLastName("Doe");

        // Tampilkan nama lengkap pelanggan
        System.out.println("Full Name: " + guest.getFullName());

        // Buat member
        Member member = new Member(2022, 1, 1);
        member.setFirstName("Jane");
        member.setLastName("Smith");

        // Tampilkan nama lengkap member dan durasi keanggotaan
        System.out.println("Full Name: " + member.getFullName());
        System.out.println("Membership Duration: " + member.membershipDuration());
    }
}
