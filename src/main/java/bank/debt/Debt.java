package bank.debt;

import java.math.BigDecimal;

public class Debt {
    Currency currency;
    BigDecimal interest;

    public Debt(Currency currency, BigDecimal interest) {
        this.currency = currency;
        this.interest = interest;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getInterest() {
        return new BigDecimal(interest.toBigInteger());
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
