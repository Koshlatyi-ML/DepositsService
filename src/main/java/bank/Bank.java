package bank;

import bank.deposit.Deposit;
import bank.service.PlainSavingService;

import java.util.List;

public class Bank {
    private String name;
    private List<Deposit> deposits;

    public Bank(String name, List<Deposit> deposits) {
        this.name = name;
        this.deposits = deposits;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public String getName() {
        return name;
    }
}
