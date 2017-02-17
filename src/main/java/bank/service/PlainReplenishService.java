package bank.service;

import bank.deposit.Deposit;
import bank.deposit.Deposits;
import bank.service.description.ReplenishServiceDescription;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PlainReplenishService implements ReplenishService {
    private ReplenishServiceDescription serviceDescription;
    private Deposit deposit;

    public PlainReplenishService(ReplenishServiceDescription serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public ReplenishServiceDescription getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(ReplenishServiceDescription serviceDescription) {
        if (Objects.isNull(serviceDescription)) {
            throw new NullPointerException();
        }

        this.serviceDescription = serviceDescription;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        if (Objects.isNull(deposit)) {
            throw new NullPointerException();
        }

        if (deposit.isOpened()) {
            throw new IllegalArgumentException();
        }

        this.deposit = deposit;
    }

    public BigDecimal getMinReplenishment() {
        return serviceDescription.getMinReplenishment();
    }

    public void setMinReplenishment(BigDecimal minReplenishment) {
        if (minReplenishment.compareTo(deposit.getMaxBalance()) > 0) {
            throw new IllegalStateException();
        }

        serviceDescription.setMinReplenishment(minReplenishment);
    }

    public BigDecimal getMaxReplenishment() {
        return serviceDescription.getMaxReplenishment();
    }

    public void setMaxReplenishment(BigDecimal maxReplenishment) {
        serviceDescription.setMaxReplenishment(maxReplenishment);
    }

    public int getReplenishableMonths() {
        return serviceDescription.getReplenishableMonths();
    }

    public void setReplenishableMonths(int replenishableMonths) {
        serviceDescription.setReplenishableMonths(replenishableMonths);
    }

    public boolean hasDeposit() {
        return !Objects.isNull(deposit);
    }

    @Override
    public void replenish(BigDecimal replenishment) {
        LocalDate openingDate = deposit.getOpeningDate();
        int replenishableMonths = serviceDescription.getReplenishableMonths();

        LocalDate replenishExpirationTime = openingDate.plusMonths(replenishableMonths);

        if (!Deposits.isBetween(LocalDate.now(), openingDate, replenishExpirationTime)) {
            throw new IllegalStateException();
        }

        BigDecimal minReplenishment = serviceDescription.getMinReplenishment();
        BigDecimal maxReplenishment = serviceDescription.getMaxReplenishment();

        if (!Deposits.isBetween(replenishment, minReplenishment, maxReplenishment)) {
            throw new IllegalArgumentException();
        }

        BigDecimal balance = deposit.getBalance();
        deposit.setBalance(balance.add(replenishment));
    }
}
