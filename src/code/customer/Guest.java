package code.customer;

import code.customer.Customer;

public class Guest extends Customer {

    public Guest() {
        super();
    }

    @Override
    public void makeOrder() {
        isOrdering = true;
    }

    @Override
    public void confirmPay(int orderNumber) {

    }
}
