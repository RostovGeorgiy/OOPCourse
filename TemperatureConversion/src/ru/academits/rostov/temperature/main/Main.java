package ru.academits.rostov.temperature.main;

import ru.academits.rostov.temperature.model.*;
import ru.academits.rostov.temperature.presenter.Presenter;
import ru.academits.rostov.temperature.view.DesktopView;
import ru.academits.rostov.temperature.view.View;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter converter = new TemperatureConverter();

        View view = new DesktopView();

        Presenter presenter = new Presenter(converter, view);
        presenter.start();
    }
}