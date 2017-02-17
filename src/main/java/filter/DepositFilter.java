package filter;

import bank.Bank;
import bank.debt.CurrencyCode;
import bank.deposit.Deposit;
import bank.service.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepositFilter {
    private List<Bank> banks;
    private List<Map.Entry<Bank, List<Deposit>>> flow;

    private final double ACCURACY = 0.0001;
    private boolean isLoaded = false;

    private DepositFilter(List<Bank> banks) {
        this.banks = banks;
    }

    public static DepositFilter of(List<Bank> banks) {
        return new DepositFilter(banks);
    }

    private void loadData() {
        class BankDepositsTuple implements Map.Entry<Bank, List<Deposit>> {
            Bank key;
            List<Deposit> value;

            public BankDepositsTuple(Bank key, List<Deposit> value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public Bank getKey() {
                return key;
            }

            @Override
            public List<Deposit> getValue() {
                return value;
            }

            @Override
            public List<Deposit> setValue(List<Deposit> value) {
                this.value = value;
                return value;
            }
        }

        flow = new ArrayList<>();
        for (Bank bank : banks) {
            flow.add(new BankDepositsTuple(bank, bank.getDeposits()));
        }

        isLoaded = true;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public DepositFilter addBank(Bank bank) {
        if (bank == null) {
            throw new NullPointerException();
        }

        banks.add(bank);
        return this;
    }

    public DepositFilter removeBank(Bank bank) {
        if (bank == null) {
            throw new NullPointerException();
        }

        banks.remove(bank);
        return this;
    }

    public DepositFilter withInterest(double interest) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> {
                        double actual = deposit.getInterest().doubleValue();
                        return Math.abs(actual - interest) < ACCURACY;
                    })
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public DepositFilter withInterestMoreThan(double min) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();

            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> {
                        double actual = deposit.getInterest().doubleValue();
                        return actual - min > ACCURACY;
                    })
                    .collect(Collectors.toList())
            );

            if (bankDeposits.getValue().isEmpty()) {
                iter.remove();
            }
        }

        return this;
    }

    public DepositFilter withInterestLessThan(double max) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> {
                        double actual = deposit.getInterest().doubleValue();
                        return Math.abs(actual - max) < 0 - ACCURACY;
                    })
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public DepositFilter withInterestBetween(double min, double max) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> {
                        double actual = deposit.getInterest().doubleValue();
                        return actual - min > 0 - ACCURACY
                                && max - actual > 0 - ACCURACY;
                    })
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public DepositFilter withCurrency(CurrencyCode currencyCode) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(d -> d.getCurrency().getCode() == currencyCode)
                    .collect(Collectors.toList()));
        }

        return this;
    }

    public DepositFilter withTerm(int months) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> deposit.getMinMonthsTerm() == months)
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public DepositFilter withTermMoreThan(double minTerm) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> deposit.getMinMonthsTerm() >= minTerm)
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public DepositFilter withTermtLessThan(double maxTerm) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> deposit.getMinMonthsTerm() <= maxTerm)
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public DepositFilter withTermBetween(double min, double max) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> deposit.getMinMonthsTerm() >= min
                            && deposit.getMinMonthsTerm() <= max)
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    @SafeVarargs
    public final DepositFilter withServices(Class<? extends Service>... services) {
        if (!isLoaded) {
            loadData();
        }

        Iterator<Map.Entry<Bank, List<Deposit>>> iter = flow.iterator();
        while (iter.hasNext()) {
            Map.Entry<Bank, List<Deposit>> bankDeposits = iter.next();
            bankDeposits.setValue(bankDeposits.getValue()
                    .stream()
                    .filter(deposit -> ofAllServices(deposit, services))
                    .collect(Collectors.toList())
            );
        }

        return this;
    }

    public List<Map.Entry<Bank, List<Deposit>>> filter() {
        return flow;
    }

    private boolean ofAllServices(Deposit deposit, Class<? extends Service>[] services) {
        boolean isSatisfactory = true;
        for (Class<? extends Service> service : services) {
            if (!service.isAssignableFrom(deposit.getClass())) {
                isSatisfactory = false;
                break;
            }
        }

        return isSatisfactory;
    }

}
