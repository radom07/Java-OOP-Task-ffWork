package domain.resource;

import money.Money;

public class Desk extends Resource {
    private static final String HOT_BASE_RATE_PER_HOUR = "10.00";
    private static final String FIXED_BASE_RATE_PER_HOUR = "15.00";

   private final DeskType type;

    public Desk(String name, Money customHourlyRate, DeskType type) {
        if (type == null) {
            throw new IllegalArgumentException("Desk type cannot be null");
        }
        super(name, customHourlyRate);
        this.type = type;
    }

    public Desk(String name, DeskType type) {
        this(name, null, type);
    }

    @Override
    protected Money baseRatePerHour() {
        return switch (type) {
            case HOT -> Money.of(HOT_BASE_RATE_PER_HOUR);
            case FIXED -> Money.of(FIXED_BASE_RATE_PER_HOUR);
            default -> throw new IllegalStateException("Invalid desk type");
        };
    }

    @Override
    public String describe() {
        return String.format("Desk '%s' [Type: %s]", getName(), type);
    }

    public enum DeskType {
       HOT, FIXED
   }
}
