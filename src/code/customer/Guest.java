package code.customer;

public class Guest extends Customer {

    public Guest(String guestID, int guestBudget) {
        super(guestID,guestBudget);
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
        } else {
            System.out.println("This ID is invalid.");
            return false;
        }
    }
}
