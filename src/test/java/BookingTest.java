import domain.booking.Booking;
import domain.resource.Desk;
import domain.resource.Resource;
import domain.user.IndividualUser;
import domain.user.User;
import money.Money;

import java.time.LocalDateTime;

public class BookingTest {

    public static void main(String[] args) {
        // Testowe dane
        User dummyUser = new IndividualUser("john.doe@example.com", "John Doe");
        Resource dummyResource = new Desk("Hot Desk 01", Desk.DeskType.HOT);
        Money dummyPrice = Money.of("50.00");

        // 1. Czas końcowy wcześniej niż czas startowy
        System.out.print("Test 1 - Attempt to book with reversed dates (End before Start): ");
        try {
            new Booking(
                    "BK-20231025-01", dummyUser, dummyResource,
                    LocalDateTime.of(2023, 10, 25, 14, 0),
                    LocalDateTime.of(2023, 10, 25, 12, 0),
                    dummyPrice
            );
            System.out.println("[ERROR] Program allowed invalid date range!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Caught exception -> " + e.getMessage());
        }

        // 2. Czas trwania 0 minut
        System.out.print("Test 2 - Attempt to book with 0 minutes duration (End = Start): ");
        try {
            new Booking(
                    "BK-20231025-02", dummyUser, dummyResource,
                    LocalDateTime.of(2023, 10, 25, 12, 0),
                    LocalDateTime.of(2023, 10, 25, 12, 0),
                    dummyPrice
            );
            System.out.println("[ERROR] Program allowed a 0-minute booking!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Caught exception -> " + e.getMessage());
        }

        // Testowy booking
        LocalDateTime start = LocalDateTime.of(2023, 10, 25, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 25, 12, 30);
        Booking validBooking = new Booking("BK-20231025-03", dummyUser, dummyResource, start, end, dummyPrice);

        // 3. Test durationMinutes
        System.out.println("\nTest 3 - Duration calculation (10:00 to 12:30):");
        System.out.println("Expected: 150 minutes");
        System.out.println("Received: " + validBooking.durationMinutes() + " minutes");

        // 4. Zmiana statusów
        System.out.println("\nTest 4 - Allowed status transitions:");
        System.out.println("- Initial status: " + validBooking.getStatus());

        validBooking.confirm();
        System.out.println("- After confirm(): " + validBooking.getStatus() + " (Expected: CONFIRMED)");

        validBooking.complete();
        System.out.println("- After complete(): " + validBooking.getStatus() + " (Expected: COMPLETED)");

        System.out.print("\nTest 5 - Forbidden transition (COMPLETED -> CANCELLED): ");
        try {
            validBooking.cancel();
            System.out.println("[ERROR] Program allowed cancellation of a completed booking!");
        } catch (IllegalStateException e) {
            System.out.println("[OK] Caught exception -> " + e.getMessage());
        }

        System.out.print("Test 6 - Forbidden transition (PENDING -> COMPLETED): ");
        try {
            Booking newBooking = new Booking("BK-20231025-04", dummyUser, dummyResource, start, end, dummyPrice);
            newBooking.complete();
            System.out.println("[ERROR] Program allowed completion of an unconfirmed booking!");
        } catch (IllegalStateException e) {
            System.out.println("[OK] Caught exception -> " + e.getMessage());
        }
    }
}