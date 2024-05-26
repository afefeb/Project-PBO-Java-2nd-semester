package code.promotion;

import code.Order;
import code.customer.Customer;

public interface Applicable {
    boolean isCustomerEligible(Customer customer);
    boolean isMinimumPriceEligible(Order order);
}
