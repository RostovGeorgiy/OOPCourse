package ru.academits.rostov.temperatureconversion.model;

public class CelsiusConverter implements TemperatureScales {
    @Override
    public double convertToBaseScale(double celsiusTemperature) {
        return celsiusTemperature + 273.15;
    }

    @Override
    public double convertFromBaseScale(double kelvinTemperature) {
        return kelvinTemperature - 273.15;
    }

    @Override
    public String toString() {
        return "Celsius";
    }
}