package bank.service;

import java.time.Period;

public class ImmediateWithdrawService {
    private int unwithdrawableDays;

    public ImmediateWithdrawService(int unwithdrawableDays) {
        this.unwithdrawableDays = unwithdrawableDays;
    }

    public int getUnwithdrawableDays() {
        return unwithdrawableDays;
    }

    public void setUnwithdrawableDays(int unwithdrawableDays) {
        this.unwithdrawableDays = unwithdrawableDays;
    }
}
