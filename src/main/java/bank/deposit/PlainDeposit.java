package bank.deposit;

import bank.service.SavingService;
import bank.debt.Debt;

public class PlainDeposit extends AbstractDeposit {
    PlainDeposit(Debt debt, SavingService savingService, int monthTerm) {
        super(debt, savingService, monthTerm);
    }
}
