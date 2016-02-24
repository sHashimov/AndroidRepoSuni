package com.sunay.moony.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sunay.moony.R;
import com.sunay.moony.ui.views.RobotoTextView;
import com.sunay.moony.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunay on 16-2-23.
 */
public class ForecastAdapter extends HeaderFooterAdapter {
  private List<com.sunay.moony.models.List> mData;

  public ForecastAdapter(Context context, List<com.sunay.moony.models.List> data) {
    super(context);
    mData = data;
  }

  @Override
  protected int getHeaderItemCount() {
    return 0;
  }

  @Override
  protected int getFooterItemCount() {
    return 0;
  }

  @Override
  protected int getContentItemCount() {
    return mData.size();
  }

  @Override
  protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder(ViewGroup parent,
      int headerViewType) {
    return null;
  }

  @Override
  protected RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent,
      int footerViewType) {
    return null;
  }

  @Override
  protected RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent,
      int contentViewType) {
    View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_forecast,
        parent, false);

    return new ContentItemViewHolder(contentView);
  }

  @Override
  protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder,
      int position) {

  }

  @Override
  protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder,
      int position) {

  }

  @Override
  protected void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder,
      int position) {
    com.sunay.moony.models.List item = mData.get(position);
    final ContentItemViewHolder contentItem = ((ContentItemViewHolder) contentViewHolder);
    contentItem.tvTemperature.setText(Utils.kelvinToCelsius(item.getTemp().getDay()));
    contentItem.tvCondition.setText((item.getWeather().get(0).getDescription()));
    String dayOfWeek = (Utils.dayOfWeek(Long.valueOf(item.getDt())).toString().charAt(0))
        + (Utils.dayOfWeek(Long.valueOf(item.getDt())).toString()
            .substring(1, (Utils.dayOfWeek(Long.valueOf(item.getDt())).toString().length()))
            .toLowerCase());
    contentItem.tvDayOfWeek.setText(dayOfWeek);
    if (item.getWeather().get(0).getMain().equals("Clear")) {
      contentItem.rlBackground.setBackgroundResource(R.drawable.sunny_back);
    } else if (item.getWeather().get(0).getMain().equals("Rain")) {
      contentItem.rlBackground.setBackgroundResource(R.drawable.rainy);
    } else if (item.getWeather().get(0).getMain().equals("Snow")) {
      contentItem.rlBackground.setBackgroundResource(R.drawable.snowy);
    } else if (item.getWeather().get(0).getMain().equals("Clouds")) {
      contentItem.rlBackground.setBackgroundResource(R.drawable.cloudy);
    } else {
      contentItem.rlBackground.setBackgroundResource(R.drawable.sunny_back);
    }
  }


  class ContentItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_temperature)
    RobotoTextView tvTemperature;

    @Bind(R.id.tv_condition)
    RobotoTextView tvCondition;

    @Bind(R.id.tv_day)
    RobotoTextView tvDayOfWeek;

    @Bind(R.id.rl_container)
    RelativeLayout rlBackground;

    public ContentItemViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }
}
