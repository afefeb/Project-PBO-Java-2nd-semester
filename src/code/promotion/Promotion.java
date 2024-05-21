package code.promotion;
import java.time.LocalDate;

public abstract class Promotion implements Applicable{
    protected String promoCode;
    protected LocalDate startDate = LocalDate.of(2024, 5, 9);
    protected LocalDate endDate = LocalDate.of(2024, 6, 9);

    protected int percentOff;
    protected int maxDiscount;
    protected int minPurchase;

    public Promotion(String promoCode, LocalDate startDate, LocalDate endDate, int percentOff, int maxDiscount, int minPurchase) {
        this.promoCode = promoCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentOff = percentOff;
        this.maxDiscount = maxDiscount;
        this.minPurchase = minPurchase;
    }

    public String getPromoCode() {
        return promoCode;
    }
}
