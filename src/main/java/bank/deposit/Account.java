package bank.deposit;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Account {
    LocalDate getOpeningDate();

    BigDecimal getBalance();
    void setBalance(BigDecimal balance);
}
