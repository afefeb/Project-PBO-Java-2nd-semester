package code.customer;

import java.time.LocalDate;
import java.time.Period;

public class Member extends Customer {
    private LocalDate signUpTime;

    public Member(int signUpDate, int signUpMonth, int signUpYear) {
        super();
        this.signUpTime = LocalDate.of(signUpYear, signUpMonth, signUpDate);
    }

    @Override
    public void makeOrder() {
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
    public void confirmPay(int orderNumber) {

    }
}
