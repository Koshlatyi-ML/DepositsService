package bank.deposit;

import bank.service.PlainReplenishService;
import bank.service.ReplenishService;
import bank.service.description.ReplenishServiceDescription;
import bank.service.SavingService;
import bank.debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ReplenishServiceDeposit extends AbstractDeposit implements ReplenishService {
    private final LocalDate openingDate = this.getOpeningDate();
    private final LocalDate closingDate = openingDate
            .plusMonths(getMonthTerm())
            .plusDays(1);

    private PlainReplenishService replenishService;

    ReplenishServiceDeposit(Debt debt, SavingService savingService, int monthTerm,
                            PlainReplenishService replenishService) {
        super(debt, savingService, monthTerm);
        this.replenishService = replenishService;
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

    public BigDecimal getMinReplenishment() {
        return replenishService.getServiceDescription().getMinReplenishment();
    }

    public void setMinReplenishment(BigDecimal minReplenishment) {
        if (minReplenishment.compareTo(getMaxBalance()) > 0) {
            throw new IllegalStateException();
        }

        ReplenishServiceDescription serviceDescription = replenishService.getServiceDescription();
        serviceDescription.setMinReplenishment(minReplenishment);
        replenishService.setServiceDescription(serviceDescription);
    }

    public BigDecimal getMaxReplenishment() {
        return replenishService.getServiceDescription().getMaxReplenishment();
    }

    public void setMaxReplenishment(BigDecimal maxReplenishment) {
        ReplenishServiceDescription serviceDescription = replenishService.getServiceDescription();
        serviceDescription.setMaxReplenishment(maxReplenishment);
        replenishService.setServiceDescription(serviceDescription);
    }

    public int getReplenishableMonths() {
        return replenishService.getServiceDescription().getReplenishableMonths();
    }

    public void setReplenishableMonths(int replenishableMonths) {
        ReplenishServiceDescription serviceDescription = replenishService.getServiceDescription();
        serviceDescription.setReplenishableMonths(replenishableMonths);
        replenishService.setServiceDescription(serviceDescription);
    }

    public boolean isEngaged() {
        return replenishService.hasDeposit();
    }

    @Override
    public void replenish(BigDecimal replenishment) {
        replenishService.replenish(replenishment);
    }

}
