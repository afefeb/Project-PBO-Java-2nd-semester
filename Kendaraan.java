
public interface Kendaraan {

    String getNomorKendaraan();
    int getKapasitas();
    int getHarga();
    Boolean isAvailable(String nama);


} 

class kendaraanBesar implements Kendaraan{
    
    @Override
    public int getHarga() {
        return 0;
    }

    @Override
    public int getKapasitas() {
        return 0;
    }

    @Override
    public String getNomorKendaraan() {
        return null;
    }

    @Override
    public Boolean isAvailable(String nama) {
        return null;
    }
    
}
class kendaraanSedang implements Kendaraan{

    @Override
    public int getHarga() {
        return 0;
    }

    @Override
    public int getKapasitas() {
        return 0;
    }

    @Override
    public String getNomorKendaraan() {
        return null;
    }

    @Override
    public Boolean isAvailable(String nama) {
        return null;
    }

}
class kendaraanKecil implements Kendaraan{

    @Override
    public int getHarga() {
        return 0;
    }

    @Override
    public int getKapasitas() {
        return 0;
    }

    @Override
    public String getNomorKendaraan() {
        return null;
    }

    @Override
    public Boolean isAvailable(String nama) {
        return null;
    }
    
}
