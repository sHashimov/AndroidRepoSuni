package com.sunay.fsqapp;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import com.sunay.fsqapp.FoursquareApp.FsqAuthListener;

public class Main extends Activity {
    private FoursquareApp mFsqApp;
    private ListView mListView;
    private NearbyAdapter mAdapter;
    private ArrayList<FsqVenue> mNearbyList;
    private ProgressDialog mProgress;

    public static final String CLIENT_ID = "JQFPSHHQQY1UWVNZLH3R04ORWCR5X31EU5FYSPXVV50BKXSD";
    public static final String CLIENT_SECRET = "HKEBLKQTILUSPMVCTIVP31FDJQPF5EDKCUMULFFRQAADTZRU";

    GPSTracker tracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	final TextView nameTv = (TextView) findViewById(R.id.tv_name);
	Button connectBtn = (Button) findViewById(R.id.b_connect);
	Button goBtn = (Button) findViewById(R.id.b_go);
	mListView = (ListView) findViewById(R.id.lv_places);

	mFsqApp = new FoursquareApp(this, CLIENT_ID, CLIENT_SECRET);

	mAdapter = new NearbyAdapter(this);
	mNearbyList = new ArrayList<FsqVenue>();
	mProgress = new ProgressDialog(this);

	mProgress.setMessage("Loading data ...");

	if (mFsqApp.hasAccessToken())
	    nameTv.setText("Connected as " + mFsqApp.getUserName());

	FsqAuthListener listener = new FsqAuthListener() {
	    @Override
	    public void onSuccess() {
		Toast.makeText(Main.this, "Connected as " + mFsqApp.getUserName(), Toast.LENGTH_SHORT).show();
		nameTv.setText("Connected as " + mFsqApp.getUserName());
	    }

	    @Override
	    public void onFail(String error) {
		Toast.makeText(Main.this, error, Toast.LENGTH_SHORT).show();
	    }
	};

	mFsqApp.setListener(listener);

	// get access token and user name from foursquare
	connectBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		mFsqApp.authorize();
	    }
	});

	// use access token to get nearby places
	goBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		tracker = new GPSTracker(Main.this);
		if (tracker.canGetLocation()) {

//		    double latitude = tracker.getLatitude();
//		    double longitude = tracker.getLongitude();
		    loadNearbyPlaces(42.692792, 23.352711);
		} else {
		    // can't get location
		    // GPS or Network is not enabled
		    // Ask user to enable GPS/network in settings
		    tracker.showSettingsAlert();
		}

	    }
	});
    }

    private void loadNearbyPlaces(final double latitude, final double longitude) {
	mProgress.show();

	new Thread() {
	    @Override
	    public void run() {
		int what = 0;

		try {

		    mNearbyList = mFsqApp.getNearby(latitude, longitude);
		} catch (Exception e) {
		    what = 1;
		    e.printStackTrace();
		}

		mHandler.sendMessage(mHandler.obtainMessage(what));
	    }
	}.start();
    }

    private Handler mHandler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
	    mProgress.dismiss();

	    if (msg.what == 0) {
		if (mNearbyList.size() == 0) {
		    Toast.makeText(Main.this, "No nearby places available", Toast.LENGTH_SHORT).show();
		    return;
		}

		mAdapter.setData(mNearbyList);
		mListView.setAdapter(mAdapter);
	    } else {
		Toast.makeText(Main.this, "Failed to load nearby places", Toast.LENGTH_SHORT).show();
	    }
	}
    };
}