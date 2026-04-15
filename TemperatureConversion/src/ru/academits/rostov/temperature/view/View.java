package ru.academits.rostov.temperature.view;

import ru.academits.rostov.temperature.model.TemperatureScale;
import ru.academits.rostov.temperature.presenter.Presenter;

public interface View {
    void start();

    void showIncorrectInputMessage();

    void setPresenter(Presenter presenter);

    double getInputTemperature();

    TemperatureScale getInputScale();

    TemperatureScale getOutputScale();

    void showConvertedTemperature(double convertedTemperature, TemperatureScale outputScale);
}