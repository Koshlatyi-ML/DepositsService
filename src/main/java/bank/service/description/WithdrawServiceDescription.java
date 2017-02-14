package bank.service.description;

public class WithdrawServiceDescription {
    private int unwithdrawableDays;

    public WithdrawServiceDescription(int unwithdrawableDays) {
        this.unwithdrawableDays = unwithdrawableDays;
    }

    public int getUnwithdrawableDays() {
        return unwithdrawableDays;
    }

    public void setUnwithdrawableDays(int unwithdrawableDays) {
        if (unwithdrawableDays < 0) {
            throw new IllegalArgumentException();
        }

        this.unwithdrawableDays = unwithdrawableDays;
    }
}
