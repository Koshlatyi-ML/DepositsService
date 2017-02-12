package bank.service;

import java.math.BigDecimal;

public class ReplenishService {
    private BigDecimal minReplenishment;
    private BigDecimal maxReplenishment;

    private int replenishableMonths;

    public ReplenishService(BigDecimal minReplenishment,
                            BigDecimal maxReplenishment,
                            int replenishableMonths) {
        this.minReplenishment = minReplenishment;
        this.maxReplenishment = maxReplenishment;
        this.replenishableMonths = replenishableMonths;
    }

    public BigDecimal getMinReplenishment() {
        return minReplenishment;
    }

    public BigDecimal getMaxReplenishment() {
        return maxReplenishment;
    }

    public int getReplenishableMonths() {
        return replenishableMonths;
    }

    public void setReplenishableMonths(int replenishableMonths) {
        this.replenishableMonths = replenishableMonths;
    }
}
