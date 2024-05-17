package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {
    private static final double MINIMUM_PAYMENT = 50000;

    @Override
    public long processPayment(long saldo, long amount) throws Exception {
        if (amount < MINIMUM_PAYMENT) {
            throw new Exception("The amount of price must past Rp 50000!");
        }

        if (saldo < amount) {
            throw new Exception("Your Balance is low, please use another payment method.");
        }

        return amount;
    }
}