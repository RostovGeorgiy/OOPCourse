package ru.academits.rostov.temperatureconversion_model;

public class TemperatureConverter implements Converter {
    private double celsiusTemperature;
    private double fahrenheitTemperature;
    private double kelvinTemperature;

    public void convertCelsiusToFahrenheit(double celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
        fahrenheitTemperature = celsiusTemperature * 9 / 5 + 32;
    }

    @Override
    public void convertCelsiusToKelvin(double celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
        kelvinTemperature = celsiusTemperature + 273;
    }

    @Override
    public void convertFahrenheitToCelsius(double fahrenheitTemperature) {
        this.fahrenheitTemperature = fahrenheitTemperature;
        celsiusTemperature = (fahrenheitTemperature - 32) * 5 / 9;
    }

    @Override
    public void convertFahrenheitToKelvin(double fahrenheitTemperature) {
        this.fahrenheitTemperature = fahrenheitTemperature;
        kelvinTemperature = (fahrenheitTemperature - 32) * 5 / 9 + 273;
    }

    @Override
    public void convertKelvinToFahrenheit(double kelvinTemperature) {
        this.kelvinTemperature = kelvinTemperature;
        fahrenheitTemperature = (kelvinTemperature - 273) * 9 / 5 + 32;
    }

    @Override
    public void convertKelvinToCelsius(double kelvinTemperature) {
        this.kelvinTemperature = kelvinTemperature;
        celsiusTemperature = kelvinTemperature - 273;
    }

    @Override
    public void convertCelsiusToCelsius(double celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
    }

    @Override
    public void convertFahrenheitToFahrenheit(double fahrenheitTemperature) {
        this.fahrenheitTemperature = fahrenheitTemperature;
    }

    @Override
    public void convertKelvinToKelvin(double kelvinTemperature) {
        this.kelvinTemperature = kelvinTemperature;
    }

    @Override
    public double getCelsiusTemperature() {
        return celsiusTemperature;
    }

    @Override
    public double getFahrenheitTemperature() {
        return fahrenheitTemperature;
    }

    @Override
    public double getKelvinTemperature() {
        return kelvinTemperature;
    }
}