package domain.resource;

import money.Money;

public abstract class Resource {
    private String name;
    private Money customHourlyRate;

    public Resource(String name, Money customHourlyRate) {
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

    public void setName(String name) {
        this.name = name;
    }

    public Money getCustomHourlyRate() {
        return customHourlyRate;
    }

    public void setCustomHourlyRate(Money customHourlyRate) {
        this.customHourlyRate = customHourlyRate;
    }
}
