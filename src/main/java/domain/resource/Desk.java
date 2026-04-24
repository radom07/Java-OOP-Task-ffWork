package domain.resource;

import money.Money;

public class Desk extends Resource {
   private DeskType type;

    public Desk(String name, Money customHourlyRate, DeskType type) {
        super(name, customHourlyRate);
        this.type = type;
    }

    public Desk(String name, DeskType type) {
        this(name, null, type);
    }

    @Override
    protected Money baseRatePerHour() {
        if (type == DeskType.HOT)
            return Money.of("100.00");
        if (type == DeskType.FIXED)
            return Money.of("50.00");
        throw new IllegalStateException("Invalid desk type");
    }

    @Override
    public String describe() {
        return String.format("Desk '%s' [Type: %s]", getName(), type);
    }


    public enum DeskType {
       HOT, FIXED
   }
}
