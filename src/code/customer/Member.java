package code.customer;

import java.time.LocalDate;
import java.time.Period;

public class Member extends Customer {
    private LocalDate date;
    private String memberName;

    public Member(String memberID, String memberName, LocalDate date, int memberBalance){
        super(memberID,memberBalance);
        this.memberName = memberName;
        this.date = date;
    }

    @Override
    public void makeOrder(){
            isOrdering = true;
    }

    public String membershipDuration() {
        LocalDate today = LocalDate.now();
        Period period = Period.between(date, today);

        int year = period.getYears();
        int month = period.getMonths();

        if (year == 0) {
            return month + " month";
        } else if (month == 0) {
            return year + " year";
        } else {
            return year + " year and " + month + " month";
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

    public String getMemberName() {
        return memberName;
    }

    public LocalDate getDate() {
        return date;
    }
}
