package bank.deposit;

import bank.service.ReplenishService;
import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ReplenishableDeposit extends AbstractDeposit implements Replenishable {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    private ReplenishService replenishService;

    ReplenishableDeposit(Debt debt,
                         SavingService savingService,
                         ReplenishService replenishService,
                         int monthTerm) {
        super(debt, savingService, monthTerm);
        this.replenishService = replenishService;
    }

    @Override
    public void replenish(BigDecimal replenishment) {
        replenishService.replenish(replenishment);
    }

    @Override
    public BigDecimal close() {
        if (Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        return super.close();
    }

    public ReplenishService getReplenishService() {
        return replenishService;
    }

    public void setReplenishService(ReplenishService replenishService) {
        if (Objects.isNull(replenishService)) {
            throw new NullPointerException();
        }

        this.replenishService = replenishService;
    }
}
