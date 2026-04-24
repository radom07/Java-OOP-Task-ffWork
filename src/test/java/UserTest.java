import domain.user.CompanyUser;
import domain.user.IndividualUser;
import domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserTest {
    public static void main(String[] args) {
        System.out.println("--- Rozpoczynamy testy hierarchii Użytkowników ---\n");

        // 1. Tworzenie użytkownika indywidualnego (ze studentId)
        IndividualUser student = new IndividualUser(
                "jan.kowalski@uczelnia.pl",
                "Janek Kowalski",
                "123456"
        );

        // 2. Tworzenie użytkownika indywidualnego (bez studentId)
        IndividualUser normalUser = new IndividualUser(
                "anna.nowak@gmail.com",
                "Anna Nowak"
        );

        // 3. Tworzenie użytkownika firmowego
        CompanyUser company = new CompanyUser(
                "kontakt@mojafirma.pl",
                "Obsługa Klienta",
                "Tech-Bud Sp. z o.o.",
                "1234567890"
        );

        // --- TEST POLIMORFIZMU ---
        // Ponieważ obie klasy dziedziczą po "User", możemy wrzucić je do jednej listy!
        List<User> allUsers = new ArrayList<>();
        allUsers.add(student);
        allUsers.add(normalUser);
        allUsers.add(company);

        System.out.println("Lista wszystkich użytkowników w systemie:");
        for (User user : allUsers) {
            // Program sam rozpozna, z jakiego typu obiektem ma do czynienia
            // i wywoła odpowiednią metodę toString() dla konkretnej klasy.
            System.out.println("- " + user.toString());
        }

        System.out.println("\n--- Testy walidacji (Oczekiwane błędy) ---");

        System.out.print("Próba utworzenia firmy bez NIPu: ");
        try {
            new CompanyUser("zly@mail.pl", "Brak Nipu", "Firma Krzak", "");
            System.out.println("[BŁĄD] Program na to pozwolił!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Złapano wyjątek -> " + e.getMessage());
        }
    }
}
