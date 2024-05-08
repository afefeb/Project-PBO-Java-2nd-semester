abstract class Customer {
    private String firstName, lastName;
    public boolean isOrdering;

    public Customer() {
        this.firstName = "";
        this.lastName = "";
        this.isOrdering = false;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    public abstract void makeOrder();

    public abstract void confirmPay(int orderNumber);
}
