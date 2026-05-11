package pricing;

import domain.booking.Booking;
import money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HappyHoursPricing implements PricingPolicy {
    private static final int HAPPY_HOURS_START_HOUR = 14;
    private static final int HAPPY_HOURS_END_HOUR = 16;
    private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("30");

    private final PricingPolicy standardPricing = new StandardPricing();

    @Override
    public Money price(Booking booking) {
        Money basePrice = standardPricing.price(booking);
        int startHour = booking.getStart().getHour();

        if (startHour >= HAPPY_HOURS_START_HOUR && startHour < HAPPY_HOURS_END_HOUR) {
            BigDecimal discountFraction = DISCOUNT_PERCENTAGE.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            Money discountAmount = basePrice.multiply(discountFraction);
            Money discountedPrice = basePrice.subtract(discountAmount);
            return discountedPrice;
        }
        return basePrice;
    }
}
