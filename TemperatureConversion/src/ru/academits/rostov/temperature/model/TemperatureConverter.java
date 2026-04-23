package ru.academits.rostov.temperature.model;

import java.util.List;
import java.util.Objects;

public class TemperatureConverter {
    private final List<TemperatureScale> scales;

    public TemperatureConverter(List<TemperatureScale> scales) {
        Objects.requireNonNull(scales, "Scales list is null.");

        if (scales.isEmpty()) {
            throw new IllegalArgumentException("Scales list is empty.");
        }

        this.scales = List.copyOf(scales);
    }

    public List<TemperatureScale> getScales() {
        return List.copyOf(scales);
    }

    public double convert(double inputTemperature, TemperatureScale inputScale, TemperatureScale outputScale) {
        return outputScale.convertFromKelvin(inputScale.convertToKelvin(inputTemperature));
    }
}