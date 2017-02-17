package bank.deposit.comparator;

import bank.deposit.Deposit;

import java.util.Comparator;

public class CurrencyComparator implements Comparator<Deposit> {
    @Override
    public int compare(Deposit o1, Deposit o2) {
        int ordinal1 = o1.getCurrency().getCode().ordinal();
        int ordinal2 = o2.getCurrency().getCode().ordinal();

        return ordinal1 - ordinal2;
    }
}
