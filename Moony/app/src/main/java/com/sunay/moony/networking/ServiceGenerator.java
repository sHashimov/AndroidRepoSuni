package com.sunay.moony.networking;


import com.sunay.moony.util.Constants;
import com.sunay.moony.util.Logger;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by krasimir.karamazov on 12/28/2015.
 */
public class ServiceGenerator {
    private static Retrofit retrofit;

    public static AirVuzAPIInterface createAPIService() {
        if(retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient();
            if(Logger.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.interceptors().add(interceptor);
            }
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(GsonConverterFactory.create(AirVuzGsonBuilder.gson()));
            retrofit = builder.client(httpClient).build();
        }

        return retrofit.create(AirVuzAPIInterface.class);
    }


}
