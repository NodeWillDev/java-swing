package br.com.financiamento;

public class FinancingCalculator {
    public static final double TAXA = 0.32;

    public static FinancingResult calculate(double vehicleValue, double downPayment, int installments) {
        double financedValue = vehicleValue - downPayment;
        double totalValue = financedValue * (1 + TAXA);
        double installmentValue = totalValue / installments;

        return new FinancingResult(financedValue, installmentValue, totalValue);
    }
}
