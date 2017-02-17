package bank.service;

import bank.deposit.Deposit;
import bank.deposit.Deposits;
import bank.service.description.WithdrawServiceDescription;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PlainWithdrawService implements WithdrawService {
    private WithdrawServiceDescription serviceDescription;
    private Deposit deposit;

    public PlainWithdrawService(WithdrawServiceDescription serviceDescription) {
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

    public WithdrawServiceDescription getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(WithdrawServiceDescription serviceDescription) {
        if (Objects.isNull(serviceDescription)) {
            throw new NullPointerException();
        }

        this.serviceDescription = serviceDescription;
    }

    public int getUnwithdrawableDays() {
        return serviceDescription.getUnwithdrawableDays();
    }

    public void setUnwithdrawableDays(int unwithdrawableDays) {
        LocalDate lastUnwithdrawableDate = deposit.getOpeningDate().plusDays(unwithdrawableDays);
        LocalDate closingDate = deposit.getOpeningDate()
                .plusMonths(deposit.getMonthTerm())
                .plusDays(1);

        if ((lastUnwithdrawableDate).compareTo(closingDate) >= 0) {
            throw new IllegalStateException();
        }

        serviceDescription.setUnwithdrawableDays(unwithdrawableDays);
    }

    @Override
    public BigDecimal withdraw() {
        LocalDate openingDate = deposit.getOpeningDate();
        LocalDate closingDate = openingDate
                .plusMonths(deposit.getMonthTerm())
                .plusDays(1);

        int unwithdrawableDays = serviceDescription.getUnwithdrawableDays();
        LocalDate withdrawStartDate = openingDate.plusDays(unwithdrawableDays);

        if (!Deposits.isBetween(LocalDate.now(), withdrawStartDate, closingDate)) {
            throw new IllegalStateException();
        }

        if (!Deposits.isBetween(LocalDate.now(), openingDate, closingDate)) {
            throw new IllegalStateException();
        }

        return deposit.close();
    }
}
