package com.sunay.moony.util;

import android.util.Log;

/**
 * Created by krasimir.karamazov on 12/17/2014.
 */
public class Logger {

    public static boolean DEBUG = true;
    public static final String LOG_TAG = Logger.class.getSimpleName();

    public static void d(String msg){
        if(DEBUG){
            Log.d(LOG_TAG, msg);
        }
    }

    public static void e(String msg){
        if(DEBUG){
            Log.e(LOG_TAG, msg);
        }
    }

    public static void w(String msg){
        if(DEBUG){
            Log.w(LOG_TAG, msg);
        }
    }

    public static void i(String msg){
        if(DEBUG){
            Log.i(LOG_TAG, msg);
        }
    }
}
