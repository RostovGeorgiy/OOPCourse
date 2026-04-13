package ru.academits.rostov.temperature.model;

public class KelvinScale implements TemperatureScale {
    @Override
    public double convertToKelvin(double temperatureValue) {
        return temperatureValue;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperatureValue) {
        return kelvinTemperatureValue;
    }

    @Override
    public String toString() {
        return "Kelvin";
    }
}