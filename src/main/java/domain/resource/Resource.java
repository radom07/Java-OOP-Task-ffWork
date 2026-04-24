package domain.resource;

import money.Money;

import java.util.Objects;

public abstract class Resource {
    private final String name;
    private final Money customHourlyRate;

    public Resource(String name, Money customHourlyRate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Resource name cannot be empty");
        }
        this.name = name;
        this.customHourlyRate = customHourlyRate;
    }

    public Resource(String name) {
        this(name, null);
    }

    protected abstract Money baseRatePerHour();
    public abstract String describe();

    public Money hourlyRate() {
        return customHourlyRate != null ? customHourlyRate : baseRatePerHour();
    }

    public String getName() {
        return name;
    }

    public Money getCustomHourlyRate() {
        return customHourlyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(name, resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
