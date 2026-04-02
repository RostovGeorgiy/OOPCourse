package ru.academits.rostov.temperatureconversion_view;

import ru.academits.rostov.temperatureconversion_presenter.Presenter;

public interface View {
    void start();

    void setController(Presenter presenter);

    void showFahrenheitTemperature(double fahrenheitTemperature);

    void showCelsiusTemperature(double celsiusTemperature);

    void showKelvinTemperature(double kelvinTemperature);
}