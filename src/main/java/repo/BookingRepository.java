package repo;

import domain.booking.Booking;
import domain.booking.BookingStatus;
import domain.resource.Resource;
import domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    void add(Booking b);

    Optional<Booking> findById(String id);

    List<Booking> findAll();

    // Nowa metoda
    List<Booking> findOverlappingBookings(Resource resource, LocalDateTime start, LocalDateTime end);

    // Nowa metoda
    List<Booking> findByParams(User user, Resource resource, BookingStatus status);
}
