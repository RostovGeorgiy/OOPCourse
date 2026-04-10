package ru.academits.rostov.temperatureconversion.presenter;

import ru.academits.rostov.temperatureconversion.model.TemperatureConverter;
import ru.academits.rostov.temperatureconversion.model.TemperatureScales;
import ru.academits.rostov.temperatureconversion.view.View;

public class Presenter {
    private final TemperatureConverter converter;
    private final View view;

    public Presenter(TemperatureConverter converter, View view) {
        this.converter = converter;
        this.view = view;

        view.setPresenter(this);
    }

    public void start() {
        view.start();
    }

    public void convert() {
        TemperatureScales outputScale = view.getOutputScale();

        converter.convert(view.getInput(), view.getInputScale(), outputScale);

        view.showConvertedTemperature(String.format("%.2f", converter.getOutputTemperature()), outputScale.toString());
    }
}