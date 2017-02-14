package bank.debt;

import java.math.BigDecimal;
import java.util.Objects;

public class DebtFactory {
    private DebtFactory() {
        throw new IllegalAccessError();
    }

    public static Debt createDebt(Currency currency, BigDecimal interest) {
        if (Objects.isNull(currency) || Objects.isNull(interest)) {
            throw new IllegalArgumentException();
        }

        if (interest.signum() == -1) {
            throw new IllegalArgumentException();
        }

        return new Debt(currency, interest);
    }

    public static Debt createDebt(CurrencyCode code, BigDecimal interest) {
        if (Objects.isNull(code) || Objects.isNull(interest)) {
            throw new IllegalArgumentException();
        }

        if (interest.signum() == -1) {
            throw new IllegalArgumentException();
        }

        return new Debt(Currency.getCurrency(code), interest);
    }
}
