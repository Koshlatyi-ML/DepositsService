package bank.service;

import java.math.BigDecimal;
import java.util.Objects;

public class ServiceFactory {
    private ServiceFactory() {
        throw new IllegalAccessError();
    }

    public static SavingService createSavingService(BigDecimal minBalance,
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

        return new SavingService(minBalance, maxBalance, minMonthTerm);
    }

    public static ReplenishService createReplenishService(BigDecimal minReplenishment,
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

        return new ReplenishService(minReplenishment, maxReplenishment, replenishableMonths);
    }

    public static ImmediateWithdrawService createImmediateWithdrawService(int unwithdrawableDays) {
        if (unwithdrawableDays < 1) {
            throw new IllegalArgumentException();
        }

        return new ImmediateWithdrawService(unwithdrawableDays);
    }
}
