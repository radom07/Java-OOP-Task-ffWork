package pricing;

import domain.booking.Booking;
import money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StandardPricing implements PricingPolicy {
    @Override
    public Money price(Booking booking) {
        BigDecimal pricePerMinute = booking.getResource().hourlyRate().getAmount().divide(new BigDecimal(60),2, RoundingMode.HALF_UP);
        BigDecimal minutes = BigDecimal.valueOf(booking.durationMinutes());
        BigDecimal price = pricePerMinute.multiply(minutes);

        return new Money(price);
    }
}
