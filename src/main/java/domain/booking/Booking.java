package domain.booking;

import domain.resource.Resource;
import domain.user.User;
import money.Money;
import payment.Payment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Booking {
    private final String id;
    private final User user;
    private final Resource resource;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private BookingStatus status;
    private final Money calculatedPrice;
   private Payment payment;

    public Booking(String id, User user, Resource resource, LocalDateTime start, LocalDateTime end, Money calculatedPrice, Payment payment) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end cannot be null");
        }
        if (!end.isAfter(start))
            throw new IllegalArgumentException("Start date must be before end date");
        this.id = id;
        this.user = user;
        this.resource = resource;
        this.start = start;
        this.end = end;
        this.calculatedPrice = calculatedPrice;
        this.status = BookingStatus.PENDING;
        this.payment = payment;
    }

    public void confirm() {
        if (this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only pending bookings can be confirmed");
        }
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status != BookingStatus.PENDING && this.status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only pending or confirmed bookings can be cancelled");
        }
        this.status = BookingStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed bookings can be completed");
        }
        this.status = BookingStatus.COMPLETED;
    }

    public int durationMinutes() {
        return (int) ChronoUnit.MINUTES.between(start, end);
    }

    public String getId() {
        return id;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Resource getResource() {
        return resource;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
