package com.suni.diabetesdiary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DiabetesDiary extends Activity implements OnClickListener {

    Button logsBut, sugarsHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	logsBut = (Button) findViewById(R.id.logsBut);
	sugarsHistory = (Button) findViewById(R.id.sqlGetResultButton);
	logsBut.setOnClickListener(this);
	sugarsHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
	switch (arg0.getId()) {
	case R.id.logsBut:
	    Intent i = new Intent("android.intent.action.SUGARRATES");
	    startActivity(i);
	    break;

	case R.id.sqlGetResultButton:
	    Intent iHistory = new Intent("android.intent.action.SUGARRATESHISTORY");
	    startActivity(iHistory);
	    break;
	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.diabetes_diary, menu);
	return true;
    }

}
