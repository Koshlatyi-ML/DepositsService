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

    private final LocalDate closingDate = openingDate
            .plusMonths(monthTerm)
            .plusDays(1);

    private BigDecimal income = BigDecimal.ZERO;


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

    @Override
    public Debt getDebt() {
        return debt;
    }

    @Override
    public void setDebt(Debt debt) {
        if (Objects.isNull(debt)) {
            throw new NullPointerException();
        }
        this.debt = debt;
    }

    public SavingService getSavingService() {
        return savingService;
    }

    public void setSavingService(SavingService savingService) {
        if (Objects.isNull(savingService)) {
            throw new NullPointerException();
        }
        this.savingService = savingService;
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

    public void setOpeningDate(LocalDate openingDate) {
        if (Objects.isNull(openingDate)) {
            throw new NullPointerException();
        }

        this.openingDate = openingDate;
    }

    public int getMonthTerm() {
        return monthTerm;
    }

    @Override
    public void setMonthTerm(int monthTerm) {
        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        this.monthTerm = monthTerm;
    }

    public BigDecimal getIncome() {
        return income;
    }

    void setIncome(BigDecimal income) {
        if (Objects.isNull(income)) {
            throw new NullPointerException();
        }

        if (income.signum() == -1) {
            throw new IllegalArgumentException();
        }

        this.income = income;
    }

    void processMonthlyTransaction() {
        if (!Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        BigDecimal accruedInterest
                = Deposits.getAccruedInterest(getBalance(), getDebt().getInterest());
        income = income.add(accruedInterest);
    }
}
