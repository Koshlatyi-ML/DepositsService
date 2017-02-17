package bank.deposit.comparator;

import bank.deposit.Deposit;

import java.util.Comparator;

public class InterestComparator implements Comparator<Deposit> {

    @Override
    public int compare(Deposit o1, Deposit o2) {
        return o1.getInterest().compareTo(o2.getInterest());
    }
}
