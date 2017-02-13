package bank.deposit;

import bank.service.ImmediateWithdrawService;
import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class CallDeposit extends AbstractDeposit implements Withdrawable {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    private ImmediateWithdrawService withdrawService;

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
        return withdrawService.withdraw();
    }

    public ImmediateWithdrawService getWithdrawService() {
        return withdrawService;
    }

    public void setWithdrawService(ImmediateWithdrawService withdrawService) {
        if (Objects.isNull(withdrawService)) {
            throw new NullPointerException();
        }

        this.withdrawService = withdrawService;
    }
}
