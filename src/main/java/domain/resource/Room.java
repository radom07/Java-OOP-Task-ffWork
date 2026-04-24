package domain.resource;

import money.Money;

import java.util.Set;

public class Room extends Resource {
    private static final String BASE_RATE_PER_HOUR = "50.00";

    private final int seats;
    private final Set<String> equipment;

    public Room(String name, Money customHourlyRate, int seats, Set<String> equipment) {
        if (seats < 1) {
            throw new IllegalArgumentException("Room must have at least one seat");
        }
        super(name, customHourlyRate);
        this.seats = seats;
        this.equipment = equipment;
    }

    public Room(String name, int seats, Set<String> equipment) {
        this(name, null, seats, equipment);
    }

    public int getSeats() {
        return seats;
    }

    public Set<String> getEquipment() {
        return equipment;
    }

    @Override
    protected Money baseRatePerHour() {
        return Money.of(BASE_RATE_PER_HOUR);
    }

    @Override
    public String describe() {
        String eqString = equipment.isEmpty() ? "lack of equipment" : String.join(", ", equipment);
        return String.format("Room '%s' [Seats: %d, Equipment: %s]", getName(), seats, eqString);
    }
}
