package ru.academits.rostov.temperature.model;

public class FahrenheitScale implements TemperatureScale {
    @Override
    public double convertToKelvin(double temperature) {
        return (temperature - 32) * 5 / 9 + 273.15;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperature) {
        return (kelvinTemperature - 273.15) * 9 / 5 + 32;
    }

    @Override
    public String toString() {
        return "Fahrenheit";
    }
}