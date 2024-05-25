package code.promotion;
import code.customer.Customer;

import java.time.LocalDate;

public abstract class Promotion implements Applicable{
    protected String promoCode;
    protected LocalDate startDate;
    protected LocalDate endDate ;

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
    public boolean isPromoAvailable() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(startDate) && currentDate.isBefore(endDate);
    }
}
