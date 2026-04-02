package ru.academits.rostov.temperatureconversion_view;

import ru.academits.rostov.temperatureconversion_presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DesktopView implements View {
    private Presenter presenter;
    private JLabel resultLabel;
    //default values at the program's startup
    private String inputScale = "Celsius";
    private String outputScale = "Fahrenheit";

    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setSize(500, 600);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new FlowLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComboBox<String> inputScalesComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
            inputScalesComboBox.addActionListener(a -> {
                inputScale = (String) inputScalesComboBox.getSelectedItem();
                System.out.println("Input: " + inputScale);
            });

            JComboBox<String> convertToScalesComboBox = new JComboBox<>(new String[]{"Fahrenheit", "Celsius", "Kelvin"});
            convertToScalesComboBox.addActionListener(a -> {
                outputScale = (String) convertToScalesComboBox.getSelectedItem();
                System.out.println("Convert: " + outputScale);
            });

            JTextField inputTemperatureTextField = new JTextField();
            inputTemperatureTextField.setPreferredSize(new Dimension(200, 30));

            JButton convertButton = new JButton("Convert temperature");

            convertButton.addActionListener(a -> {
                try {
                    double inputTemperature = Double.parseDouble(inputTemperatureTextField.getText());

                    String converterMethodName = "convert" + inputScale + "To" + outputScale;

                    try {
                        Method method = presenter.getClass().getMethod(converterMethodName, double.class);
                        method.invoke(presenter, inputTemperature);

                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        System.out.println(e.getMessage() + " Error calling method: " + converterMethodName);
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null,
                            "Input value must contain only digits, '-' sign, and period symbol, and must not be empty.",
                            "InputError",
                            JOptionPane.ERROR_MESSAGE);

                    inputTemperatureTextField.setText("");
                }
            });

            convertButton.setBounds(150, 200, 220, 50);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JLabel inputLabel = new JLabel("Select input scale.");
            JLabel outputLabel = new JLabel("Select output scale.");

            JLabel messageLabel = new JLabel("Input temperature value.");

            mainPanel.add(messageLabel);
            mainPanel.add(inputTemperatureTextField);

            JPanel scaleSelectionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            mainPanel.add(scaleSelectionPanel);

            mainPanel.add(convertButton);

            scaleSelectionPanel.add(inputLabel);
            scaleSelectionPanel.add(inputScalesComboBox);

            scaleSelectionPanel.add(outputLabel);
            scaleSelectionPanel.add(convertToScalesComboBox);

            resultLabel = new JLabel();

            mainPanel.add(resultLabel);

            frame.add(mainPanel);

            frame.setVisible(true);
        });
    }

    @Override
    public void setController(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showFahrenheitTemperature(double fahrenheitTemperature) {
        resultLabel.setText("Температура по шкале Фаренгейта: " + fahrenheitTemperature);
    }

    @Override
    public void showCelsiusTemperature(double celsiusTemperature) {
        resultLabel.setText("Температура по шкале Цельсия: " + celsiusTemperature);
    }

    @Override
    public void showKelvinTemperature(double kelvinTemperature) {
        resultLabel.setText("Температура по шкале Кельвина: " + kelvinTemperature);
    }
}