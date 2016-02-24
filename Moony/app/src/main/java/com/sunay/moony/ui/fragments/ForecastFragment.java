package com.sunay.moony.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.sunay.moony.R;
import com.sunay.moony.adapters.ForecastAdapter;
import com.sunay.moony.models.ForecastModel;
import com.sunay.moony.networking.APIClient;
import com.sunay.moony.networking.events.ForecastReceivedEvent;
import com.sunay.moony.ui.views.SimpleDividerDecoration;
import com.sunay.moony.util.LocationUtils;

import butterknife.Bind;
import retrofit.Call;

/**
 * Created by sunay on 16-2-23.
 */
public class ForecastFragment extends BaseFragment {
  @Bind(R.id.rv_forecast)
  RecyclerView rvForecastList;
  ForecastAdapter mAdapter;

  public static ForecastFragment getInstance(Bundle args) {
    ForecastFragment fragment = new ForecastFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private Call<ForecastModel> mCall;

  @Override
  public String getBackstackTag() {
    return null;
  }

  @Override
  protected void retryQuery() {

  }

  @Override
  protected void initUI() {

  }

  @Override
  public void onResume() {
    super.onResume();
    showProgress();
    mCall = APIClient.getInstance(getActivity())
        .getForecastByCity(LocationUtils.getInstance(getContext())
            .getCity());
//    mCall = APIClient.getInstance(getActivity())
//        .getForecastByCity("Sofia");
  }

  @Subscribe
  public void onForecastReceivedEvent(ForecastReceivedEvent event) {
    hideProgress();
    if (event.getForecastData() != null && event.getForecastData().getList().size() > 0)
      populateFields(event.getForecastData());
  }


  private void populateFields(ForecastModel data) {
    rvForecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvForecastList.addItemDecoration(new SimpleDividerDecoration(getActivity()));
    mAdapter = new ForecastAdapter(getActivity(), data.getList());
    rvForecastList.setAdapter(mAdapter);
    mAdapter.notifyDataSetChanged();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_forecast;
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
