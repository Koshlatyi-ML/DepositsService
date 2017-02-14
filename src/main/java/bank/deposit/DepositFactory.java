package bank.deposit;

import bank.service.ReplenishService;
import bank.service.SavingService;
import bank.service.WithdrawService;
import bank.service.description.WithdrawServiceDescription;
import bank.service.description.ReplenishServiceDescription;
import bank.debt.Debt;

import java.util.Objects;

public class DepositFactory {   
    private DepositFactory(){}

    public static PlainDeposit createPlainDeposit(Debt debt, SavingService savingService, int monthTerm) {
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

    public static CallDeposit createCallDeposit(Debt debt, SavingService savingService, int monthTerm,
                                                WithdrawServiceDescription withdrawServiceDescription) {
        if (Objects.isNull(debt) || Objects.isNull(savingService)
                || Objects.isNull(withdrawServiceDescription)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        int unwithdrawableDays = withdrawServiceDescription.getUnwithdrawableDays();
        if (monthTerm <= unwithdrawableDays % 31) {
            throw new IllegalArgumentException();
        }

        WithdrawService withdrawService = new WithdrawService(withdrawServiceDescription);
        CallDeposit deposit = new CallDeposit(debt, savingService, monthTerm, withdrawService);
        withdrawService.setDeposit(deposit);

        return deposit;
    }

    public static ReplenishableDeposit createReplenishableDeposit(Debt debt, SavingService savingService,
            int monthTerm, ReplenishServiceDescription replenishServiceDescription) {
        if (Objects.isNull(debt) || Objects.isNull(savingService)
                || Objects.isNull(replenishServiceDescription)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        if (replenishServiceDescription.getReplenishableMonths() > monthTerm) {
            replenishServiceDescription.setReplenishableMonths(monthTerm);
        }

        ReplenishService replenishService = new ReplenishService(replenishServiceDescription);
        ReplenishableDeposit deposit
                = new ReplenishableDeposit(debt, savingService, monthTerm, replenishService);
        replenishService.setDeposit(deposit);

        return deposit;
    }

    public static AllInclusiveDeposit createAllInclusiveDeposit(Debt debt, SavingService savingService,
            int monthTerm, ReplenishServiceDescription replenishServiceDescription,
            WithdrawServiceDescription withdrawServiceDescription) {
        if (Objects.isNull(debt) || Objects.isNull(savingService)
                || Objects.isNull(replenishServiceDescription)
                || Objects.isNull(withdrawServiceDescription)) {
            throw new NullPointerException();
        }

        if (monthTerm < 1) {
            throw new IllegalArgumentException();
        }

        if (monthTerm < savingService.getMinMonthsTerm()) {
            throw new IllegalArgumentException();
        }

        int unwithdrawableDays = withdrawServiceDescription.getUnwithdrawableDays();
        if (monthTerm <= unwithdrawableDays % 31) {
            throw new IllegalArgumentException();
        }

        if (replenishServiceDescription.getReplenishableMonths() > monthTerm) {
            replenishServiceDescription.setReplenishableMonths(monthTerm);
        }

        WithdrawService withdrawService = new WithdrawService(withdrawServiceDescription);
        ReplenishService replenishService = new ReplenishService(replenishServiceDescription);

        AllInclusiveDeposit deposit = new AllInclusiveDeposit(debt, savingService,
                monthTerm, replenishService, withdrawService);

        withdrawService.setDeposit(deposit);
        replenishService.setDeposit(deposit);

        return deposit;
    }
}
