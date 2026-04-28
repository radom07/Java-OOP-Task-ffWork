package repo;

import domain.booking.Booking;

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
}
