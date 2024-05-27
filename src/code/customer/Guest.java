package code.customer;

import java.time.LocalDate;
public class Guest extends Customer {
    public Guest(String guestID, int guestBudget) {
        super(guestID,guestBudget);
    }

    public void checkOut() {
        if (getBalance() >= calculateTotalPurchase()) {
            System.out.println("CHECK_OUT SUCCESS: " + getId() + " GUEST");
            updateBalance(-calculateTotalPurchase());
            orderHistory.put(orderCounter, listOrder);
            checkOutDate = LocalDate.now();
            orderCounter++;
            this.reset();
        } else {
            System.out.println("CHECK_OUT FAILED: " + getId() + " GUEST INSUFFICIENT BALANCE");
        }
    }
}
