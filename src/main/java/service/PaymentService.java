package service;

import domain.booking.Booking;
import money.Money;
import payment.CardPayment;
import payment.Payment;
import repo.BookingRepository;

import java.util.Optional;

public class PaymentService {
    private final BookingRepository bookingRepository;

    public PaymentService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Payment pay(String bookingId, String cardLast4) {
        Optional<Booking> foundBooking = bookingRepository.findById(bookingId);
        if (foundBooking.isEmpty())
            throw new IllegalArgumentException("No booking found");

        Booking booking = foundBooking.get();
        Money amount = booking.getCalculatedPrice();

        CardPayment payment = new CardPayment(amount, bookingId, cardLast4);
        payment.capture();
        booking.setPayment(payment);
        return payment;
    }
}
