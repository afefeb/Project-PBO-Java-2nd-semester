package code;

import code.customer.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Customer[] pelanggan = new Customer[3];
        Kendaraan kendaraan;

        System.out.println("===========================================");
        System.out.println("|          Program FILKOM TRAVEL          |");
        System.out.println("===========================================");

        for (int i = 0; i < pelanggan.length; i++) {
            pelanggan[i] = new Customer();
            Pemesanan pemesanan = new Pemesanan();
            System.out.print("Masukkan nama Anda: ");
            pemesanan.setNamaPelanggan(sc.nextLine());
            Boolean tanggal;
            long durasi = 0;

            do {
                do {
                    try {
                        System.out.print("Masukkan tanggal awal sewa (format: dd-MM-yyyy): ");
                        String tanggalAwalString = sc.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate tanggalAwal = LocalDate.parse(tanggalAwalString, formatter);
                        // pemesanan.setTanggalAwal(tanggalAwal);
                        tanggal = true;
                    } catch (Exception e) {
                        System.out.println("Format tanggal tidak valid");
                        tanggal = false;
                    }
                } while (tanggal == false);

                do {
                    try {
                        System.out.print("Masukkan tanggal akhir sewa (format: dd-MM-yyyy): ");
                        String tanggalAkhirString = sc.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate tanggalAkhir = LocalDate.parse(tanggalAkhirString, formatter);
                        // pemesanan.setTanggalAkhir(tanggalAkhir);
                        tanggal = true;
                    } catch (Exception e) {
                        System.out.println("Format tanggal tidak valid");
                        tanggal = false;
                    }
                } while (tanggal == false);

                // durasi = ChronoUnit.DAYS.between(pemesanan.getTanggalAwal(),
                // pemesanan.getTanggalAkhir());
                pemesanan.setDurasi(durasi);

                if (!pemesanan.validasiTanggal(durasi)) {
                    System.out.println("Tanggal akhir sewa tidak boleh lebih awal dari tanggal awal sewa");
                    tanggal = false;
                }
            } while (tanggal == false);

            System.out.print("Masukkan alamat: ");
            pemesanan.setAlamat(sc.nextLine());
            System.out.print("Masukkan nomor telepon: ");
            pemesanan.setNomorTelepon(sc.nextLine());
            System.out.println();

            int penumpang;

            do {
                System.out.println("Daftar Kendaraan:");
                System.out.println("+---------------------------------------------------+");
                System.out.println("| No. | Kendaraan         | Kapasitas | Harga       |");
                System.out.println("+---------------------------------------------------+");
                System.out.println("| 1.  | Kendaraan Besar   | 8         | Rp. 100.000 |");
                System.out.println("| 2.  | Kendaraan Sedang  | 6         | Rp. 50.000  |");
                System.out.println("| 3.  | Kendaraan Kecil   | 4         | Rp. 25.000  |");
                System.out.println("+---------------------------------------------------+");
                System.out.print("Masukkan jumlah penumpang: ");
                penumpang = sc.nextInt();
                sc.nextLine();

                if (penumpang <= 4 && penumpang > 0) {
                    kendaraan = new KendaraanKecil();
                    pemesanan.setKendaraan(kendaraan);
                } else if (penumpang <= 6) {
                    kendaraan = new KendaraanSedang();
                    pemesanan.setKendaraan(kendaraan);
                } else if (penumpang <= 8) {
                    kendaraan = new KendaraanBesar();
                    pemesanan.setKendaraan(kendaraan);
                } else {
                    System.out.println("Jumlah penumpang maksimal 8");
                    pemesanan.setKendaraan(null);
                }
            } while (penumpang > 8 || penumpang <= 0);
            pemesanan.setJumlahPenumpang(penumpang);
            pemesanan.setIdPemesanan(i + 1);
            pelanggan[i].setPemesanan(pemesanan);
            System.out.println();
            pelanggan[i].getPemesanan().display();
            System.out.println();
        }
        sc.close();
    }
}
