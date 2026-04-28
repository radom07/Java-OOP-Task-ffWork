package service;

import billing.Billable;
import billing.Invoice;
import domain.booking.Booking;
import repo.BookingRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BillingService implements Billable {
    private int invoiceCounter;

    @Override
    public Invoice toInvoice(Booking booking) {
        String invoiceNumber = generateInvoiceNumber();
        LocalDateTime issueDate = LocalDateTime.now();
        String itemDescription = getItemDescription(booking);
        return new Invoice(invoiceNumber, issueDate, booking.getUser(), booking.getCalculatedPrice(), itemDescription);
    }

    private String generateInvoiceNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateToNumber = LocalDateTime.now().format(formatter);

        invoiceCounter++;
        String invoiceNumber = String.format("INV-%s-%d", dateToNumber, invoiceCounter);

        return invoiceNumber;
    }

    private static String getItemDescription(Booking booking) {
        String resourceName = booking.getResource().getName();
        LocalDateTime start = booking.getStart().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime end = booking.getEnd().truncatedTo(ChronoUnit.MINUTES);
        String itemDescription = String.format("Reservation %s %s-%s", resourceName, start, end);
        return itemDescription;
    }
}
