package money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private static final NumberFormat PLN_FORMAT =
            NumberFormat.getCurrencyInstance(Locale.of("pl", "PL"));

    private final BigDecimal amount; //PLN

    // Konstruktory
    public Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        BigDecimal scaledAmount = amount.setScale(2, RoundingMode.HALF_UP);

        if (scaledAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = scaledAmount;
    }

    public static Money of(String amountString) {
        if (amountString == null || amountString.isBlank()) {
            throw new IllegalArgumentException("Amount cannot be null or blank");
        }
        return new Money(new BigDecimal(amountString));
    }

    // Operacje Matematyczne
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public Money multiply(BigDecimal multiplier) {
        if (multiplier == null) {
            throw new IllegalArgumentException("Multiplier cannot be null");
        }
        if (multiplier.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }
        return new Money(this.amount.multiply(multiplier));
    }

    public Money multiply(double multiplier) {
        return multiply(BigDecimal.valueOf(multiplier));
    }

    //Metody pomocnicze
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Money o) {
        return this.amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public String toString() {
        return PLN_FORMAT.format(amount);
    }
}
