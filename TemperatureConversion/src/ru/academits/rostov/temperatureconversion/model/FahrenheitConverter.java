package ru.academits.rostov.temperatureconversion.model;

public class FahrenheitConverter implements TemperatureScales {
    @Override
    public double convertToBaseScale(double fahrenheitTemperature) {
        return (fahrenheitTemperature - 32) * 5 / 9 + 273.15;
    }

    @Override
    public double convertFromBaseScale(double kelvinTemperature) {
        return (kelvinTemperature - 273.15) * 9 / 5 + 32;
    }

    @Override
    public String toString() {
        return "Fahrenheit";
    }
}