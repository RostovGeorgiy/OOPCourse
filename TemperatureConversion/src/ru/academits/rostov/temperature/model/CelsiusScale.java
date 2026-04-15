package ru.academits.rostov.temperature.model;

public class CelsiusScale implements TemperatureScale {
    @Override
    public double convertToKelvin(double temperature) {
        return temperature + 273.15;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperature) {
        return kelvinTemperature - 273.15;
    }

    @Override
    public String toString() {
        return "Celsius";
    }
}