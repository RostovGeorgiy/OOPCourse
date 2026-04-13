package ru.academits.rostov.temperature.model;

public class FahrenheitScale implements TemperatureScale {
    @Override
    public double convertToKelvin(double temperatureValue) {
        return (temperatureValue - 32) * 5 / 9 + 273.15;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperatureValue) {
        return (kelvinTemperatureValue - 273.15) * 9 / 5 + 32;
    }

    @Override
    public String toString() {
        return "Fahrenheit";
    }
}