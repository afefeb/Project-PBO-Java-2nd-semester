package code.customer;

import code.promotion.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Member extends Customer {
    private LocalDate date;
    private String memberName;
    private double discount;
    private double cashback;
    private Promotion promo;
    private Map<Integer, Promotion> promoHistory = new HashMap<>();

    public Member(String memberID, String memberName, LocalDate date, int memberBalance){
        super(memberID,memberBalance);
        this.memberName = memberName;
        this.date = date;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getFirstName() {
        return memberName.split(" ")[0];
    }

    public LocalDate getDate() {
        return date;
    }

    public void applyPromo(ArrayList<Promotion> listPromo, String promoCode) {
        if (isPromoApplied()) {
            System.out.println("APPLY_PROMO FAILED: " + promoCode);
            return;
        }
        for (Promotion promotions : listPromo) {
            if (promotions.getPromoCode().equals(promoCode)) {
                if (promotions.isPromoAvailable()) {
                    if (promotions.isCustomerEligible(this)) {
                        if (promotions instanceof Discount) {
                            promo = (Discount) promotions;
                            discount = promo.getPercentOff() / 100.0 * getSubTotal();
                            double temp = 0;
                            if (discount > promo.getMaxDiscount()) {
                                temp = promo.getMaxDiscount();
                                discount = temp;
                            } else {
                                temp = discount;
                            }
                            setTotalPurchase(calculateTotalPurchase());
                            Discount tempPromo = (Discount) promo;
                            promoHistory.put(getCurrentOrderNumber(), tempPromo);
                        }
                        else if (promotions instanceof CashbackPromo) {
                            promo = (CashbackPromo) promotions;
                            cashback = promo.getPercentOff() / 100.0 * getSubTotal();
                            CashbackPromo tempPromo = (CashbackPromo) promo;
                            promoHistory.put(getCurrentOrderNumber(), tempPromo);
                        }
                        System.out.println("APPLY_PROMO SUCCESS: " + promoCode);
                    } else {
                        System.out.println("APPLY_PROMO FAILED: " + promoCode);
                    }
                    return;
                } else {
                    System.out.println("APPLY_PROMO FAILED: " + promoCode + " is EXPIRED");
                    return;
                }
            }
        }
        System.out.println("APPLY_PROMO FAILED: " + promoCode);
    }

    public void checkOut() {
        if (getBalance() >= calculateTotalPurchase() && isOrdering()) {
            if (promo instanceof CashbackPromo) {
                updateBalance(cashback);
            }
            System.out.println("CHECK_OUT SUCCESS: " + getId() + " " + memberName);
            updateBalance(-calculateTotalPurchase() + getCashbackAmount());
            orderHistory.put(orderCounter, listOrder);
            subTotalHistory.put(orderCounter, getSubTotal());
            checkOutDate = LocalDate.now();
            orderCounter++;
            this.reset();
        } else {
            System.out.println("CHECK_OUT FAILED: " + getId() + " " + memberName + " INSUFFICIENT BALANCE");
        }
    }

    public Promotion getPromo() {
        return promo;
    }

    public Promotion getPromoFromHistory() {
        return promoHistory.get(getCurrentOrderNumber());
    }

    public Promotion getPromoByIndex(int index) {
        return promoHistory.get(index);
    }

    public boolean isPromoFromHistoryApplied() {
        return promoHistory.get(getCurrentOrderNumber()) != null;
    }

    public boolean isPromoApplied() {
        return promo != null;
    }

    public double calculateDiscount() {
        if (isOrdering()) {
            return promo.getPercentOff() * getSubTotal() / 100.0;
        } else {
            return promoHistory.get(getCurrentOrderNumber()).getPercentOff() * getSubTotal() / 100.0;
        }
    }

    public double getCashbackAmount() {
        return cashback;
    }

    @Override
    public void reset() {
        super.reset();
        this.discount = 0.0;
        this.cashback = 0;
        this.promo = null;
    }
}