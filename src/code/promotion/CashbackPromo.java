package code.promotion;

import code.Order;
import code.customer.Customer;
import code.customer.Guest;
import code.customer.Member;

import java.time.LocalDate;

public class CashbackPromo extends Promotion implements  Applicable{

    @Override
    public boolean isCustomerEligible(Customer customer) {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMinimumPriceEligible(Order order) {
        return order.calculatePrice() >= 300000;
    }

    @Override
    public boolean isShippingFeeEligbile(Order x) {
        return false;
    }

    @Override
    public double calculateDiscount(Order order) {
        return 0;
    }

    @Override
    public double calculateCashback(Order order) {
        return 25000;
    }

    @Override
    public double calculateShippingCost(Order order) {
        return 0;
    }


}
