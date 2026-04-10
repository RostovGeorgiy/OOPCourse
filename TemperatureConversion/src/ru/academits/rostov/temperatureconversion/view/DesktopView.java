package ru.academits.rostov.temperatureconversion.view;

import ru.academits.rostov.temperatureconversion.model.TemperatureScales;
import ru.academits.rostov.temperatureconversion.presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class DesktopView implements View {
    private final TemperatureScales[] scales;

    public DesktopView(TemperatureScales[] scales) {
        this.scales = scales;
    }

    private Presenter presenter;
    private JLabel resultLabel;

    double inputTemperature;
    private TemperatureScales inputScale;
    private TemperatureScales outputScale;

    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Temperature converter");
            frame.setSize(350, 220);
            frame.setMinimumSize(new Dimension(350, 220));
            frame.setResizable(false);

            frame.setLayout(new FlowLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComboBox<TemperatureScales> inputScalesComboBox = new JComboBox<>(scales);

            JComboBox<TemperatureScales> convertToScalesComboBox = new JComboBox<>(scales);

            JTextField inputTemperatureTextField = new JTextField(10);
            inputTemperatureTextField.setMaximumSize(new Dimension(100, 30));
            inputTemperatureTextField.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton convertButton = new JButton("Convert temperature");

            convertButton.addActionListener(a -> {
                inputTemperature = Double.parseDouble(inputTemperatureTextField.getText());
                inputScale = (TemperatureScales) inputScalesComboBox.getSelectedItem();
                outputScale = (TemperatureScales) convertToScalesComboBox.getSelectedItem();
                presenter.convert();
            });

            convertButton.setBounds(150, 200, 220, 50);
            convertButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setMaximumSize(new Dimension(330, 200));

            JLabel inputLabel = new JLabel("Select input scale:");
            JLabel outputLabel = new JLabel("Select output scale:");

            JLabel messageLabel = new JLabel("Input temperature value:");
            messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            mainPanel.add(messageLabel);
            mainPanel.add(inputTemperatureTextField);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

            JPanel scaleSelectionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            scaleSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            mainPanel.add(scaleSelectionPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            mainPanel.add(convertButton);

            scaleSelectionPanel.add(inputLabel);
            scaleSelectionPanel.add(inputScalesComboBox);

            scaleSelectionPanel.add(outputLabel);
            scaleSelectionPanel.add(convertToScalesComboBox);

            resultLabel = new JLabel();
            resultLabel.setMaximumSize(new Dimension(220, 20));
            resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            mainPanel.add(resultLabel);

            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showConvertedTemperature(String convertedTemperature, String outputScale) {
        resultLabel.setText("Temperature in " + outputScale + ": " + convertedTemperature);
    }

    @Override
    public double getInput() {
        return inputTemperature;
    }

    @Override
    public TemperatureScales getInputScale() {
        return inputScale;
    }

    @Override
    public TemperatureScales getOutputScale() {
        return outputScale;
    }
}