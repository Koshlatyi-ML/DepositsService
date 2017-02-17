package bank.service;

import java.math.BigDecimal;

public interface SavingService extends Service {
    BigDecimal getMinBalance();
    void setMinBalance(BigDecimal minBalance);

    BigDecimal getMaxBalance();
    void setMaxBalance(BigDecimal maxBalance);

    int getMinMonthsTerm();
    void setMinMonthsTerm(int minMonthsTerm);
}
