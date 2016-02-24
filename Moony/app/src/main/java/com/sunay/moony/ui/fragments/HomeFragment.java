package com.sunay.moony.ui.fragments;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.sunay.moony.R;
import com.sunay.moony.models.WeatherModel;
import com.sunay.moony.networking.APIClient;
import com.sunay.moony.networking.events.WeatherReceivedEvent;
import com.sunay.moony.ui.views.RobotoTextView;
import com.sunay.moony.util.CircularSeekBar;
import com.sunay.moony.util.LocationUtils;
import com.sunay.moony.util.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import retrofit.Call;

/**
 * Created by sunay on 16-2-17.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.tv_city_name)
    RobotoTextView tvCityName;

    @Bind(R.id.tv_temperature)
    RobotoTextView tvTemperature;

    @Bind(R.id.tv_sunset)
    RobotoTextView tvSunsetTime;

    @Bind(R.id.tv_sunrise)
    RobotoTextView tvSunriseTime;

    @Bind(R.id.circularSeekBar1)
    CircularSeekBar circularSeekBar;

    @Bind(R.id.tv_humidity)
    RobotoTextView tvHumidity;

    @Bind(R.id.tv_condition)
    RobotoTextView tvCondition;

    @Bind(R.id.tv_day_or_night)
    RobotoTextView tvDayOrNight;

    @Bind(R.id.iv_left)
    ImageView ivLeft;

    @Bind(R.id.iv_right)
    ImageView ivRight;

    private Call<WeatherModel> mCall;

    public static HomeFragment getInstance(Bundle args) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getBackstackTag() {
        return null;
    }

    @Override
    protected void retryQuery() {

    }

    @Override
    protected void initUI() {
        tvCityName.setText("-");
        tvTemperature.setText("- -°");
        ivLeft.setImageResource(R.drawable.mini_sun);
        ivRight.setImageResource(R.drawable.mini_moon);
    }


    @Override
    public void onResume() {
        super.onResume();
        mCall = APIClient.getInstance(getActivity())
            .getWeatherByCity(LocationUtils.getInstance(getContext())
                .getCity());
    }


    @Subscribe
    public void onWeatherReceivedEvent(WeatherReceivedEvent event) {
        hideProgress();
        populateFields(event.getWeatherData());
    }

    private void populateFields(WeatherModel data) {
        if (data != null) {
            tvCityName.setText(data.getName());
            tvTemperature.setText(new StringBuilder().append(Utils.kelvinToCelsius(data.getMain()
                .getTemp())));
            tvHumidity.setText(new StringBuilder().append("Humidity: ")
                .append(data.getMain()
                    .getHumidity())
                .append("%")
                .toString());
            tvCondition.setText(data.getWeather()
                .get(0)
                .getDescription());
            populateCircularProgressBar(data);
        }
    }

    private String getDate(long timeStamp) {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    private void populateCircularProgressBar(WeatherModel data) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long begin = data.getSys()
            .getSunrise();
        long end = data.getSys()
            .getSunset();
        circularSeekBar.setIsTouchEnabled(false);
        circularSeekBar.setPointerHaloColor(R.color.yellow);
        tvSunsetTime.setText(getDate(data.getSys()
            .getSunset() * 1000L));
        tvSunriseTime.setText(getDate(data.getSys()
            .getSunrise() * 1000L));
        //////////////////////////////////////
        int endFinal = (int) (end - begin);
        int currentProgress = (int) (endFinal - (end - currentTimestamp));
        circularSeekBar.setMax(endFinal);
        if (currentProgress >= endFinal) {
            circularSeekBar.setProgress(endFinal);
        } else {
            circularSeekBar.setProgress(currentProgress);
        }

        if (currentTimestamp < begin) {
            circularSeekBar.setProgress(0);
            tvDayOrNight.setText("night");
        } else {
            if (circularSeekBar.getMax() >= currentProgress) {
                //day
                tvDayOrNight.setText("day");
            } else {
                //night
                tvDayOrNight.setText("night");
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void customizeActionBar() {
    }

    @Override
    public String getScreenNameForAnalytics() {
        return null;
    }

    @Override
    protected boolean handleMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    protected boolean hasFragmentOptionsMenu() {
        return false;
    }
}
