package code.customer;

import code.customer.Customer;
import java.util.ArrayList;

public class Guest extends Customer {
    
    public Guest() {
        super();
    }

    @Override
    public void makeOrder() {
        isOrdering = true;
    }

    @Override
    public boolean confirmPay(int orderNumber) {
        if (IDOrderList.containsKey(orderNumber)) {
            IDOrderList.put(orderNumber, true);
            System.out.println("This ID is valid. Please proceed with the payment.");
            return true;
        }
        else {
            System.out.println("This ID is invalid.");
            return false;
        }
    }
}
