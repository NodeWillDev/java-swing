package br.com.financiamento;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FinancingFrame().setVisible(true);
        });
    }
}
