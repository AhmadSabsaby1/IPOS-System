package cust.model;

public class DiscountData{
    private double tier1Discount;
    private int tier1Threshold;
    private double tier2Discount;
    private int tier2Threshold;
    private double tier3Discount;

    public DiscountData(int tier1Threshold, int tier2Threshold, double tier1Discount, double tier2Discount, double tier3Discount){
        this.tier1Threshold = tier1Threshold;
        this.tier2Threshold = tier2Threshold;
        this.tier1Discount = tier1Discount;
        this.tier2Discount = tier2Discount;
        this.tier3Discount = tier3Discount;
    }
    public double getDiscount(int amount){
        if (amount <= tier1Threshold)
            return tier1Discount;
        else if (amount <= tier2Threshold)
            return tier2Discount;
        else
            return tier3Discount;
    }

    public double getTier1Discount() {
        return tier1Discount;
    }

    public int getTier1Threshold() {
        return tier1Threshold;
    }

    public double getTier2Discount() {
        return tier2Discount;
    }

    public int getTier2Threshold() {
        return tier2Threshold;
    }

    public double getTier3Discount() {
        return tier3Discount;
    }

    public void setTier1Discount(double tier1Discount) {
        this.tier1Discount = tier1Discount;
    }

    public void setTier1Threshold(int tier1Threshold) {
        this.tier1Threshold = tier1Threshold;
    }

    public void setTier2Discount(double tier2Discount) {
        this.tier2Discount = tier2Discount;
    }

    public void setTier2Threshold(int tier2Threshold) {
        this.tier2Threshold = tier2Threshold;
    }

    public void setTier3Discount(double tier3Discount) {
        this.tier3Discount = tier3Discount;
    }
}