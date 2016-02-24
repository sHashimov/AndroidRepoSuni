package com.sunay.moony.networking.events;


import com.sunay.moony.models.ForecastModel;

/**
 * Created by krasimir.karamazov on 12/28/2015.
 */
public class ForecastReceivedEvent {
    private ForecastModel forecast;

    public ForecastReceivedEvent(ForecastModel forecast) {
        this.forecast = forecast;
    }

    public ForecastModel getForecastData() {
        return forecast;
    }
}
