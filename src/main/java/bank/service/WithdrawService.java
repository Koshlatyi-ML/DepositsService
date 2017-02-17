package bank.service;

import java.math.BigDecimal;

public interface WithdrawService extends Service {
    BigDecimal withdraw();
}
