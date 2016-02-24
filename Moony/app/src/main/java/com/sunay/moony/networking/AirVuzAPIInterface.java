package com.sunay.moony.networking;


import com.sunay.moony.models.ForecastModel;
import com.sunay.moony.models.WeatherModel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by krasimir.karamazov on 12/28/2015.
 */
public interface AirVuzAPIInterface {

    @GET("/data/2.5/weather")
    Call<WeatherModel> getWeatherByCity(@Query("q") String city, @Query("appid") String appId);

    @GET("/data/2.5/forecast/daily")
    Call<ForecastModel> getForecastByCity(@Query("q") String city, @Query("appid") String appId);


}
