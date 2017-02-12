package bank.deposit;

import bank.service.SavingService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

/**
 * Pay monthly interests to income;
 */
public class PlainDeposit extends AbstractDeposit {
    private final LocalDate openingDate = this.getOpeningDate();

    private BigDecimal income;

    private BigDecimal payInterest() {
        BigDecimal balance = this.getBalance();

        return balance.multiply(this.getDebt().getInterest());
    }

    public BigDecimal getIncome() {
        return income;
    }

    @Override
    void processMonthlyTransaction() {
        if (LocalDate.now().compareTo(openingDate) < 0) {
            throw new IllegalStateException();
        }

        SavingService savingService = this.getSavingService();

        if (Period.between(openingDate, LocalDate.now()).getMonths() <= savingService.getMonthsTerm()) {
            this.income = this.payInterest();
        }
    }
}
