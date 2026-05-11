package cli;

import billing.Invoice;
import domain.booking.Booking;
import domain.resource.Desk;
import domain.resource.Device;
import domain.resource.Resource;
import domain.resource.Room;
import domain.user.CompanyUser;
import domain.user.IndividualUser;
import domain.user.User;
import money.Money;
import payment.CardPayment;
import payment.Payment;
import pricing.HappyHoursPricing;
import pricing.StandardPricing;
import repo.ResourceRepository;
import repo.UserRepository;
import service.BillingService;
import service.BookingService;
import service.PaymentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplCli {
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final BillingService billingService;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    public ReplCli(BookingService bookingService, PaymentService paymentService, BillingService billingService,
                   UserRepository userRepository, ResourceRepository resourceRepository) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.billingService = billingService;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to ffWork CLI. Type HELP for commands");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = parseInput(input);
            String command = parts[0].toUpperCase();

            if (command.equals("QUIT")) {
                System.out.println("OK: Goodbye!");
                break;
            }

            try {
                handleCommand(command, parts);
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void handleCommand(String command, String[] parts) {
        switch (command) {
            case "HELP":
                printHelp();
                break;
            case "ADD_USER":
                handleAddUser(parts);
                break;
            case "LIST_USERS":
                System.out.println("OK: Listing users:");
                userRepository.findAll().forEach(System.out::println);
                break;
            case "ADD_ROOM", "ADD_DESK", "ADD_DEVICE":
                handleAddResource(parts);
                break;
            case "LIST_RESOURCES":
                System.out.println("OK: Listing resources:");
                resourceRepository.findAll().forEach(System.out::println);
                break;
            case "BOOK":
                handleBook(parts);
                break;
            case "CONFIRM":
                handleConfirm(parts);
                break;
            case "CANCEL":
                handleCancel(parts);
                break;
            case "LIST_BOOKINGS":
                System.out.println("OK: Listing bookings:");
                bookingService.list().forEach(System.out::println);
                break;
            case "SET_PRICING":
                handleSetPricing(parts);
                break;
            case "PAY":
                handlePay(parts);
                break;
            case "INVOICE":
                handleInvoice(parts);
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    private void handleAddUser(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Usage: ADD_USER INDIVIDUAL <email> <displayName> " +
                    "OR ADD_USER COMPANY <email> <displayName> <companyName> <nip>");
        }

        String type = parts[1].toUpperCase();
        String email = parts[2];
        String displayName = parts[3];

        if (type.equals("INDIVIDUAL")) {
            if (parts.length > 4) {
                String studentId = parts[4];
                userRepository.add(new IndividualUser(email, displayName, studentId));
            } else
                userRepository.add(new IndividualUser(email, displayName));
            System.out.println("OK: Added individual user " + displayName);
        } else if (type.equals("COMPANY")) {
            String companyName = parts[4];
            String taxId = parts[5];
            userRepository.add(new CompanyUser(email, displayName, companyName, taxId));
            System.out.println("OK: Added company user " + displayName);
        } else {
            throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }

    private void handleAddResource(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Usage: ADD_<RESOURCE> <name> <param1> <param2>");
        }
        String command = parts[0].toUpperCase();
        String name = parts[1];

        switch (command) {
            case "ADD_ROOM" -> {
                int seats = Integer.parseInt(parts[2]);
                Money hourlyRate = Money.of(parts[3]);
                Set<String> equipment = Set.of();
                resourceRepository.add(new Room(name, hourlyRate, seats, equipment));
                System.out.println("OK: Added room " + name);
            }
            case "ADD_DESK" -> {
                Desk.DeskType type = parts[2].equalsIgnoreCase("HOT") ? Desk.DeskType.HOT : Desk.DeskType.FIXED;
                Money hourlyRate = Money.of(parts[3]);
                resourceRepository.add(new Desk(name, hourlyRate, type));
                System.out.println("OK: Added desk " + name);
            }
            case "ADD_DEVICE" -> {
                int quantity = Integer.parseInt(parts[2]);
                Money hourlyRate = Money.of(parts[3]);
                resourceRepository.add(new Device(name, hourlyRate, quantity));
                System.out.println("OK: Added device " + name);
            }
            default -> throw new IllegalArgumentException("Unknown add resource command: " + command);
        }
    }

    private void handleBook(String[] parts) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Usage: BOOK <userEmail> <resourceName> <startIso> <endIso|durationMinutes>");
        }

        String email = parts[1];
        String resourceName = parts[2];

        LocalDateTime start;
        try {
            start = LocalDateTime.parse(parts[3]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid start date format. Expected YYYY-MM-DDTHH:MM");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        Resource resource = resourceRepository.findByName(resourceName)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + resourceName));

        Booking booking;

        try {
            int durationMinutes = Integer.parseInt(parts[4]);
            booking = bookingService.book(user, resource, start, durationMinutes);
        } catch (NumberFormatException e) {
            try {
                LocalDateTime end = LocalDateTime.parse(parts[4]);
                booking = bookingService.book(user, resource, start, end);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid end date format or duration.");
            }
        }

        System.out.println("OK: Created booking " + booking.getId() + " | Price: " + booking.getCalculatedPrice());
    }

    private void handleConfirm(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: CONFIRM <bookingId>");
        }
        String bookingId = parts[1];
        bookingService.confirm(bookingId);
        System.out.println("OK: Booking " + bookingId + " confirmed.");
    }

    private void handleCancel(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: CANCEL <bookingId>");
        }
        String bookingId = parts[1];
        bookingService.cancel(bookingId);
        System.out.println("OK: Booking " + bookingId + " cancelled.");
    }

    private void handleSetPricing(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: SET_PRICING <STANDARD|HAPPY_HOURS>");
        }

        String policyName = parts[1].toUpperCase();

        if (policyName.equals("STANDARD")) {
            bookingService.setCurrentPricingPolicy(new StandardPricing());
            System.out.println("OK: Pricing policy set to STANDARD");

        } else if (policyName.equals("HAPPY_HOURS")) {
            bookingService.setCurrentPricingPolicy(new HappyHoursPricing());
            System.out.println("OK: Pricing policy set to HAPPY_HOURS");

        } else {
            throw new IllegalArgumentException("Unknown pricing policy: " + policyName);
        }
    }

    private void handlePay(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Usage: PAY <bookingId> <paymentMethod> <paymentDetails>");
        }
        String bookingId = parts[1];
        String paymentMethod = parts[2].toUpperCase();
        String paymentDetails = parts[3];

        if (paymentMethod.equals("CARD")) {
            Payment paymentResult = paymentService.pay(bookingId, paymentDetails);
            if (paymentResult instanceof CardPayment cardPayment)
                System.out.println("OK: Payment captured method=" + paymentMethod + " last4=" + cardPayment.getLast4());
        } else if (paymentMethod.equals("WALLET")) {
            // Miejsce na opcjonalne zadanie z Portfelem
            System.out.println("OK: Payment captured method=" + paymentMethod);
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }

    private void handleInvoice(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: INVOICE <bookingId>");
        }

        String bookingId = parts[1];
        Booking targetBooking = bookingService.list()
                .stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Invoice invoice = billingService.toInvoice(targetBooking);
        System.out.println(invoice);
    }

    private String[] parseInput(String input) {
        List<String> parts = new ArrayList<>();

        Matcher matcher = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                parts.add(matcher.group(1));
            } else {
                parts.add(matcher.group(2));
            }
        }
        return parts.toArray(new String[0]);
    }

    private void printHelp() {
        System.out.println("OK: Available commands:");
        System.out.println("- ADD_USER INDIVIDUAL <email> <displayName> optional: <studentId>");
        System.out.println("- ADD_USER COMPANY <email> <companyName> <nip>");
        System.out.println("- LIST_USERS");
        System.out.println("------------");
        System.out.println("- ADD_ROOM <name> <seats> <hourlyRate>");
        System.out.println("- ADD_DESK <name> <hot|fixed> <hourlyRate>");
        System.out.println("- ADD_DEVICE <name> <quantity> <hourlyRate>");
        System.out.println("- LIST_RESOURCES");
        System.out.println("------------");
        System.out.println("- BOOK <userEmail> <resourceName> <startIso> <endIso>");
        System.out.println("- BOOK <userEmail> <resourceName> <startIso> <durationMinutes>");
        System.out.println("- CONFIRM <bookingId");
        System.out.println("- CANCEL <bookingId>");
        System.out.println("- LIST_BOOKINGS");
        System.out.println("------------");
        System.out.println("- SET_PRICING STANDARD|HAPPY_HOURS");
        System.out.println("------------");
        System.out.println("- PAY <bookingId> CARD <last4>");
        System.out.println("- INVOICE <bookingId>");
        System.out.println("------------");
        System.out.println("- QUIT");
    }
}
