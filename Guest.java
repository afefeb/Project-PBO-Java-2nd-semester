class Guest extends Customer {

    public Guest() {
        super();
    }

    @Override
    public void makeOrder() {
        isOrdering = true;
    }

    @Override
    public void confirmPay(int nomorPesanan) {

    }
}
