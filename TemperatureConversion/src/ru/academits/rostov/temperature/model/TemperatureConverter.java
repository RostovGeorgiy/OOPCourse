package ru.academits.rostov.temperature.model;

public class TemperatureConverter {
    public double convert(double inputTemperature, TemperatureScale inputScale, TemperatureScale outputScale) {
        return outputScale.convertFromKelvin(inputScale.convertToKelvin(inputTemperature));
    }
}