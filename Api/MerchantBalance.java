package Api;

import java.util.UUID;

public class MerchantBalance {
    private String merchantId;
    private double creditLimit;
    private double outstandingBalance;
    private double availableCredit;

    public MerchantBalance(String merchant_id, double creditLimit, double outstandingBalance_double, double availableBalance_double) {
        this.merchantId = "";
        this.creditLimit = 0;
        this.outstandingBalance = 0;
        this.availableCredit = 0;

    }
}
