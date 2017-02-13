package bank.service;

import bank.deposit.AbstractDeposit;
import bank.deposit.Deposit;
import bank.deposit.Deposits;
import bank.deposit.Withdrawable;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ImmediateWithdrawService implements Withdrawable {
    private Deposit deposit;

    private int unwithdrawableDays;

    public ImmediateWithdrawService(int unwithdrawableDays) {
        this.unwithdrawableDays = unwithdrawableDays;
    }

    public ImmediateWithdrawService(Deposit deposit, int unwithdrawableDays) {
        this.deposit = deposit;
        this.unwithdrawableDays = unwithdrawableDays;
    }

    public int getUnwithdrawableDays() {
        return unwithdrawableDays;
    }

    public void setUnwithdrawableDays(int unwithdrawableDays) {
        this.unwithdrawableDays = unwithdrawableDays;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public BigDecimal withdraw() {
        LocalDate openingDate = deposit.getOpeningDate();
        LocalDate closingDate = openingDate
                .plusMonths(deposit.getMonthTerm())
                .plusDays(1);
        LocalDate withdrawStartDate = openingDate.plusDays(unwithdrawableDays);

        if (!Deposits.isBetween(LocalDate.now(), withdrawStartDate, closingDate)) {
            throw new IllegalStateException();
        }

        if (!Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        return deposit.close();
    }
}
