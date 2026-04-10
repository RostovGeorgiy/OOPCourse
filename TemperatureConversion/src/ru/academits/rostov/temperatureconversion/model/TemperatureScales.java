package ru.academits.rostov.temperatureconversion.model;

public interface TemperatureScales {
    double convertToBaseScale(double value);

    double convertFromBaseScale(double value);

    String toString();
}