package bank.deposit;

import java.math.BigDecimal;

public interface Deposit {
    void open(BigDecimal principalSum);
    BigDecimal close();
}
