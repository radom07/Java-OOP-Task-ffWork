import money.Money;

public class MoneyTest {
    public static void main(String[] args) {
        // 1. Testowanie tworzenia i zaokrąglania
        Money m1 = Money.of("123.456");
        System.out.println("Test 1 - Rounding:");
        System.out.println("Expected: 123.46 PLN");
        System.out.println("Actual:   " + m1);
        System.out.println();

        // 2. Testowanie dodawania
        Money m2 = Money.of("10.50");
        Money m3 = Money.of("20.75");
        Money sum = m2.add(m3);
        System.out.println("Test 2 - Addition:");
        System.out.println("Expected: 31.25 PLN");
        System.out.println("Actual:   " + sum);
        System.out.println();

        // 3. Testowanie odejmowania
        Money diff = sum.subtract(m2);
        System.out.println("Test 3 - Subtraction (31.25 - 10.50):");
        System.out.println("Expected: 20.75 PLN");
        System.out.println("Actual:   " + diff);
        System.out.println();

        // 4. Test inwariantu: tworzenie kwoty ujemnej
        System.out.print("Test 4 - Attempting to create a negative amount (-5.00): ");
        try {
            Money.of("-5.00");
            System.out.println("[ERROR] The program did not throw an exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Exception caught -> " + e.getMessage());
        }

        // 5. Test inwariantu: zejście poniżej zera przy odejmowaniu
        System.out.print("Test 5 - Attempting to subtract 50 PLN from 10.50 PLN: ");
        try {
            m2.subtract(Money.of("50.00"));
            System.out.println("[ERROR] The program allowed a negative balance!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Exception caught -> " + e.getMessage());
        }

        // 6. Test równości (metoda equals)
        Money eq1 = Money.of("100.00");
        Money eq2 = Money.of("100.0"); // Zapisane jako double
        System.out.println("\nTest 6 - Is 100.00 the same as 100.0?");
        System.out.println("Expected: true");
        System.out.println("Actual:   " + eq1.equals(eq2));
    }
}
