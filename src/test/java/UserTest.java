import domain.user.CompanyUser;
import domain.user.IndividualUser;
import domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserTest {
    public static void main(String[] args) {
        // 1. Tworzenie użytkownika indywidualnego (ze studentId)
        IndividualUser student = new IndividualUser(
                "jan.kowalski@university.edu",
                "Jan Kowalski",
                "123456"
        );

        // 2. Tworzenie użytkownika indywidualnego (bez studentId)
        IndividualUser normalUser = new IndividualUser(
                "anna.nowak@gmail.com",
                "Anna Nowak"
        );

        // 3. Tworzenie użytkownika firmowego
        CompanyUser company = new CompanyUser(
                "contact@mycompany.com",
                "Customer Support",
                "Tech-Build LLC",
                "1234567890"
        );

        // --- TEST POLIMORFIZMU ---
        // Ponieważ obie klasy dziedziczą po "User", możemy wrzucić je do jednej listy
        List<User> allUsers = new ArrayList<>();
        allUsers.add(student);
        allUsers.add(normalUser);
        allUsers.add(company);

        System.out.println("List of all users in the system:");
        for (User user : allUsers) {
            System.out.println("- " + user.toString());
        }

        System.out.println("\n--- Validation tests (Expected errors) ---");

        System.out.print("Attempting to create a company without a Tax ID: ");
        try {
            new CompanyUser("bad@email.com", "No Tax ID", "Fake Company", "");
            System.out.println("[ERROR] The program allowed it!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Exception caught -> " + e.getMessage());
        }
    }
}
