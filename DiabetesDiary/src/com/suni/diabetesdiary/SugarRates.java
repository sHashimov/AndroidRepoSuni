package com.suni.diabetesdiary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class SugarRates extends Activity implements OnClickListener {

    EditText sqlDate, sqlTime, sqlDateRow, sqlMeal, sqlGlucose;
    Button sqlSave, sqlModify, sqlGetInfo, sqlDelete;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.sugarrates);

	expListView = (ExpandableListView) findViewById(R.id.exLV);
	prepareListData();
	listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
	expListView.setAdapter(listAdapter);
	expListView.setOnChildClickListener(new OnChildClickListener() {

	    @Override
	    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		sqlMeal.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
		return false;
	    }
	});

	sqlMeal = (EditText) findViewById(R.id.etMealRes);
	sqlDate = (EditText) findViewById(R.id.etDate);
	sqlTime = (EditText) findViewById(R.id.etTime);
	sqlGlucose = (EditText) findViewById(R.id.etGlucose);
	sqlSave = (Button) findViewById(R.id.sqlButtonUpdate);
	sqlSave.setOnClickListener(this);

	sqlDateRow = (EditText) findViewById(R.id.etInfoByDate);
	sqlModify = (Button) findViewById(R.id.bSqlEditEntry);
	sqlDelete = (Button) findViewById(R.id.bSqlDeleteEntry);
	sqlGetInfo = (Button) findViewById(R.id.bGetInfo);
	sqlModify.setOnClickListener(this);
	sqlDelete.setOnClickListener(this);
	sqlGetInfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {

	switch (arg0.getId()) {
	case R.id.sqlButtonUpdate:
	    boolean didItWork = true;
	    try {
		String date = sqlDate.getText().toString();
		String time = sqlTime.getText().toString();
		String meal = sqlMeal.getText().toString();
		String glucose = sqlGlucose.getText().toString();

		SqlItems entry = new SqlItems(this);
		entry.open();
		entry.createEntry(date, time, meal, glucose);
		entry.close();
	    } catch (Exception e) {
		didItWork = false;
		String error = e.toString();
		Dialog d = new Dialog(this);
		d.setTitle("Error");
		TextView tv = new TextView(this);
		tv.setText(error);
		d.setContentView(tv);
		d.show();

	    } finally {
		if (didItWork) {
		    Dialog d = new Dialog(this);
		    d.setTitle("Success");
		    TextView tv = new TextView(this);
		    tv.setText("You can check your results in the History tab");
		    d.setContentView(tv);
		    d.show();
		}
	    }

	    break;
	case R.id.bGetInfo:
	    try {
		String s = sqlDateRow.getText().toString();
		long l = Long.parseLong(s);
		SqlItems si = new SqlItems(this);
		si.open();
		String returnedDate = si.getDate(l);
		String returnedTime = si.getTime(l);
		si.close();
		sqlDate.setText(returnedDate);
		sqlTime.setText(returnedTime);
	    } catch (Exception e) {
		String error = e.toString();
		Dialog d = new Dialog(this);
		d.setTitle("Error");
		TextView tv = new TextView(this);
		tv.setText(error);
		d.setContentView(tv);
		d.show();

	    }
	    break;
	case R.id.bSqlEditEntry:
	    try {
		String mDate = sqlDate.getText().toString();
		String mTime = sqlTime.getText().toString();
		String sRow = sqlDateRow.getText().toString();
		long lRow = Long.parseLong(sRow);
		SqlItems mSi = new SqlItems(this);
		mSi.open();
		mSi.updateEntry(lRow, mDate, mTime);
		mSi.close();
	    } catch (Exception e) {
		String error = e.toString();
		Dialog d = new Dialog(this);
		d.setTitle("Error");
		TextView tv = new TextView(this);
		tv.setText(error);
		d.setContentView(tv);
		d.show();

	    }

	    break;

	case R.id.bSqlDeleteEntry:
	    try {
		String sRowDel = sqlDateRow.getText().toString();
		long lRowDel = Long.parseLong(sRowDel);
		SqlItems dSi = new SqlItems(this);
		dSi.open();
		dSi.deleteEntry(lRowDel);
		dSi.close();
	    } catch (Exception e) {
		String error = e.toString();
		Dialog d = new Dialog(this);
		d.setTitle("Error");
		TextView tv = new TextView(this);
		tv.setText(error);
		d.setContentView(tv);
		d.show();

	    }
	    break;

	default:
	    break;
	}
    }

    private void prepareListData() {
	listDataHeader = new ArrayList<String>();
	listDataChild = new HashMap<String, List<String>>();

	// Adding child data
	listDataHeader.add("Select Meal");

	// Adding child data
	List<String> selectMeal = new ArrayList<String>();
	selectMeal.add("Breakfast");
	selectMeal.add("After Breakfast");
	selectMeal.add("Lunch");
	selectMeal.add("After Lunch");
	selectMeal.add("Dinner");
	selectMeal.add("After Dinner");

	listDataChild.put(listDataHeader.get(0), selectMeal);
    }

}
