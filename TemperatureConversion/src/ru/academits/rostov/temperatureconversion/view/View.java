package ru.academits.rostov.temperatureconversion.view;

import ru.academits.rostov.temperatureconversion.model.TemperatureScales;
import ru.academits.rostov.temperatureconversion.presenter.Presenter;

public interface View {
    void start();

    void setPresenter(Presenter presenter);

    double getInput();

    TemperatureScales getInputScale();

    TemperatureScales getOutputScale();

    void showConvertedTemperature(String convertedValueString, String outputScale);
}