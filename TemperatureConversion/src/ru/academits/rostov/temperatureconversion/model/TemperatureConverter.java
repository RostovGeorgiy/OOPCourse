package ru.academits.rostov.temperatureconversion.model;

public class TemperatureConverter {
    private double outputTemperature;

    public void convert(double inputTemperature, TemperatureScales inputScale, TemperatureScales outputScale) {
        outputTemperature = outputScale.convertFromBaseScale(inputScale.convertToBaseScale(inputTemperature));
    }

    public double getOutputTemperature() {
        return outputTemperature;
    }
}