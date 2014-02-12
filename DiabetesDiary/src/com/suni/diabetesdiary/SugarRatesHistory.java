package com.suni.diabetesdiary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SugarRatesHistory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.sugarrateshistory);
	TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
	SqlItems info = new SqlItems(this);
	info.open();
	String data = info.getData();
	info.close();
	tv.setText(data);
	
    }

}
