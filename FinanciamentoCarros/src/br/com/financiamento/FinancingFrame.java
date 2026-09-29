package br.com.financiamento;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class FinancingFrame extends JFrame {

    private final JComboBox<String> brandCombo;
    private final JTextField modelField;
    private final JComboBox<Integer> yearCombo;
    private final JTextField valueField;

    private final JRadioButton newRadio;
    private final JRadioButton usedRadio;

    private final JPanel usedPanel;
    private final JTextField mileageField;
    private final JTextField ownersField;

    private final JCheckBox downPaymentCheck;
    private final JPanel downPaymentPanel;
    private final JTextField downPaymentField;
    private final JComboBox<Integer> installmentsCombo;

    private final JPanel resultPanel;
    private final JLabel financedValueLabel;
    private final JLabel installmentValueLabel;
    private final JLabel totalValueLabel;

    private final NumberFormat currency =
            NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public FinancingFrame() {
        setTitle("Financiamento de Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 900);
        setMinimumSize(new Dimension(680, 780));
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBorder(new EmptyBorder(22, 35, 25, 35));
        content.setBackground(Color.WHITE);

        JLabel title = new JLabel("Financiamento de Carros", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 12, 0));
        content.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);

        // Dados do veículo
        JPanel vehicleSection = createSection("Dados do Veículo");
        JPanel vehicleForm = new JPanel(new GridBagLayout());
        vehicleForm.setBackground(Color.WHITE);

        brandCombo = new JComboBox<>(new String[] {
                "FIAT", "Chevrolet", "Ford", "Honda", "Hyundai", "Toyota", "Volkswagen"
        });
        modelField = new JTextField();
        yearCombo = createYearCombo();
        valueField = new JTextField();

        addFormRow(vehicleForm, 0, "Marca", brandCombo);
        addFormRow(vehicleForm, 1, "Modelo", modelField);
        addFormRow(vehicleForm, 2, "Ano", yearCombo);
        addFormRow(vehicleForm, 3, "Valor", valueField);

        vehicleSection.add(vehicleForm, BorderLayout.CENTER);
        body.add(vehicleSection);

        // Tipo
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 4));
        typePanel.setBackground(Color.WHITE);

        JLabel typeLabel = new JLabel("Tipo");
        typeLabel.setFont(new Font("SansSerif", Font.BOLD, 15));

        newRadio = new JRadioButton("NOVO");
        usedRadio = new JRadioButton("USADO");
        newRadio.setBackground(Color.WHITE);
        usedRadio.setBackground(Color.WHITE);

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(newRadio);
        typeGroup.add(usedRadio);
        usedRadio.setSelected(true);

        typePanel.add(typeLabel);
        typePanel.add(newRadio);
        typePanel.add(usedRadio);
        body.add(typePanel);

        // Dados do veículo usado
        usedPanel = createSection("Dados do Veículo Usado");
        mileageField = new JTextField();
        ownersField = new JTextField();

        JPanel usedForm = new JPanel(new GridBagLayout());
        usedForm.setBackground(Color.WHITE);
        addFormRow(usedForm, 0, "Quilometragem", mileageField);
        addFormRow(usedForm, 1, "Proprietários", ownersField);
        usedPanel.add(usedForm, BorderLayout.CENTER);
        body.add(usedPanel);

        // Financiamento
        JPanel financingSection = createSection("Financiamento");

        downPaymentCheck = new JCheckBox("Possui entrada?");
        downPaymentCheck.setBackground(Color.WHITE);
        downPaymentCheck.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel financingForm = new JPanel();
        financingForm.setLayout(new BoxLayout(financingForm, BoxLayout.Y_AXIS));
        financingForm.setBackground(Color.WHITE);

        JPanel checkRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));
        checkRow.setBackground(Color.WHITE);
        checkRow.add(downPaymentCheck);
        financingForm.add(checkRow);

        downPaymentPanel = new JPanel(new GridBagLayout());
        downPaymentPanel.setBackground(Color.WHITE);
        downPaymentField = new JTextField();
        installmentsCombo = new JComboBox<>(new Integer[] {12, 24, 36, 48, 60});

        addFormRow(downPaymentPanel, 0, "Entrada", downPaymentField);
        addFormRow(downPaymentPanel, 1, "Parcelas", installmentsCombo);
        financingForm.add(downPaymentPanel);

        financingSection.add(financingForm, BorderLayout.CENTER);
        body.add(financingSection);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton calculateButton = createButton("CALCULAR");
        JButton clearButton = createButton("LIMPAR");

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        body.add(buttonPanel);

        // Resultado
        resultPanel = createSection("Resultado");
        JPanel resultForm = new JPanel();
        resultForm.setLayout(new BoxLayout(resultForm, BoxLayout.Y_AXIS));
        resultForm.setBackground(Color.WHITE);

        financedValueLabel = createResultLabel("Valor financiado: R$ 0,00");
        installmentValueLabel = createResultLabel("Valor da parcela: R$ 0,00");
        totalValueLabel = createResultLabel("Total a pagar: R$ 0,00");

        resultForm.add(financedValueLabel);
        resultForm.add(Box.createVerticalStrut(12));
        resultForm.add(installmentValueLabel);
        resultForm.add(Box.createVerticalStrut(12));
        resultForm.add(totalValueLabel);

        resultPanel.add(resultForm, BorderLayout.CENTER);
        resultPanel.setVisible(false);
        body.add(resultPanel);

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        content.add(scrollPane, BorderLayout.CENTER);

        setContentPane(content);

        // Eventos
        newRadio.addActionListener(e -> updateUsedPanel());
        usedRadio.addActionListener(e -> updateUsedPanel());
        downPaymentCheck.addActionListener(e -> updateDownPaymentPanel());

        calculateButton.addActionListener(e -> calculate());
        clearButton.addActionListener(e -> clearForm());

        updateUsedPanel();
        updateDownPaymentPanel();
    }

    private JComboBox<Integer> createYearCombo() {
        Integer[] years = new Integer[27];
        for (int i = 0; i < years.length; i++) {
            years[i] = 2026 - i;
        }
        return new JComboBox<>(years);
    }

    private JPanel createSection(String title) {
        JPanel section = new JPanel(new BorderLayout(8, 5));
        section.setBackground(Color.WHITE);
        section.setBorder(new CompoundBorder(
                new TitledBorder(
                        new LineBorder(new Color(60, 60, 60), 1, true),
                        title,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 16)
                ),
                new EmptyBorder(8, 12, 12, 12)
        ));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        return section;
    }

    private void addFormRow(JPanel panel, int row, String labelText, JComponent field) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = row;
        labelGbc.insets = new Insets(5, 4, 5, 15);
        labelGbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(label, labelGbc);

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 1;
        fieldGbc.gridy = row;
        fieldGbc.weightx = 1;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.insets = new Insets(5, 4, 5, 4);

        field.setPreferredSize(new Dimension(390, 32));
        panel.add(field, fieldGbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 42));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        return button;
    }

    private JLabel createResultLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return label;
    }

    private void updateUsedPanel() {
        usedPanel.setVisible(usedRadio.isSelected());
        refreshLayout();
    }

    private void updateDownPaymentPanel() {
        boolean hasDownPayment = downPaymentCheck.isSelected();

        for (Component component : downPaymentPanel.getComponents()) {
            component.setVisible(hasDownPayment);
        }

        downPaymentPanel.setVisible(hasDownPayment);
        refreshLayout();
    }

    private void refreshLayout() {
        resultPanel.setVisible(resultPanel.isVisible());
        revalidate();
        repaint();
    }

    private void calculate() {
        try {
            validateVehicleData();

            double vehicleValue = parseMoney(valueField.getText());
            double downPayment = 0;

            if (downPaymentCheck.isSelected()) {
                downPayment = parseMoney(downPaymentField.getText());

                if (downPayment <= 0) {
                    throw new IllegalArgumentException("O valor da entrada deve ser maior que zero.");
                }

                if (downPayment >= vehicleValue) {
                    throw new IllegalArgumentException(
                            "O valor da entrada deve ser menor que o valor do veículo."
                    );
                }
            }

            if (usedRadio.isSelected()) {
                validateUsedVehicleData();
            }

            int installments = (Integer) installmentsCombo.getSelectedItem();

            FinancingResult result = FinancingCalculator.calculate(
                    vehicleValue,
                    downPayment,
                    installments
            );

            financedValueLabel.setText(
                    "Valor financiado: " + currency.format(result.getFinancedValue())
            );
            installmentValueLabel.setText(
                    "Valor da parcela: " + currency.format(result.getInstallmentValue())
            );
            totalValueLabel.setText(
                    "Total a pagar: " + currency.format(result.getTotalValue())
            );

            resultPanel.setVisible(true);
            revalidate();
            repaint();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Dados inválidos",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void validateVehicleData() {
        if (modelField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o modelo do veículo.");
        }

        if (valueField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o valor do veículo.");
        }

        double value = parseMoney(valueField.getText());

        if (value <= 0) {
            throw new IllegalArgumentException("O valor do veículo deve ser maior que zero.");
        }
    }

    private void validateUsedVehicleData() {
        if (mileageField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a quilometragem do veículo usado.");
        }

        if (ownersField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a quantidade de proprietários.");
        }

        try {
            double mileage = Double.parseDouble(mileageField.getText().trim().replace(",", "."));
            int owners = Integer.parseInt(ownersField.getText().trim());

            if (mileage < 0) {
                throw new IllegalArgumentException("A quilometragem não pode ser negativa.");
            }

            if (owners < 1) {
                throw new IllegalArgumentException("A quantidade de proprietários deve ser maior que zero.");
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "Quilometragem e proprietários devem conter apenas números válidos."
            );
        }
    }

    private double parseMoney(String text) {
        String value = text.trim()
                .replace("R$", "")
                .replace(" ", "")
                .replace(".", "")
                .replace(",", ".");

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Informe valores numéricos válidos.");
        }
    }

    private void clearForm() {
        brandCombo.setSelectedIndex(0);
        modelField.setText("");
        yearCombo.setSelectedIndex(0);
        valueField.setText("");

        newRadio.setSelected(false);
        usedRadio.setSelected(true);
        mileageField.setText("");
        ownersField.setText("");

        downPaymentCheck.setSelected(false);
        downPaymentField.setText("");
        installmentsCombo.setSelectedIndex(2); // 36

        resultPanel.setVisible(false);

        updateUsedPanel();
        updateDownPaymentPanel();

        revalidate();
        repaint();
    }
}
