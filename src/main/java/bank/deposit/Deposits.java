package bank.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class Deposits {
    private Deposits() { }

    public static BigDecimal getAccruedInterest(BigDecimal principalSum, BigDecimal interest) {
        return principalSum.multiply(interest);
    }

    public static boolean isBetween(LocalDate now, LocalDate start, LocalDate end) {
        return now.isAfter(start) && now.isBefore(end);
    }

    public static boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
}
