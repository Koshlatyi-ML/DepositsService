package bank.deposit;

import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

abstract public class AbstractDeposit implements Deposit{

    private String name;

    private Debt debt;
    private SavingService savingService;

    private BigDecimal balance;
    private LocalDate openingDate;

    @Override
    final public void open(BigDecimal principalSum) {
        if (principalSum.compareTo(savingService.getMinContribution()) < 0
                || principalSum.compareTo(savingService.getMaxContribution()) > 0) {
            throw new IllegalArgumentException();
        }

        this.openingDate = LocalDate.now();
        this.balance = principalSum;
    }

    @Override
    final public BigDecimal close() {
        if (savingService.getMonthsTerm() < Period.between(openingDate, LocalDate.now()).getMonths()) {
            throw new IllegalStateException();
        }

        return balance;
    }

    public String getName() {
        return name;
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

    public LocalDate getOpeningDate() {
        return openingDate;
    }

//    ???
//    BigDecimal payInterest() {
//        return balance.multiply(debt.getInterest());
//    }

    abstract void processMonthlyTransaction();
//    {
//        if (LocalDate.now().compareTo(openingDate) < 0) {
//            throw new IllegalStateException();
//        }
//
//        if (Period.between(openingDate, LocalDate.now()).getMonths() <= savingService.getMonthsTerm()) {
//            this.income = this.payInterest();
//        }
//    }
}
