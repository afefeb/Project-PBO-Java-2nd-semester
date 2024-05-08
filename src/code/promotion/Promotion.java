package code.promotion;
import java.time.LocalDate;
public abstract class Promotion {
    protected String promoCode;
    protected LocalDate startDate;
    protected LocalDate endDate;
    public Promotion(String promoCode, LocalDate startDate, LocalDate endDate) {
        this.promoCode = promoCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    //Ini masih gak tau promosinya buat apa
    public abstract void applyPromotion();
}
