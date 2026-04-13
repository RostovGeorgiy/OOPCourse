package ru.academits.rostov.temperature.model;

public class CelsiusScale implements TemperatureScale {
    @Override
    public double convertToKelvin(double temperatureValue) {
        return temperatureValue + 273.15;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperatureValue) {
        return kelvinTemperatureValue - 273.15;
    }

    @Override
    public String toString() {
        return "Celsius";
    }
}