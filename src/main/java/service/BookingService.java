package service;

import domain.booking.Booking;
import domain.booking.BookingStatus;
import domain.resource.Device;
import domain.resource.Resource;
import domain.user.User;
import money.Money;
import pricing.PricingPolicy;
import repo.BookingRepository;
import repo.ResourceRepository;
import repo.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingService {
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;

    private PricingPolicy currentPricingPolicy;

    private int bookingCounter;

    public BookingService(UserRepository userRepository, ResourceRepository resourceRepository, BookingRepository bookingRepository, PricingPolicy currentPricingPolicy) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.bookingRepository = bookingRepository;
        this.currentPricingPolicy = currentPricingPolicy;
    }

    public Booking book(User u, Resource r, LocalDateTime start, LocalDateTime end) {
        List<Booking> allBookings = bookingRepository.findAll();

        long overlapCount = allBookings.stream()
                .filter(b -> b.getResource().equals(r))
                .filter(b -> b.getStatus() == BookingStatus.PENDING || b.getStatus() == BookingStatus.CONFIRMED)
                .filter(b -> start.isBefore(b.getEnd()) && b.getStart().isBefore(end))
                .count();

        if (r instanceof Device device) {
            if (overlapCount >= device.getQuantity()) {
                throw new IllegalStateException("Not enough devices available on the specified date");
            }
        } else {
            if (overlapCount > 0) {
                throw new IllegalStateException("The resource is already booked for the specified date");
            }
        }

        String bookingId = generateBookingId(start);
        Booking newBooking = new Booking(bookingId, u, r, start, end);

        Money calculatedPrice = currentPricingPolicy.price(newBooking);
        newBooking.setCalculatedPrice(calculatedPrice);

        bookingRepository.add(newBooking);

        return newBooking;
    }

    public Booking book(User u, Resource r, LocalDateTime start, int durationMinutes) {
        return this.book(u, r, start, start.plusMinutes(durationMinutes));
    }

    private String generateBookingId(LocalDateTime start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateToId = start.format(formatter);

        bookingCounter++;
        String id = String.format("BK-%s-%d", dateToId, bookingCounter);

        return id;
    }

    public void confirm(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("No reservation found"));

        booking.confirm();
    }

    public void cancel(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("No reservation found"));

        booking.cancel();
    }

    public void complete(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("No reservation found"));

        booking.complete();
    }

    public List<Booking> list() {
        return bookingRepository.findAll();
    }

    public List<Booking> list(User user, Resource resource, BookingStatus status) {
        return bookingRepository.findAll().stream()
                .filter(b -> user == null || b.getUser().equals(user))
                .filter(b -> resource == null || b.getResource().equals(resource))
                .filter(b -> status == null || b.getStatus() == status)
                .toList();
    }

    public PricingPolicy getCurrentPricingPolicy() {
        return currentPricingPolicy;
    }

    public void setCurrentPricingPolicy(PricingPolicy currentPricingPolicy) {
        this.currentPricingPolicy = currentPricingPolicy;
    }
}
