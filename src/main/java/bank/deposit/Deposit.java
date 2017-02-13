package bank.deposit;

import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Deposit {
    void open(BigDecimal principalSum);
    BigDecimal close();

    Debt getDebt();
    void setDebt(Debt debt);

    int getMonthTerm();
    void setMonthTerm(int monthTerm);

    LocalDate getOpeningDate();

    BigDecimal getBalance();
    void setBalance(BigDecimal balance);
}
