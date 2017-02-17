package bank.service;

import java.math.BigDecimal;

public interface ReplenishService extends Service {
    void replenish(BigDecimal replenishment);
}
