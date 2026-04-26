package payment;

import money.Money;

public abstract class Payment {
    private final Money amount;
    private final String paymentId;
    private PaymentStatus status;

    public Payment(Money amount, String paymentId) {
        this.amount = amount;
        this.paymentId = paymentId;
        this.status = PaymentStatus.INITIATED;
    }

    public abstract void capture();

    public PaymentStatus getStatus() {
        return status;
    }

    protected void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
