package ru.academits.rostov.temperature.view;

import ru.academits.rostov.temperature.model.TemperatureScale;
import ru.academits.rostov.temperature.presenter.Presenter;

public interface View {
    void start();

    void setPresenter(Presenter presenter);

    double getInputTemperatureValue();

    TemperatureScale getInputScale();

    TemperatureScale getOutputScale();

    void showConvertedTemperature(double convertedValueString, String outputScale);
}