package code.customer;

import java.time.LocalDate;
import java.time.Period;

public class Member extends Customer {
    private LocalDate signUpTime;

    public Member() {
        super();
        this.signUpTime = LocalDate.now();
    }

    @Override
    public void makeOrder(){
            isOrdering = true;
    }

    public String membershipDuration() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(signUpTime, today);

        int year = period.getYears();
        int month = period.getMonths();

        if (year == 0) {
            return month + " bulan";
        } else if (month == 0) {
            return year + " tahun";
        } else {
            return year + " tahun dan " + month + " bulan";
        }
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
