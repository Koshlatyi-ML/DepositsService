package bank.service;

import java.math.BigDecimal;
import java.util.Objects;

public class PlainSavingService implements SavingService {
    private BigDecimal minBalance;
    private BigDecimal maxBalance;

    private int minMonthsTerm;

    public PlainSavingService(BigDecimal minBalance,
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
        if (Objects.isNull(minBalance)) {
            throw new NullPointerException();
        }

        if (minBalance.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (minBalance.compareTo(maxBalance) > 0) {
            throw new IllegalArgumentException();
        }

        this.minBalance = minBalance;
    }

    public BigDecimal getMaxBalance() {
        return new BigDecimal(maxBalance.toBigInteger());
    }

    public void setMaxBalance(BigDecimal maxBalance) {
        if (Objects.isNull(maxBalance)) {
            throw new NullPointerException();
        }

        if (maxBalance.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (maxBalance.compareTo(minBalance) < 0) {
            throw new IllegalArgumentException();
        }

        this.maxBalance = maxBalance;
    }

    public int getMinMonthsTerm() {
        return minMonthsTerm;
    }

    public void setMinMonthsTerm(int minMonthsTerm) {
        if (minMonthsTerm < 0) {
            throw new IllegalArgumentException();
        }
        this.minMonthsTerm = minMonthsTerm;
    }
}
