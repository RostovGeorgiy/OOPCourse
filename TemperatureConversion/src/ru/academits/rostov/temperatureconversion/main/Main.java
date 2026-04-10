package ru.academits.rostov.temperatureconversion.main;

import ru.academits.rostov.temperatureconversion.model.*;
import ru.academits.rostov.temperatureconversion.presenter.Presenter;
import ru.academits.rostov.temperatureconversion.view.DesktopView;
import ru.academits.rostov.temperatureconversion.view.View;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter converter = new TemperatureConverter();
        TemperatureScales[] scales = {new CelsiusConverter(), new FahrenheitConverter(), new KelvinConverter()};
        View view = new DesktopView(scales);
        Presenter presenter = new Presenter(converter, view);
        presenter.start();
    }
}