package code.promotion;

import code.Order;
import code.customer.Customer;
import code.customer.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Discount extends Promotion{
    public Discount(String promoCode, LocalDate startDate, LocalDate endDate, int percentOff,int maxDiscount,int minPurchase){
        super(promoCode, startDate, endDate, percentOff, maxDiscount, minPurchase);

    }

    @Override
    public boolean isCustomerEligible(Customer customer) {
        Member member = (Member) customer;
        LocalDate registerDate = member.getDate();
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(registerDate, currentDate) > 30;
    }

    @Override
    public boolean isMinimumPriceEligible(Order order) {
        return order.calculatePrice() >= 400000;
    }

    @Override
    public boolean isShippingFeeEligbile(Order x) {
        return false;
    }

    @Override
    public double calculateDiscount(Order order) {
        return order.calculatePrice() * 0.10;
    }

    @Override
    public double calculateCashback(Order order) {
        return 0;
    }

    @Override
    public double calculateShippingCost(Order order) {
        return 0;
    }
}