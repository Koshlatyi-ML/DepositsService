package bank.deposit;

import bank.service.SavingService;
import bank.service.WithdrawService;
import bank.service.Withdrawable;
import bank.service.description.WithdrawServiceDescription;
import bank.debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class CallDeposit extends AbstractDeposit implements Withdrawable {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    private WithdrawService withdrawService;

    CallDeposit(Debt debt, SavingService savingService, int monthTerm,
                WithdrawService withdrawService) {
        super(debt, savingService, monthTerm);
        this.withdrawService = withdrawService;
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

    public int getUnwithdrawableDays() {
        return withdrawService.getUnwithdrawableDays();
    }

    public void setUnwithdrawableDays(int unwithdrawableDays) {
        withdrawService.setUnwithdrawableDays(unwithdrawableDays);
    }

    public boolean isEngaged() {
        return withdrawService.hasDeposit();
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
    public BigDecimal withdraw() {
        return withdrawService.withdraw();
    }
}
