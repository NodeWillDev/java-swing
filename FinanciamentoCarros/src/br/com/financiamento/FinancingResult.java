package br.com.financiamento;

public class FinancingResult {
    private final double financedValue;
    private final double installmentValue;
    private final double totalValue;

    public FinancingResult(double financedValue, double installmentValue, double totalValue) {
        this.financedValue = financedValue;
        this.installmentValue = installmentValue;
        this.totalValue = totalValue;
    }

    public double getFinancedValue() {
        return financedValue;
    }

    public double getInstallmentValue() {
        return installmentValue;
    }

    public double getTotalValue() {
        return totalValue;
    }
}
