package domain.user;

import java.util.Objects;

public class User {
    private String email;
    private String displayName;

    public User(String email, String displayName) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        this.email = email;
        this.displayName = displayName;
    }

    // Akcesory uznałem tylko jako Gettery
    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "User: " + displayName + " " + email;
    }

    // Do sprawdzenia unikalności emaila
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
