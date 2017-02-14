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
     //todo parse file
        return new HashMap<CurrencyCode, BigDecimal>(){{
                exchangeRates.put(CurrencyCode.UAH, BigDecimal.ONE);
                exchangeRates.put(CurrencyCode.USD, new BigDecimal("27.061"));
                exchangeRates.put(CurrencyCode.EUR, new BigDecimal("28.681"));
                exchangeRates.put(CurrencyCode.RUB, new BigDecimal("0.432"));
            }};
    }

    public CurrencyCode getCode() {
        return code;
    }

    public BigDecimal getValue() {
        return value;
    }
}
