import java.time.LocalDate;
import java.time.Period;

public class Member extends Customer {
    private LocalDate tanggalMendaftar;

    public Member(int tanggalDaftar, int bulanDaftar, int tahunDaftar) {
        super();
        this.tanggalMendaftar = LocalDate.of(tanggalDaftar, bulanDaftar, tahunDaftar);
    }

    @Override
    public void makeOrder() {
        isOrdering = true;
    }

    public String hitungLamaKeanggotaan() {
        LocalDate hariIni = LocalDate.now();
        Period periode = Period.between(tanggalMendaftar, hariIni);

        int tahun = periode.getYears();
        int bulan = periode.getMonths();

        if (tahun == 0) {
            return bulan + " bulan";
        } else if (bulan == 0) {
            return tahun + " tahun";
        } else {
            return tahun + " tahun dan " + bulan + " bulan";
        }
    }

    @Override
    public void confirmPay(int nomorPesanan) {

    }
}
