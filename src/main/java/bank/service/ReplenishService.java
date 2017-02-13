package bank.service;

import bank.deposit.AbstractDeposit;
import bank.deposit.Deposit;
import bank.deposit.Deposits;
import bank.deposit.Replenishable;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReplenishService implements Replenishable {
    private Deposit deposit;

    private BigDecimal minReplenishment;
    private BigDecimal maxReplenishment;

    private int replenishableMonths;

    public ReplenishService(BigDecimal minReplenishment,
                            BigDecimal maxReplenishment,
                            int replenishableMonths) {
        this.minReplenishment = minReplenishment;
        this.maxReplenishment = maxReplenishment;
        this.replenishableMonths = replenishableMonths;
    }

    public ReplenishService(Deposit deposit,
                            BigDecimal minReplenishment,
                            BigDecimal maxReplenishment,
                            int replenishableMonths) {
        this.deposit = deposit;
        this.minReplenishment = minReplenishment;
        this.maxReplenishment = maxReplenishment;
        this.replenishableMonths = replenishableMonths;
    }

    public BigDecimal getMinReplenishment() {
        return minReplenishment;
    }

    public BigDecimal getMaxReplenishment() {
        return maxReplenishment;
    }

    public int getReplenishableMonths() {
        return replenishableMonths;
    }

    public void setReplenishableMonths(int replenishableMonths) {
        this.replenishableMonths = replenishableMonths;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public void replenish(BigDecimal replenishment) {
        LocalDate openingDate = deposit.getOpeningDate();
        LocalDate replenishExpirationTime = openingDate.plusMonths(replenishableMonths);

        if (!Deposits.isBetween(LocalDate.now(), openingDate, replenishExpirationTime)) {
            throw new IllegalStateException();
        }

        if (!Deposits.isBetween(replenishment, minReplenishment, maxReplenishment)) {
            throw new IllegalArgumentException();
        }

        BigDecimal balance = deposit.getBalance();
        deposit.setBalance(balance.add(replenishment));
    }
}
