package code.promotion;

public interface Applicable {
    boolean isCustomerEligible(Customer customer);
    boolean isMinimumPriceEligible(Order order);

    boolean isShippingFeeEligbile(Order x);

    double calculateDiscount(Order order);
    double calculateCashback(Order order);
    double calculateShippingCost(Order order);

}
