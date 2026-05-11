import cli.ReplCli;
import pricing.PricingPolicy;
import pricing.StandardPricing;
import repo.*;
import service.BillingService;
import service.BookingService;
import service.PaymentService;

public class CliTest {
    public static void main(String[] args) {
        // 1. Inicjalizacja repozytoriów
        UserRepository userRepository = new InMemoryUserRepository();
        ResourceRepository resourceRepository = new InMemoryResourceRepository();
        BookingRepository bookingRepository = new InMemoryBookingRepository();

        // 2. Inicjalizacja serwisów
        PricingPolicy pricingPolicy = new StandardPricing();

        BookingService bookingService = new BookingService(userRepository, resourceRepository, bookingRepository, pricingPolicy);
        PaymentService paymentService = new PaymentService(bookingRepository);
        BillingService billingService = new BillingService();

        ReplCli commandLineInterface = new ReplCli(bookingService, paymentService, billingService, userRepository, resourceRepository);
        commandLineInterface.start();
    }
}
