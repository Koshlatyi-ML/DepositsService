package bank.service.description;

import bank.service.PlainSavingService;
import bank.service.description.ReplenishServiceDescription;
import bank.service.description.WithdrawServiceDescription;

import java.math.BigDecimal;
import java.util.Objects;

public class ServiceDescriptionFactory {
    private ServiceDescriptionFactory() {
        throw new IllegalAccessError();
    }

    public static PlainSavingService createSavingService(BigDecimal minBalance,
                                                         BigDecimal maxBalance,
                                                         int minMonthTerm) {
        if (Objects.isNull(minBalance) || Objects.isNull(maxBalance)) {
            throw new NullPointerException();
        }

        if (minBalance.signum() == -1 || maxBalance.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (minBalance.compareTo(maxBalance) > 0) {
            throw new IllegalArgumentException();
        }

        if (minMonthTerm < 1) {
            throw new IllegalArgumentException();
        }

        return new PlainSavingService(minBalance, maxBalance, minMonthTerm);
    }

    public static ReplenishServiceDescription createReplenishService(BigDecimal minReplenishment,
                                                                     BigDecimal maxReplenishment,
                                                                     int replenishableMonths) {
        if (Objects.isNull(minReplenishment) || Objects.isNull(maxReplenishment)) {
            throw new NullPointerException();
        }

        if (minReplenishment.signum() == -1 || maxReplenishment.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (minReplenishment.compareTo(maxReplenishment) > 0) {
            throw new IllegalArgumentException();
        }

        if (replenishableMonths < 1) {
            throw new IllegalArgumentException();
        }

        return new ReplenishServiceDescription(minReplenishment, maxReplenishment, replenishableMonths);
    }

    public static WithdrawServiceDescription createImmediateWithdrawService(int unwithdrawableDays) {
        if (unwithdrawableDays < 1) {
            throw new IllegalArgumentException();
        }

        return new WithdrawServiceDescription(unwithdrawableDays);
    }
}
