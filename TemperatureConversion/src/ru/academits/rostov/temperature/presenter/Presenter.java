package ru.academits.rostov.temperature.presenter;

import ru.academits.rostov.temperature.model.TemperatureConverter;
import ru.academits.rostov.temperature.model.TemperatureScale;
import ru.academits.rostov.temperature.view.View;

import java.util.Objects;

public class Presenter {
    private final TemperatureConverter converter;
    private final View view;

    public Presenter(TemperatureConverter converter, View view) {
        this.converter = Objects.requireNonNull(converter, "Converter must not be null.");
        this.view = Objects.requireNonNull(view, "View must not be null.");

        view.setPresenter(this);
    }

    public void start() {
        view.start();
    }

    public void convert() {
        TemperatureScale outputScale = view.getOutputScale();

        view.showConvertedTemperature(converter.convert(view.getInputTemperature(), view.getInputScale(), outputScale), outputScale);
    }
}