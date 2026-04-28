package billing;

import domain.user.User;
import money.Money;

import java.time.LocalDateTime;

public class Invoice {
    private final String invoiceNumber; // np. INV-<yyyyMMdd>-<counter>
    private final LocalDateTime issueDate;
    private final User buyer;
    private final Money total;
    private String itemDescription; // np."Rezerwacja <resource> <start–end>"

    public Invoice(String invoiceNumber, LocalDateTime issueDate, User buyer, Money total, String itemDescription) {
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.buyer = buyer;
        this.total = total;
        this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
        return String.format(
                "Invoice Number: '%s', Issue Date: %s, Buyer: %s, Total price: %s, Item Description: '%s'",
                invoiceNumber,
                issueDate,
                buyer,
                total,
                itemDescription);
    }
}
