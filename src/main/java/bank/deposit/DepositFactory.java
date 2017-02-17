package bank.deposit;

import bank.service.PlainReplenishService;
import bank.service.SavingService;
import bank.service.PlainWithdrawService;
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

        PlainWithdrawService withdrawService = new PlainWithdrawService(withdrawServiceDescription);
        CallDeposit deposit = new CallDeposit(debt, savingService, monthTerm, withdrawService);
        withdrawService.setDeposit(deposit);

        return deposit;
    }

    public static ReplenishServiceDeposit createReplenishableDeposit(Debt debt, SavingService savingService,
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

        PlainReplenishService replenishService = new PlainReplenishService(replenishServiceDescription);
        ReplenishServiceDeposit deposit
                = new ReplenishServiceDeposit(debt, savingService, monthTerm, replenishService);
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

        PlainWithdrawService withdrawService = new PlainWithdrawService(withdrawServiceDescription);
        PlainReplenishService replenishService = new PlainReplenishService(replenishServiceDescription);

        AllInclusiveDeposit deposit = new AllInclusiveDeposit(debt, savingService,
                monthTerm, replenishService, withdrawService);

        withdrawService.setDeposit(deposit);
        replenishService.setDeposit(deposit);

        return deposit;
    }
}
