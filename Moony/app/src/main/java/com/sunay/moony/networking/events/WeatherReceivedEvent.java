package com.sunay.moony.networking.events;


import com.sunay.moony.models.WeatherModel;

/**
 * Created by krasimir.karamazov on 12/28/2015.
 */
public class WeatherReceivedEvent {
    private WeatherModel weather;

    public WeatherReceivedEvent(WeatherModel weather) {
        this.weather = weather;
    }

    public WeatherModel getWeatherData() {
        return weather;
    }
}
