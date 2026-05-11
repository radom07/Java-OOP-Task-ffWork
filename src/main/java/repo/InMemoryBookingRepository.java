package repo;

import domain.booking.Booking;
import domain.booking.BookingStatus;
import domain.resource.Resource;
import domain.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryBookingRepository implements BookingRepository {
    // Kluczem jest unikalny String ID
    private final Map<String, Booking> bookings = new HashMap<>();

    @Override
    public void add(Booking b) {
        bookings.put(b.getId(), b);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.ofNullable(bookings.get(id));
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    // Nowa metoda
    @Override
    public List<Booking> findOverlappingBookings(Resource resource, LocalDateTime start, LocalDateTime end) {
        return bookings.values().stream()
                .filter(b -> b.getResource().equals(resource))
                .filter(b -> b.getStatus() == BookingStatus.PENDING || b.getStatus() == BookingStatus.CONFIRMED)
                .filter(b -> start.isBefore(b.getEnd()) && b.getStart().isBefore(end))
                .toList();
    }

    // Nowa metoda
    @Override
    public List<Booking> findByParams(User user, Resource resource, BookingStatus status) {
        return bookings.values().stream()
                .filter(b -> user == null || b.getUser().equals(user))
                .filter(b -> resource == null || b.getResource().equals(resource))
                .filter(b -> status == null || b.getStatus() == status)
                .toList();
    }
}
