package code.customer;

import code.promotion.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Member extends Customer {
    private LocalDate date;
    private String memberName;
    private double discount;
    private int cashback;
    private Promotion promo;

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
                            discount = promotions.getPercentOff();
                            double temp = 0;
                            if (discount / 100.0 * getSubTotal() > promotions.getMaxDiscount()) {
                                temp = promotions.getMaxDiscount();
                            } else {
                                temp = discount / 100.0 * getSubTotal();
                            }
                            setTotalPurchase(getTotalPurchase() - (temp > promotions.getMaxDiscount() ? promotions.getMaxDiscount() : (double) discount / 100.0 * getTotalPurchase()));
                            promo = (Discount) promotions;
                        }
                        else if (promotions instanceof CashbackPromo) {
                            cashback = promotions.getPercentOff();
                            promo = (CashbackPromo) promotions;
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
        if (getBalance() >= getTotalPurchase()) {
            System.out.println("CHECK_OUT SUCCESS: " + getId() + " " + memberName);
            updateBalance(-getTotalPurchase() + getCashbackAmount());
            orderHistory.put(orderCounter, listOrder);
            checkedOut = true;
            checkOutDate = LocalDate.now();
            orderCounter++;
        } else {
            System.out.println("CHECK_OUT FAILED: " + getId() + " " + memberName + " INSUFFICIENT BALANCE");
        }
    }

    public Promotion getPromo() {
        return promo;
    }

    public boolean isPromoApplied() {
        return promo != null;
    }

    public double calculateDiscount() {
        return promo.getPercentOff() * getSubTotal() / 100.0;
    }

    public int getCashbackAmount() {
        return cashback;
    }
}
