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

    public Money(BigDecimal amount) {
        BigDecimal scaledAmount = amount.setScale(2, RoundingMode.HALF_UP);

        if (scaledAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.amount = scaledAmount;
    }

    public static Money of(String amountString) {
        return new Money(new BigDecimal(amountString));
    }

    // Operacje Matematyczne
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public Money multiply(BigDecimal m) {
        return new Money(this.amount.multiply(m));
    }

    public Money multiply(double m) {
        return multiply(BigDecimal.valueOf(m));
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
