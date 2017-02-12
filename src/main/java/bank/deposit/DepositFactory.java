package bank.deposit;

import bank.service.ImmediateWithdrawService;
import bank.service.SavingService;

import java.util.HashMap;
import java.util.Map;

public class DepositFactory {
    private static final Map<DepositServices, Class<? extends SavingService>> map = new HashMap<>();

    static {
       map.put(DepositServices.REPLENISHABLE, ReplenishableDeposit.class);
       map.put(DepositServices.WITHDRAWABLE, ImmediateWithdrawService.class);
    }

    public static SavingService createDeposit(DepositServices... services) {
        return null;
    }

    public static void main(String[] args) {
    }
}
