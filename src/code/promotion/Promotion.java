package code.promotion;
import code.customer.Customer;
import code.customer.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Promotion implements Applicable{
    protected String promoCode;
    protected LocalDate startDate;
    protected LocalDate endDate ;

    protected double percentOff;
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

    public double getPercentOff() {
        return percentOff;
    }

    @Override
    public boolean isCustomerEligible(Customer customer) {
        Member member = (Member) customer;
        LocalDate registerDate = member.getDate();
        return (ChronoUnit.DAYS.between(registerDate, LocalDate.now()) > 30 && member.calculateTotalPurchase() >= minPurchase);
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }
}
