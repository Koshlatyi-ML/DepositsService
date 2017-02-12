package bank.deposit;

import bank.service.ImmediateWithdrawService;
import bank.service.ReplenishService;
import bank.service.SavingService;
import debt.Debt;

import java.util.Objects;

public class DepositFactory {
    private DepositFactory(){}

    public static PlainDeposit createPlainDeposit(Debt debt,
                                                  SavingService savingService,
                                                  int monthTerm) {
        if (Objects.isNull(debt) || Objects.isNull(savingService)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        return new PlainDeposit(debt, savingService, monthTerm);
    }

    public static CallDeposit createCallDeposit(Debt debt,
                                                SavingService savingService,
                                                ImmediateWithdrawService withdrawService,
                                                int monthTerm) {
        if (Objects.isNull(debt)
                || Objects.isNull(savingService)
                || Objects.isNull(withdrawService)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        int unwithdrawableDays = withdrawService.getUnwithdrawableDays();
        if (monthTerm <= unwithdrawableDays % 31) {
            throw new IllegalArgumentException();
        }

        return new CallDeposit(debt, savingService, withdrawService, monthTerm);
    }

    public static ReplenishableDeposit createReplenishableDeposit(Debt debt,
                                                                  SavingService savingService,
                                                                  ReplenishService replenishService,
                                                                  int monthTerm) {
        if (Objects.isNull(debt)
                || Objects.isNull(savingService)
                || Objects.isNull(replenishService)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        if (replenishService.getReplenishableMonths() > monthTerm) {
            replenishService.setReplenishableMonths(monthTerm);
        }

        return new ReplenishableDeposit(debt, savingService, replenishService, monthTerm);
    }

    public static AllInclusiveDeposit createAllInclusiveDeposit(Debt debt,
            SavingService savingService,
            ReplenishService replenishService,
            ImmediateWithdrawService withdrawService,
            int monthTerm) {
        if (Objects.isNull(debt)
                || Objects.isNull(savingService)
                || Objects.isNull(replenishService)
                || Objects.isNull(withdrawService)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        int unwithdrawableDays = withdrawService.getUnwithdrawableDays();
        if (monthTerm <= unwithdrawableDays % 31) {
            throw new IllegalArgumentException();
        }

        if (replenishService.getReplenishableMonths() > monthTerm) {
            replenishService.setReplenishableMonths(monthTerm);
        }

        return new AllInclusiveDeposit(debt,
                savingService,
                replenishService,
                withdrawService,
                monthTerm);
    }
}
