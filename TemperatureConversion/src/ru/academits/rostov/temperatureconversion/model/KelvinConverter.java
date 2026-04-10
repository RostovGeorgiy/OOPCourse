package ru.academits.rostov.temperatureconversion.model;

public class KelvinConverter implements TemperatureScales {
    @Override
    public double convertToBaseScale(double kelvinTemperature) {
        return kelvinTemperature;
    }

    @Override
    public double convertFromBaseScale(double kelvinTemperature) {
        return kelvinTemperature;
    }

    @Override
    public String toString() {
        return "Kelvin";
    }
}