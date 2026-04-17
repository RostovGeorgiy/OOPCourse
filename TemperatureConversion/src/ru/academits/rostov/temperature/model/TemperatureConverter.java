package ru.academits.rostov.temperature.model;

import java.util.ArrayList;
import java.util.Arrays;

public class TemperatureConverter {
    private ArrayList<TemperatureScale> scales = new ArrayList<>(Arrays.asList(new CelsiusScale(), new FahrenheitScale(), new KelvinScale()));

    public ArrayList<TemperatureScale> getScales() {
        return scales;
    }

    public void setScales(ArrayList<TemperatureScale> scales) {
        this.scales = scales;
    }

    public double convert(double inputTemperature, TemperatureScale inputScale, TemperatureScale outputScale) {
        return outputScale.convertFromKelvin(inputScale.convertToKelvin(inputTemperature));
    }
}