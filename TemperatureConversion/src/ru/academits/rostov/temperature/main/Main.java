package ru.academits.rostov.temperature.main;

import ru.academits.rostov.temperature.model.*;
import ru.academits.rostov.temperature.presenter.Presenter;
import ru.academits.rostov.temperature.view.DesktopView;
import ru.academits.rostov.temperature.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter converter = new TemperatureConverter(new ArrayList<>(Arrays.asList(new CelsiusScale(), new FahrenheitScale(), new KelvinScale())));

        View view = new DesktopView();

        Presenter presenter = new Presenter(converter, view);
        presenter.start();
    }
}