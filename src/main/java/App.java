import bank.Bank;
import bank.debt.Currency;
import bank.debt.CurrencyCode;
import bank.debt.Debt;
import bank.deposit.Deposit;
import bank.deposit.DepositFactory;
import bank.service.PlainSavingService;
import bank.service.ReplenishService;
import bank.service.SavingService;
import bank.service.WithdrawService;
import bank.service.description.ReplenishServiceDescription;
import bank.service.description.WithdrawServiceDescription;
import filter.DepositFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    static List<Bank> init() {
        Currency uah = Currency.getCurrency(CurrencyCode.UAH);
        Currency eur = Currency.getCurrency(CurrencyCode.EUR);
        Currency rub = Currency.getCurrency(CurrencyCode.RUB);
        Currency usd = Currency.getCurrency(CurrencyCode.USD);

        Debt uah15 = new Debt(uah, BigDecimal.valueOf(15));
        Debt uah20 = new Debt(uah, BigDecimal.valueOf(20));
        Debt uah18 = new Debt(uah, BigDecimal.valueOf(18));
        Debt uah18_5 = new Debt(uah, BigDecimal.valueOf(18.5));
        Debt uah22 = new Debt(uah, BigDecimal.valueOf(22));
        Debt uah21_75 = new Debt(uah, BigDecimal.valueOf(21.75));

        Debt eur5_25 = new Debt(eur, BigDecimal.valueOf(5.25));
        Debt eur6_3 = new Debt(eur, BigDecimal.valueOf(6.3));
        Debt eur4_75 = new Debt(eur, BigDecimal.valueOf(4.75));
        Debt eur5 = new Debt(eur, BigDecimal.valueOf(5));
        Debt eur5_5 = new Debt(eur, BigDecimal.valueOf(5.5));
        Debt eur6_75 = new Debt(eur, BigDecimal.valueOf(6.75));

        Debt rub6_75 = new Debt(rub, BigDecimal.valueOf(6.75));
        Debt rub8_25 = new Debt(rub, BigDecimal.valueOf(8.25));
        Debt rub7 = new Debt(rub, BigDecimal.valueOf(7));
        Debt rub7_5 = new Debt(rub, BigDecimal.valueOf(7.5));
        Debt rub7_7 = new Debt(rub, BigDecimal.valueOf(7.7));
        Debt rub6_1 = new Debt(rub, BigDecimal.valueOf(6.1));

        Debt usd5 = new Debt(usd, BigDecimal.valueOf(5));
        Debt usd7_5 = new Debt(usd, BigDecimal.valueOf(7.5));
        Debt usd8_5 = new Debt(usd, BigDecimal.valueOf(8.5));
        Debt usd1_5 = new Debt(usd, BigDecimal.valueOf(1.5));
        Debt usd4_5 = new Debt(usd, BigDecimal.valueOf(4.5));
        Debt usd6_15 = new Debt(usd, BigDecimal.valueOf(6.15));

        BigDecimal min = BigDecimal.valueOf(100);
        BigDecimal max = BigDecimal.valueOf(10000000);

        SavingService savingService12 = new PlainSavingService(min, max, 12);
        SavingService savingService6 = new PlainSavingService(min, max, 6);
        SavingService savingService3 = new PlainSavingService(min, max, 3);
        SavingService savingService24 = new PlainSavingService(min, max, 24);
        SavingService savingService60 = new PlainSavingService(min, max, 60);
        SavingService savingService18 = new PlainSavingService(min, max, 18);

        int term = 120;

        Deposit d1 = DepositFactory.createPlainDeposit(uah15, savingService12, term);
        Deposit d2 = DepositFactory.createPlainDeposit(uah18, savingService6, term);
        Deposit d3 = DepositFactory.createPlainDeposit(eur4_75, savingService12, term);
        Deposit d4 = DepositFactory.createPlainDeposit(eur5, savingService6, term);
        Deposit d5 = DepositFactory.createPlainDeposit(rub6_1, savingService12, term);
        Deposit d6 = DepositFactory.createPlainDeposit(rub6_75, savingService6, term);
        Deposit d7 = DepositFactory.createPlainDeposit(usd1_5, savingService12, term);
        Deposit d8 = DepositFactory.createPlainDeposit(usd4_5, savingService6, term);

        List<Deposit> deposits = new ArrayList<Deposit>();
        deposits.add(d1);
        deposits.add(d2);
        deposits.add(d3);
        deposits.add(d4);
        deposits.add(d5);
        deposits.add(d6);
        deposits.add(d7);
        deposits.add(d8);

        Bank privat = new Bank("PrivatBank", new ArrayList<>(deposits));

        min = BigDecimal.ONE;
        max = BigDecimal.valueOf(250000);
        int replTerm = 24;
        ReplenishServiceDescription replenishServiceDescription
                = new ReplenishServiceDescription(min, max, replTerm);

        d1 = DepositFactory.createReplenishableDeposit(uah18_5, savingService3,
                term, replenishServiceDescription);
        d2 = DepositFactory.createReplenishableDeposit(uah20, savingService18,
                term, replenishServiceDescription);
        d3 = DepositFactory.createReplenishableDeposit(eur5_5, savingService3,
                term, replenishServiceDescription);
        d4 = DepositFactory.createReplenishableDeposit(eur5_25, savingService18,
                term, replenishServiceDescription);
        d5 = DepositFactory.createReplenishableDeposit(rub7, savingService3,
                term, replenishServiceDescription);
        d6 = DepositFactory.createReplenishableDeposit(rub7_5, savingService18,
                term, replenishServiceDescription);
        d7 = DepositFactory.createReplenishableDeposit(usd5, savingService3,
                term, replenishServiceDescription);
        d8 = DepositFactory.createReplenishableDeposit(usd6_15, savingService18,
                term, replenishServiceDescription);

        deposits.clear();
        deposits.add(d1);
        deposits.add(d2);
        deposits.add(d3);
        deposits.add(d4);
        deposits.add(d5);
        deposits.add(d6);
        deposits.add(d7);
        deposits.add(d8);

        Bank raiffeisen = new Bank("Raiffeisen Bank Aval", new ArrayList<>(deposits));

        int uwDays = 10;
        WithdrawServiceDescription withdrawServiceDescription
                = new WithdrawServiceDescription(10);

        d1 = DepositFactory.createCallDeposit(uah21_75, savingService24,
                term, withdrawServiceDescription);
        d2 = DepositFactory.createCallDeposit(uah22, savingService60,
                term, withdrawServiceDescription);
        d3 = DepositFactory.createCallDeposit(eur6_3, savingService24,
                term, withdrawServiceDescription);
        d4 = DepositFactory.createCallDeposit(eur6_75, savingService60,
                term, withdrawServiceDescription);
        d5 = DepositFactory.createCallDeposit(rub7_7, savingService24,
                term, withdrawServiceDescription);
        d6 = DepositFactory.createCallDeposit(rub8_25, savingService60,
                term, withdrawServiceDescription);
        d7 = DepositFactory.createCallDeposit(usd7_5, savingService24,
                term, withdrawServiceDescription);
        d8 = DepositFactory.createCallDeposit(usd8_5, savingService60,
                term, withdrawServiceDescription);

        deposits.clear();
        deposits.add(d1);
        deposits.add(d2);
        deposits.add(d3);
        deposits.add(d4);
        deposits.add(d5);
        deposits.add(d6);
        deposits.add(d7);
        deposits.add(d8);

        Bank oschadbank = new Bank("Oschadbank", new ArrayList<>(deposits));

        d1 = DepositFactory.createAllInclusiveDeposit(uah18_5, savingService24,
                term, replenishServiceDescription, withdrawServiceDescription);
        d2 = DepositFactory.createAllInclusiveDeposit(uah21_75, savingService6,
                term, replenishServiceDescription, withdrawServiceDescription);
        d3 = DepositFactory.createAllInclusiveDeposit(eur5_5, savingService3,
                term, replenishServiceDescription, withdrawServiceDescription);
        d4 = DepositFactory.createAllInclusiveDeposit(eur6_3, savingService18,
                term, replenishServiceDescription, withdrawServiceDescription);
        d5 = DepositFactory.createAllInclusiveDeposit(rub6_1, savingService12,
                term, replenishServiceDescription, withdrawServiceDescription);
        d6 = DepositFactory.createAllInclusiveDeposit(rub8_25, savingService18,
                term, replenishServiceDescription, withdrawServiceDescription);
        d7 = DepositFactory.createAllInclusiveDeposit(usd1_5, savingService6,
                term, replenishServiceDescription, withdrawServiceDescription);
        d8 = DepositFactory.createAllInclusiveDeposit(usd6_15, savingService24,
                term, replenishServiceDescription, withdrawServiceDescription);

        deposits.clear();
        deposits.add(d1);
        deposits.add(d2);
        deposits.add(d3);
        deposits.add(d4);
        deposits.add(d5);
        deposits.add(d6);
        deposits.add(d7);
        deposits.add(d8);

        Bank wellsFargo = new Bank("Wells Fargo", new ArrayList<>(deposits));

        return new ArrayList<Bank>() {{
            add(privat);
            add(raiffeisen);
            add(oschadbank);
            add(wellsFargo);
        }};
    }

    private static void printInfo(Map.Entry<Bank, List<Deposit>> entry) {
        System.out.println(entry.getKey().getName());
        for (Deposit deposit : entry.getValue()) {
            for (int i = 0; i < 50; i++) {
                System.out.print(",");
            }
            System.out.println();
            System.out.println("Currency: " + deposit.getCurrency().getCode());
            System.out.printf("Interest: %.3f%%\n", deposit.getInterest());
            System.out.println("MinTerm: " + deposit.getMinMonthsTerm()
                    + " months");
            System.out.println("is replenishable: "
                    + ReplenishService.class.isAssignableFrom(deposit.getClass()));
            System.out.println("is withdrawable: "
                    + ReplenishService.class.isAssignableFrom(deposit.getClass()));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<Bank> offers = init();

        List<Map.Entry<Bank, List<Deposit>>> query1 = DepositFilter.of(offers)
                .withCurrency(CurrencyCode.EUR)
                .withInterestMoreThan(4.5)
                .withTermBetween(6, 24)
                .withServices(ReplenishService.class)
                .filter();

        List<Map.Entry<Bank, List<Deposit>>> query2 = DepositFilter.of(offers)
                .withCurrency(CurrencyCode.USD)
                .withInterestMoreThan(7)
                .withTermBetween(1, 240)
                .withServices(WithdrawService.class)
                .filter();

        List<Map.Entry<Bank, List<Deposit>>> query3 = DepositFilter.of(offers)
                .withCurrency(CurrencyCode.UAH)
                .withInterestMoreThan(21)
                .withTermBetween(1, 306)
                .filter();

        List<Map.Entry<Bank, List<Deposit>>> query4 = DepositFilter.of(offers)
                .withCurrency(CurrencyCode.RUB)
                .filter();

        System.out.println("Query 1");
        query1.forEach(App::printInfo);
        System.out.println("Query 2");
        query2.forEach(App::printInfo);
        System.out.println("Query 3");
        query3.forEach(App::printInfo);
        System.out.println("Query 4");
        query4.forEach(App::printInfo);
    }

}
