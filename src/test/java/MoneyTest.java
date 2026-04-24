import money.Money;

public class MoneyTest {
    public static void main(String[] args) {
        System.out.println("--- Rozpoczynamy testy klasy Money ---\n");

        // 1. Testowanie tworzenia i zaokrąglania
        Money m1 = Money.of("123.456");
        System.out.println("Test 1 - Zaokrąglanie:");
        System.out.println("Oczekiwano: 123.46 PLN");
        System.out.println("Otrzymano:  " + m1);
        System.out.println();

        // 2. Testowanie dodawania
        Money m2 = Money.of("10.50");
        Money m3 = Money.of("20.75");
        Money sum = m2.add(m3);
        System.out.println("Test 2 - Dodawanie:");
        System.out.println("Oczekiwano: 31.25 PLN");
        System.out.println("Otrzymano:  " + sum);
        System.out.println();

        // 3. Testowanie odejmowania
        Money diff = sum.subtract(m2);
        System.out.println("Test 3 - Odejmowanie (31.25 - 10.50):");
        System.out.println("Oczekiwano: 20.75 PLN");
        System.out.println("Otrzymano:  " + diff);
        System.out.println();

        // 4. Test inwariantu: tworzenie kwoty ujemnej
        System.out.print("Test 4 - Próba utworzenia ujemnej kwoty (-5.00): ");
        try {
            Money.of("-5.00");
            System.out.println("[BŁĄD] Program nie zgłosił błędu!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Złapano wyjątek -> " + e.getMessage());
        }

        // 5. Test inwariantu: zejście poniżej zera przy odejmowaniu
        System.out.print("Test 5 - Próba odjęcia 50 PLN od 10.50 PLN: ");
        try {
            m2.subtract(Money.of("50.00"));
            System.out.println("[BŁĄD] Program pozwolił na ujemne saldo!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Złapano wyjątek -> " + e.getMessage());
        }

        // 6. Test równości (metoda equals)
        Money eq1 = Money.of("100.00");
        Money eq2 = Money.of("100.0"); // Zapisane jako double
        System.out.println("\nTest 6 - Czy 100.00 to to samo co 100.0?");
        System.out.println("Oczekiwano: true");
        System.out.println("Otrzymano:  " + eq1.equals(eq2));

        System.out.println("\n--- Koniec testów ---");
    }
}
