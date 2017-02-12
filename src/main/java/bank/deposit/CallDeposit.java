package bank.deposit;

import bank.service.ImmediateWithdrawService;
import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CallDeposit extends AbstractDeposit implements Withdrawable {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    ImmediateWithdrawService withdrawService;
    private BigDecimal income = BigDecimal.ZERO;

    CallDeposit(Debt debt,
                SavingService savingService,
                ImmediateWithdrawService withdrawService,
                int monthTerm) {
        super(debt, savingService, monthTerm);
        this.withdrawService = withdrawService;
    }

    @Override
    public void open(BigDecimal principalSum) {
        int unwithdrawableDays = withdrawService.getUnwithdrawableDays();
        LocalDate lastUnwithdrawableDate = LocalDate.now().plusDays(unwithdrawableDays);

        if ((lastUnwithdrawableDate).compareTo(openingDate.plusMonths(getMonthTerm())) >= 0) {
            long boundaryWithdrawPeriod = ChronoUnit.DAYS.between(openingDate,closingDate.minusDays(1));
            this.withdrawService.setUnwithdrawableDays((int) boundaryWithdrawPeriod);
        }
        super.open(principalSum);
    }

    @Override
    public BigDecimal withdraw() {
        int unwithdrawableDays = withdrawService.getUnwithdrawableDays();
        LocalDate withdrawStartDate = openingDate.plusDays(unwithdrawableDays);

        if (!Deposits.isBetween(LocalDate.now(), withdrawStartDate, closingDate)) {
            throw new IllegalStateException();
        }

        if (!Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        return this.close();
    }

    @Override
    void processMonthlyTransaction() {
        if (!Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        if (getBalance().equals(BigDecimal.ZERO)) {
            throw new IllegalStateException();
        }

        this.income = Deposits.getAccruedInterest(getBalance(), getDebt().getInterest());
    }
}
