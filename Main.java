import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------Program FILKOM TRAVEL-----------");
        Pelanggan pelanggan = new Pelanggan();
        System.out.print("Masukan nama anda: ");
        pelanggan.setNama(sc.nextLine());
        System.out.println("Masukan tanggal lahir (dd-MM-yyy): ");
        
        Boolean tgl;
        String input = sc.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        do {
            try {
                tgl = true;
                Date date = dateFormat.parse(input);
                pelanggan.setTanggalLahir(date);
            } catch (ParseException e) {
                System.out.println("Format tanggal tidak valid");
                tgl = false;
            }
        } while (tgl == false);

    }
}