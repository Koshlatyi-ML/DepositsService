package bank.deposit;

import bank.service.SavingService;
import bank.debt.Debt;

import java.math.BigDecimal;

public interface Deposit extends Account, SavingService{
    void open(BigDecimal principalSum);
    BigDecimal close();

    boolean isOpened();
    boolean isClosed();

    Debt getDebt();
    void setDebt(Debt debt);

    int getMonthTerm();
    void setMonthTerm(int monthTerm);

}
