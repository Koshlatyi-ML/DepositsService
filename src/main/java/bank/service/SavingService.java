package bank.service;

import java.math.BigDecimal;

public class SavingService {
    private BigDecimal minBalance;
    private BigDecimal maxBalance;

    private int minMonthsTerm;

    public SavingService(BigDecimal minBalance,
                         BigDecimal maxBalance,
                         int minMonthsTerm) {
        this.minBalance = minBalance;
        this.maxBalance = maxBalance;
        this.minMonthsTerm = minMonthsTerm;
    }

    public BigDecimal getMinBalance() {
        return new BigDecimal(minBalance.toBigInteger());
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }

    public BigDecimal getMaxBalance() {
        return new BigDecimal(maxBalance.toBigInteger());
    }

    public void setMaxBalance(BigDecimal maxBalance) {
        this.maxBalance = maxBalance;
    }

    public int getMinMonthsTerm() {
        return minMonthsTerm;
    }

    public void setMinMonthsTerm(int minMonthsTerm) {
        this.minMonthsTerm = minMonthsTerm;
    }
}
