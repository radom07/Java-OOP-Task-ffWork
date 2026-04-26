import domain.user.IndividualUser;
import domain.user.User;
import money.Money;
import payment.CardPayment;
import payment.Invoice;
import payment.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentTest {
    public static void main(String[] args) {
        Money amountToPay = new Money(new BigDecimal("150.00"));
        Payment payment = new CardPayment( amountToPay, "PAY-123", "4242");

        System.out.println("Status start: " + payment.getStatus());

        payment.capture();
        System.out.println("Status after capture() " + payment.getStatus());

        try {
            payment.capture();
        } catch (IllegalStateException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        System.out.println("===INVOICE CLASS TEST===");

        Money invoiceTotal = new Money(new BigDecimal("160.00"));
        LocalDateTime issueDate = LocalDateTime.of(2025, 9, 15, 12, 15);

        IndividualUser testBuyer = new IndividualUser(
                "anna.nowak@gmail.com",
                "Anna Nowak");

        Invoice invoice = new Invoice(
                "INV-20250915-1",
                issueDate,
                testBuyer,
                invoiceTotal,
                "Reservation Conference Room Alpha 10:00-12:00"
        );

        System.out.println("Expected: Invoice total=160.00 PLN buyer=ACME Sp. z o.o.");
        System.out.println("Actual:   " + invoice);
    }
}
