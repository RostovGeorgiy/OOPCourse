package ru.academits.rostov.temperature.model;

public class KelvinScale implements TemperatureScale {
    @Override
    public double convertToKelvin(double temperature) {
        return temperature;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperature) {
        return kelvinTemperature;
    }

    @Override
    public String toString() {
        return "Kelvin";
    }
}