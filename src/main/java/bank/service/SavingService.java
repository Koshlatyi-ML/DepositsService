package bank.service;

import debt.Debt;

import java.math.BigDecimal;

public class SavingService {
    private BigDecimal minContribution;
    private BigDecimal maxContribution;

    private int monthsTerm;
    private int minMonthsTerm;

    public BigDecimal getMinContribution() {
        return minContribution;
    }

    public void setMinContribution(BigDecimal minContribution) {
        this.minContribution = minContribution;
    }

    public BigDecimal getMaxContribution() {
        return maxContribution;
    }

    public void setMaxContribution(BigDecimal maxContribution) {
        this.maxContribution = maxContribution;
    }

    public int getMonthsTerm() {
        return monthsTerm;
    }

    public void setMonthsTerm(int monthsTerm) {
        this.monthsTerm = monthsTerm;
    }

    public int getMinMonthsTerm() {
        return minMonthsTerm;
    }

    public void setMinMonthsTerm(int minMonthsTerm) {
        this.minMonthsTerm = minMonthsTerm;
    }
}
