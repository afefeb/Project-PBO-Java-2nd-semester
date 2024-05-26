package code.customer;

import java.time.LocalDate;
public class Guest extends Customer {
    public Guest(String guestID, int guestBudget) {
        super(guestID,guestBudget);
    }

    public void checkOut() {
        if (getBalance() >= getTotalPurchase()) {
            System.out.println("CHECK_OUT SUCCESS: " + getId() + " GUEST");
            updateBalance(-getTotalPurchase());
            orderHistory.put(orderCounter, listOrder);
            checkedOut = true;
            checkOutDate = LocalDate.now();
            orderCounter++;
        } else {
            System.out.println("CHECK_OUT FAILED: " + getId() + " GUEST INSUFFICIENT BALANCE");
        }
    }
}
