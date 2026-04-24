package domain.resource;

import money.Money;

public class Device extends Resource {
    private int quantity;

    public Device(String name, Money customHourlyRate, int quantity) {
        super(name, customHourlyRate);
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        this.quantity = quantity;
    }

    public Device(String name, int quantity) {
        this(name, null, quantity);
    }

    @Override
    protected Money baseRatePerHour() {
        return Money.of("10.00");
    }

    @Override
    public String describe() {
        return String.format("Device '%s' [Quantity: %d]", getName(), quantity);
    }
}
