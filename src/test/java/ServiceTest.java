import domain.booking.Booking;
import domain.resource.Resource;
import domain.resource.Room;
import domain.user.IndividualUser;
import domain.user.User;
import pricing.PricingPolicy;
import pricing.StandardPricing;
import repo.*;
import service.BillingService;
import service.BookingService;
import service.PaymentService;
import payment.Payment;
import billing.Invoice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class ServiceTest {

    public static void main(String[] args) {
        System.out.println("Service Test\n");

        // 1. Inicjalizacja repozytoriów
        UserRepository userRepository = new InMemoryUserRepository();
        ResourceRepository resourceRepository = new InMemoryResourceRepository();
        BookingRepository bookingRepository = new InMemoryBookingRepository();

        // 2. Inicjalizacja serwisów
        PricingPolicy pricingPolicy = new StandardPricing();

        BookingService bookingService = new BookingService(userRepository, resourceRepository, bookingRepository, pricingPolicy);
        PaymentService paymentService = new PaymentService(bookingRepository);
        BillingService billingService = new BillingService();

        try {
            // 3. Przygotowanie danych testowych
            User user = new IndividualUser("jan@example.com", "Jan Kowalski");
            userRepository.add(user);

            Set<String> equipment = Set.of("Screen", "Micro", "Speakers");
            Resource room = new Room("Room Alpha", 10, equipment);
            resourceRepository.add(room);

            // 4. Test: BookingService
            System.out.println("--- TEST: BOOKING SERVICE ---");
            LocalDateTime start = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);

            // Rezerwujemy na 120 minut
            Booking booking = bookingService.book(user, room, start, 120);
            System.out.println("Created booking: " + booking.getId());
            System.out.println("Initial status: " + booking.getStatus());
            System.out.println("Calculated price: " + booking.getCalculatedPrice());

            // Potwierdzenie rezerwacji
            bookingService.confirm(booking.getId());
            System.out.println("Status after confirmation: " + booking.getStatus());

            // 5. Test: PaymentService
            System.out.println("\n--- TEST: PAYMENT SERVICE ---");
            Payment payment = paymentService.pay(booking.getId(), "4242");
            System.out.println("Payment made with card ending in: 4242");
            System.out.println("Amount charged: " + payment.getAmount());

            // 6. Test: BillingService
            System.out.println("\n--- TEST: BILLING SERVICE ---");
            Invoice invoice = billingService.toInvoice(booking);
            System.out.println("Invoice issued: ");
            System.out.println(invoice.toString());

            System.out.println("\nAll tests completed successfully!");

        } catch (Exception e) {
            System.err.println("\n An error occurred during the test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}