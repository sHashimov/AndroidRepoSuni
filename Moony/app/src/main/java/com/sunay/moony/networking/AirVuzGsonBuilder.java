package com.sunay.moony.networking;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by krasimir.karamazov on 12/28/2015.
 */
public class AirVuzGsonBuilder {
    private static Gson gson;
    public static Gson gson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    .serializeNulls()
                    .create();
        }
        return gson;
    }
}
