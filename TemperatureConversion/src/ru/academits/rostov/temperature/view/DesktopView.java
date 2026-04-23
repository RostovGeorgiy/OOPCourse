package ru.academits.rostov.temperature.view;

import ru.academits.rostov.temperature.model.TemperatureScale;
import ru.academits.rostov.temperature.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class DesktopView implements View {
    private Presenter presenter;

    private JLabel resultLabel;

    private double inputTemperature;

    private TemperatureScale inputScale;
    private TemperatureScale outputScale;

    private JFrame frame;

    private boolean isStarted;

    public void start() {
        if (isStarted) {
            throw new IllegalStateException("Start method was already called.");
        }

        isStarted = true;

        Objects.requireNonNull(presenter, "Presenter must not be null.");

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Temperature converter");
            frame.setSize(350, 220);
            frame.setMinimumSize(new Dimension(350, 220));
            frame.setResizable(false);

            frame.setLayout(new FlowLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            TemperatureScale[] scales = presenter.getScales().toArray(TemperatureScale[]::new);

            JComboBox<TemperatureScale> inputScalesComboBox = new JComboBox<>(scales);
            JComboBox<TemperatureScale> outputScalesComboBox = new JComboBox<>(scales);

            JTextField inputTemperatureTextField = new JTextField(10);
            inputTemperatureTextField.setMaximumSize(new Dimension(235, 30));
            inputTemperatureTextField.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton convertButton = new JButton("Convert temperature");

            convertButton.addActionListener(_ -> {
                try {
                    inputTemperature = Double.parseDouble(inputTemperatureTextField.getText());
                } catch (Exception e) {
                    showIncorrectInputMessage();
                    return;
                }

                inputScale = (TemperatureScale) inputScalesComboBox.getSelectedItem();
                outputScale = (TemperatureScale) outputScalesComboBox.getSelectedItem();
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
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(convertButton);

            scaleSelectionPanel.add(inputLabel);
            scaleSelectionPanel.add(inputScalesComboBox);

            scaleSelectionPanel.add(outputLabel);
            scaleSelectionPanel.add(outputScalesComboBox);

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
    public void showIncorrectInputMessage() {
        JOptionPane.showMessageDialog(frame, "Input must not be null and must be a valid number.",
                "Input value error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = Objects.requireNonNull(presenter, "Presenter must not be null.");
    }

    @Override
    public void showConvertedTemperature(double convertedTemperature, TemperatureScale outputScale) {
        resultLabel.setText("Temperature in " + outputScale + ": " + convertedTemperature);
    }

    @Override
    public double getInputTemperature() {
        return inputTemperature;
    }

    @Override
    public TemperatureScale getInputScale() {
        return inputScale;
    }

    @Override
    public TemperatureScale getOutputScale() {
        return outputScale;
    }
}