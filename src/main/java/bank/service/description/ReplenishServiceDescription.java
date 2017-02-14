package bank.service.description;

import java.math.BigDecimal;
import java.util.Objects;

public class ReplenishServiceDescription {
    private BigDecimal minReplenishment;
    private BigDecimal maxReplenishment;

    private int replenishableMonths;

    public ReplenishServiceDescription(BigDecimal minReplenishment,
                                       BigDecimal maxReplenishment,
                                       int replenishableMonths) {
        this.minReplenishment = minReplenishment;
        this.maxReplenishment = maxReplenishment;
        this.replenishableMonths = replenishableMonths;
    }

    public BigDecimal getMinReplenishment() {
        return minReplenishment;
    }

    public void setMinReplenishment(BigDecimal minReplenishment) {
        if (Objects.isNull(minReplenishment)) {
            throw new NullPointerException();
        }

        if (minReplenishment.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (minReplenishment.compareTo(maxReplenishment) > 0) {
            throw new IllegalArgumentException();
        }

        this.minReplenishment = minReplenishment;
    }

    public BigDecimal getMaxReplenishment() {
        return maxReplenishment;
    }

    public void setMaxReplenishment(BigDecimal maxReplenishment) {
        if (Objects.isNull(maxReplenishment)) {
            throw new NullPointerException();
        }

        if (maxReplenishment.signum() == -1) {
            throw new IllegalArgumentException();
        }

        if (maxReplenishment.compareTo(minReplenishment) < 0) {
            throw new IllegalArgumentException();
        }

        this.maxReplenishment = maxReplenishment;
    }

    public int getReplenishableMonths() {
        return replenishableMonths;
    }

    public void setReplenishableMonths(int replenishableMonths) {
        if (replenishableMonths < 0) {
            throw new IllegalArgumentException();
        }

        this.replenishableMonths = replenishableMonths;
    }
}
