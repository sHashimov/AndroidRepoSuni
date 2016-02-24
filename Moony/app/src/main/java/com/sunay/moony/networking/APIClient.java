package com.sunay.moony.networking;

import android.content.Context;

import com.squareup.otto.Produce;
import com.sunay.moony.models.ForecastModel;
import com.sunay.moony.models.WeatherModel;
import com.sunay.moony.networking.events.ErrorEvent;
import com.sunay.moony.networking.events.ForecastReceivedEvent;
import com.sunay.moony.networking.events.WeatherReceivedEvent;
import com.sunay.moony.util.BusProvider;
import com.sunay.moony.util.Constants;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by krasimir.karamazov on 12/28/2015.
 */
public class APIClient {
    private static APIClient sInstance;
    private WeatherModel weatherData;
    private ForecastModel forecastData;

    private APIClient(Context mContext) {
        //BusProvider.getInstance().register(this);
    }

    public static synchronized APIClient getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new APIClient(context);
        }
        return sInstance;
    }


    public Call<WeatherModel> getWeatherByCity(String city) {
        Call<WeatherModel> call = ServiceGenerator.createAPIService()
            .getWeatherByCity(city, Constants.API_KEY);

        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Response<WeatherModel> response, Retrofit retrofit) {
                weatherData = response.body();
                BusProvider.getInstance()
                    .post(getWeatherReceivedEvent());
            }

            @Override
            public void onFailure(Throwable t) {
                BusProvider.getInstance()
                    .post(getErrorEvent());
            }
        });
        return call;
    }

    public Call<ForecastModel> getForecastByCity(String city) {
        Call<ForecastModel> call = ServiceGenerator.createAPIService()
            .getForecastByCity(city, Constants.API_KEY);

        call.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Response<ForecastModel> response, Retrofit retrofit) {
                forecastData = response.body();
                BusProvider.getInstance()
                    .post(getForecastReceivedEvent());
            }

            @Override
            public void onFailure(Throwable t) {
                BusProvider.getInstance()
                    .post(getErrorEvent());
            }
        });
        return call;
    }

    @Produce
    public WeatherReceivedEvent getWeatherReceivedEvent() {
        return new WeatherReceivedEvent(weatherData);
    }

    @Produce
    public ForecastReceivedEvent getForecastReceivedEvent() {
        return new ForecastReceivedEvent(forecastData);
    }

    @Produce
    public ErrorEvent getErrorEvent() {
        return new ErrorEvent();
    }
}
