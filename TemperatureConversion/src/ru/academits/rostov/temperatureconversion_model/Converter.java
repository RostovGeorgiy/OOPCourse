package ru.academits.rostov.temperatureconversion_model;

public interface Converter {
    void convertCelsiusToFahrenheit(double celsiusTemperature);

    void convertCelsiusToKelvin(double celsiusTemperature);

    void convertFahrenheitToCelsius(double fahrenheitTemperature);

    void convertFahrenheitToKelvin(double fahrenheitTemperature);

    void convertKelvinToFahrenheit(double kelvinTemperature);

    void convertKelvinToCelsius(double kelvinTemperature);

    void convertCelsiusToCelsius(double celsiusTemperature);

    void convertFahrenheitToFahrenheit(double fahrenheitTemperature);

    void convertKelvinToKelvin(double kelvinTemperature);

    double getCelsiusTemperature();

    double getFahrenheitTemperature();

    double getKelvinTemperature();
}