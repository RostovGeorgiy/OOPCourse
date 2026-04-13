package ru.academits.rostov.temperature.model;

public interface TemperatureScale {
    double convertToKelvin(double temperatureValue);

    double convertFromKelvin(double kelvinTemperatureValue);
}