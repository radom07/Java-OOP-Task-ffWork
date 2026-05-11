package pricing;

import domain.booking.Booking;
import money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StandardPricing implements PricingPolicy {

    private static final BigDecimal MINUTES_IN_HOUR = BigDecimal.valueOf(60);

    @Override
    public Money price(Booking booking) {
        Money resourceHourlyRate = booking.getResource().hourlyRate();
        BigDecimal bookingDuration = BigDecimal.valueOf(booking.durationMinutes());

        return resourceHourlyRate.multiply(bookingDuration).divide(MINUTES_IN_HOUR);
    }
}
