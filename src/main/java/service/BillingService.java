package service;

import billing.Billable;
import billing.Invoice;
import domain.booking.Booking;
import payment.PaymentStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BillingService implements Billable {
    private int invoiceCounter;

    @Override
    public Invoice toInvoice(Booking booking) {
        /*
        Dodana walidacja z uwagi w ReplCli
        "Faktura na rezerwacji bez płatności nie ma biznesowego sensu — dodaj walidację, że Booking ma payment ze statusem CAPTURED"
        w metodzie private void handleInvoice(String[] parts)
         */
        if (booking.getPayment().getStatus() != PaymentStatus.CAPTURED) {
            throw new IllegalStateException("Cannot issue an invoice for a booking without a captured payment.");
        }
        LocalDateTime issueDate = LocalDateTime.now();
        String invoiceNumber = generateInvoiceNumber(issueDate);
        String itemDescription = getItemDescription(booking);
        return new Invoice(invoiceNumber, issueDate, booking.getUser(), booking.getCalculatedPrice(), itemDescription);
    }

    private String generateInvoiceNumber(LocalDateTime issueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateToNumber = issueDate.format(formatter);

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
