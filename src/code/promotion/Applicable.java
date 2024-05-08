package code.promotion;

import code.Order;
import code.customer.Customer;

public interface Applicable {
    boolean isCustomerEligible(Customer customer);
    boolean isMinimumPriceEligible(Order order);

    boolean isShippingFeeEligbile(Order x);

    double calculateDiscount(Order order);
    double calculateCashback(Order order);
    double calculateShippingCost(Order order);

}
