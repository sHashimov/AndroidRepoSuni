package com.sunay.moony.navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sunay.moony.adapters.HeaderFooterAdapter;
import com.sunay.moony.navigation.models.SlidingMenuItemModel;

import java.util.List;

/**
 * Created by krasimir.karamazov on 7/8/2015.
 */
public class NavigationAdapter extends HeaderFooterAdapter {

    private NavigationItemClickListener mListener;
    private SharedPreferences mPrefs;
    private List<SlidingMenuItemModel> mData;
    public static final int HEADER_ITEM_TYPE = 12;
    public static final int CONTENT_ITEM_TYPE = 13;


    public NavigationAdapter(List<SlidingMenuItemModel> data, NavigationItemClickListener listener,
        SharedPreferences prefs, Context context) {
        super(context);
        mData = data;
        mListener = listener;
        mPrefs = prefs;
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
        return 0;
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
        return null;
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

    }

    @Override
    protected int getHeaderItemViewType(int position) {
        return HEADER_ITEM_TYPE;
    }

    @Override
    protected int getContentItemViewType(int position) {
        return CONTENT_ITEM_TYPE;
    }

}
