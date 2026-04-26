package payment;

import domain.user.User;
import money.Money;

import java.time.LocalDateTime;

public class Invoice {
    private final String invoiceNumber;
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
        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", issueDate=" + issueDate +
                ", buyer=" + buyer +
                ", total=" + total +
                ", itemDescription='" + itemDescription + '\'' +
                '}';
    }
}
