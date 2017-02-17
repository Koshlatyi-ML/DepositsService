package bank.debt;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Currency {
    private static final Map<CurrencyCode, BigDecimal> exchangeRates = setExchangeRates();

    private CurrencyCode code;
    private BigDecimal value;

    private Currency(){}

    public static Currency getCurrency(CurrencyCode code) {
        Currency currency = new Currency();
        currency.code = code;
        currency.value = exchangeRates.get(code);
        return currency;
    }

    private static Map<CurrencyCode, BigDecimal> setExchangeRates() {
        return new HashMap<CurrencyCode, BigDecimal>(){{
                put(CurrencyCode.UAH, BigDecimal .ONE);
                put(CurrencyCode.USD, new BigDecimal("27.061"));
                put(CurrencyCode.EUR, new BigDecimal("28.681"));
                put(CurrencyCode.RUB, new BigDecimal("0.432"));
            }};
    }

    public CurrencyCode getCode() {
        return code;
    }

    public BigDecimal getValue() {
        return value;
    }
}
