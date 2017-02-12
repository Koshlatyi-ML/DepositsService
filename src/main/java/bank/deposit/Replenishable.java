package bank.deposit;

import java.math.BigDecimal;

public interface Replenishable {
    void replenish(BigDecimal replenishment);
}
