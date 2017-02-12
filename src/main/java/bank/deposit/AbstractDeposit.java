package bank.deposit;

import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

abstract public class AbstractDeposit implements Deposit{
    private Debt debt;
    private SavingService savingService;

    private BigDecimal balance;
    private int monthTerm;
    private LocalDate openingDate;

    public AbstractDeposit(Debt debt, SavingService savingService, int monthTerm) {
        this.debt = debt;
        this.savingService = savingService;
        this.monthTerm = monthTerm;

    }

    @Override
    public void open(BigDecimal principalSum) {
        BigDecimal minPrincipalSum = savingService.getMinBalance();
        BigDecimal maxPrincipalSum = savingService.getMaxBalance();

        if (!Deposits.isBetween(principalSum, minPrincipalSum, maxPrincipalSum)) {
            throw new IllegalArgumentException();
        }

        this.openingDate = LocalDate.now();
        this.balance = principalSum;
    }

    @Override
    public BigDecimal close() {
        if (Objects.isNull(openingDate)) {
            throw new IllegalStateException();
        }

        BigDecimal balance = new BigDecimal(this.balance.toBigInteger());
        resetBalance();

        return balance;
    }

    public Debt getDebt() {
        return debt;
    }

    public SavingService getSavingService() {
        return savingService;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        BigDecimal minBalance = savingService.getMinBalance();
        BigDecimal maxBalance = savingService.getMaxBalance();

        if (!Deposits.isBetween(balance, minBalance, maxBalance)) {
            throw new IllegalArgumentException();
        }

        this.balance = balance;
    }

    private void resetBalance() {
        this.balance = BigDecimal.ZERO;
    }

    public LocalDate getOpeningDate() {
        return LocalDate.from(openingDate);
    }

    public int getMonthTerm() {
        return monthTerm;
    }

    abstract void processMonthlyTransaction();
}
