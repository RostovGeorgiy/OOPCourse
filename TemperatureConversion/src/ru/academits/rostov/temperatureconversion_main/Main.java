package ru.academits.rostov.temperatureconversion_main;

import ru.academits.rostov.temperatureconversion_model.Converter;
import ru.academits.rostov.temperatureconversion_model.TemperatureConverter;
import ru.academits.rostov.temperatureconversion_presenter.Presenter;
import ru.academits.rostov.temperatureconversion_view.DesktopView;
import ru.academits.rostov.temperatureconversion_view.View;

public class Main {
    public static void main(String[] args) {
        Converter converter = new TemperatureConverter();
        View view = new DesktopView();
        Presenter presenter = new Presenter(converter, view);
        presenter.start();
    }
}