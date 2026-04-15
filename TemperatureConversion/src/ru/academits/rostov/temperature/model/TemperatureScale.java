package ru.academits.rostov.temperature.model;

public interface TemperatureScale {
    double convertToKelvin(double temperature);

    double convertFromKelvin(double kelvinTemperature);
}