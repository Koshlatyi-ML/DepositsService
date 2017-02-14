package bank.deposit;

import bank.service.*;
import bank.service.description.WithdrawServiceDescription;
import bank.service.description.ReplenishServiceDescription;
import bank.debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class AllInclusiveDeposit extends AbstractDeposit implements Replenishable, Withdrawable {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    private ReplenishService replenishService;
    private WithdrawService withdrawService;

    AllInclusiveDeposit(Debt debt, SavingService savingService, int monthTerm,
                        ReplenishService replenishService, WithdrawService withdrawService) {
        super(debt, savingService, monthTerm);
        this.replenishService = replenishService;
        this.withdrawService = withdrawService;
    }

    public ReplenishServiceDescription getReplenishServiceDescription() {
        return replenishService.getServiceDescription();
    }

    public void setReplenishServiceDescription(ReplenishServiceDescription replenishServiceDescription) {
        if (Objects.isNull(replenishServiceDescription)) {
            throw new NullPointerException();
        }

        this.replenishService.setServiceDescription(replenishServiceDescription);
    }

    public WithdrawServiceDescription getWithdrawServiceDescription() {
        return withdrawService.getServiceDescription();
    }

    public void setWithdrawServiceDescription(WithdrawServiceDescription withdrawServiceDescription) {
        if (Objects.isNull(withdrawServiceDescription)) {
            throw new NullPointerException();
        }

        this.withdrawService.setServiceDescription(withdrawServiceDescription);
    }

    public BigDecimal getMinReplenishment() {
        return replenishService.getMinReplenishment();
    }

    public void setMinReplenishment(BigDecimal minReplenishment) {
        replenishService.setMinReplenishment(minReplenishment);
    }

    public BigDecimal getMaxReplenishment() {
        return replenishService.getMaxReplenishment();
    }

    public void setMaxReplenishment(BigDecimal maxReplenishment) {
        replenishService.setMaxReplenishment(maxReplenishment);
    }

    public int getReplenishableMonths() {
        return replenishService.getReplenishableMonths();
    }

    public void setReplenishableMonths(int replenishableMonths) {
        replenishService.setReplenishableMonths(replenishableMonths);
    }

    public int getUnwithdrawableDays() {
        return withdrawService.getUnwithdrawableDays();
    }

    public void setUnwithdrawableDays(int unwithdrawableDays) {
        withdrawService.setUnwithdrawableDays(unwithdrawableDays);
    }

    @Override
    public void open(BigDecimal principalSum) {
        int unwithdrawableDays = withdrawService.getServiceDescription().getUnwithdrawableDays();
        LocalDate lastUnwithdrawableDate = LocalDate.now().plusDays(unwithdrawableDays);

        if ((lastUnwithdrawableDate).compareTo(closingDate) >= 0) {
            long boundaryWithdrawPeriod = ChronoUnit.DAYS.between(openingDate,closingDate.minusDays(1));
            setUnwithdrawableDays((int) boundaryWithdrawPeriod);
        }

        super.open(principalSum);
    }

    @Override
    public void replenish(BigDecimal replenishment) {
        replenishService.replenish(replenishment);
    }

    @Override
    public BigDecimal withdraw() {
        return withdrawService.withdraw();
    }

}
