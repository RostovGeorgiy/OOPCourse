package ru.academits.rostov.temperatureconversion_presenter;

import ru.academits.rostov.temperatureconversion_model.Converter;
import ru.academits.rostov.temperatureconversion_view.View;

public class Presenter {
    public final Converter converter;
    public final View view;

    public Presenter(Converter converter, View view) {
        this.converter = converter;
        this.view = view;

        view.setController(this);
    }

    public void start() {
        view.start();
    }

    public void convertCelsiusToFahrenheit(double celsiusTemperature) {
        converter.convertCelsiusToFahrenheit(celsiusTemperature);

        view.showFahrenheitTemperature(converter.getFahrenheitTemperature());
    }

    public void convertCelsiusToKelvin(double celsiusTemperature) {
        converter.convertCelsiusToKelvin(celsiusTemperature);

        view.showKelvinTemperature(converter.getKelvinTemperature());
    }

    public void convertFahrenheitToCelsius(double fahrenheitTemperature) {
        converter.convertFahrenheitToCelsius(fahrenheitTemperature);

        view.showCelsiusTemperature(converter.getCelsiusTemperature());
    }

    public void convertFahrenheitToKelvin(double fahrenheitTemperature) {
        converter.convertFahrenheitToKelvin(fahrenheitTemperature);

        view.showKelvinTemperature(converter.getKelvinTemperature());
    }

    public void convertKelvinToCelsius(double kelvinTemperature) {
        converter.convertKelvinToCelsius(kelvinTemperature);

        view.showCelsiusTemperature(converter.getCelsiusTemperature());
    }

    public void convertKelvinToFahrenheit(double kelvinTemperature) {
        converter.convertKelvinToFahrenheit(kelvinTemperature);

        view.showFahrenheitTemperature(converter.getFahrenheitTemperature());
    }

    public void convertCelsiusToCelsius(double celsiusTemperature) {
        converter.convertCelsiusToCelsius(celsiusTemperature);

        view.showCelsiusTemperature(converter.getCelsiusTemperature());
    }

    public void convertFahrenheitToFahrenheit(double fahrenheitTemperature) {
        converter.convertFahrenheitToFahrenheit(fahrenheitTemperature);

        view.showFahrenheitTemperature(converter.getFahrenheitTemperature());
    }

    public void convertKelvinToKelvin(double kelvinTemperature) {
        converter.convertKelvinToKelvin(kelvinTemperature);

        view.showKelvinTemperature(converter.getKelvinTemperature());
    }
}