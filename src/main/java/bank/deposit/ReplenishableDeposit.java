package bank.deposit;

import bank.service.ReplenishService;
import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReplenishableDeposit extends AbstractDeposit implements Replenishable {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    ReplenishService replenishService;
    private BigDecimal income = BigDecimal.ZERO;

    ReplenishableDeposit(Debt debt,
                         SavingService savingService,
                         ReplenishService replenishService,
                         int monthTerm) {
        super(debt, savingService, monthTerm);
        this.replenishService = replenishService;
    }

    @Override
    public void replenish(BigDecimal replenishment) {
        int replenishableMonth = replenishService.getReplenishableMonths();
        LocalDate replenishableEndDate = openingDate.plusMonths(replenishableMonth);

        if (!Deposits.isBetween(LocalDate.now(), openingDate, replenishableEndDate)) {
            throw new IllegalStateException();
        }

        BigDecimal minReplenishment = replenishService.getMinReplenishment();
        BigDecimal maxReplenishment = replenishService.getMinReplenishment();

        if (!Deposits.isBetween(replenishment, minReplenishment, maxReplenishment)) {
            throw new IllegalArgumentException();
        }

        BigDecimal balance = getBalance();
        this.setBalance(balance.add(replenishment));
    }

    @Override
    void processMonthlyTransaction() {
        if (!Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        this.income = Deposits.getAccruedInterest(getBalance(), getDebt().getInterest());
    }

    @Override
    public BigDecimal close() {
        if (Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        return super.close();
    }

}
