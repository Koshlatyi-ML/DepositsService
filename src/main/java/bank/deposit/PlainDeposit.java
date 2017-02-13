package bank.deposit;

import bank.service.SavingService;
import debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PlainDeposit extends AbstractDeposit {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    PlainDeposit(Debt debt, SavingService savingService, int monthTerm) {
        super(debt, savingService, monthTerm);
    }

    @Override
    public BigDecimal close() {
        if (Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        return super.close();
    }
}
