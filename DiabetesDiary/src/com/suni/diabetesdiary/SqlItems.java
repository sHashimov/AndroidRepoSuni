package com.suni.diabetesdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlItems {

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "curr_date";
    public static final String KEY_TIME = "curr_time";
    public static final String KEY_MEAL = "curr_meal";
    public static final String KEY_GLUCOSE = "curr_glucose";

    private static final String DATABASE_NAME = "SugarsHistoryDB";
    private static final String DATABASE_TABLE = "sugarsTable";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		    + KEY_DATE + " TEXT NOT NULL, " + KEY_TIME + " TEXT NOT NULL, " + KEY_MEAL + " TEXT NOT NULL, "
		    + KEY_GLUCOSE + " TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	    onCreate(db);
	}
    }

    public SqlItems(Context c) {
	ourContext = c;
    }

    public SqlItems open() throws SQLException {
	ourHelper = new DbHelper(ourContext);
	ourDatabase = ourHelper.getWritableDatabase();
	return this;

    }

    public void close() {
	ourHelper.close();
    }

    public long createEntry(String date, String time, String meal, String glucose) {
	ContentValues cv = new ContentValues();
	cv.put(KEY_DATE, date);
	cv.put(KEY_TIME, time);
	cv.put(KEY_MEAL, meal);
	cv.put(KEY_GLUCOSE, glucose);
	return ourDatabase.insert(DATABASE_TABLE, null, cv);

    }

    public String getData() {
	String[] columns = new String[] { KEY_ID, KEY_DATE, KEY_TIME, KEY_MEAL, KEY_GLUCOSE };
	Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
	String result = "";
	int iRow = c.getColumnIndex(KEY_ID);
	int iDate = c.getColumnIndex(KEY_DATE);
	int iTime = c.getColumnIndex(KEY_TIME);
	int iMeal = c.getColumnIndex(KEY_MEAL);
	int iGlucose = c.getColumnIndex(KEY_GLUCOSE);

	for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
	    result = result + c.getString(iRow) + "  " + c.getString(iDate) + "  " + c.getString(iTime) + "  "
		    + c.getString(iMeal) + "  " + c.getString(iGlucose) + "\n";
	}

	return result;
    }

    public String getDate(long l) throws SQLException {
	String[] columns = new String[] { KEY_ID, KEY_DATE, KEY_TIME };
	Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ID + "=" + l, null, null, null, null);
	if (c != null) {
	    c.moveToFirst();
	    String date = c.getString(1);
	    return date;
	}
	return null;
    }

    public String getTime(long l) throws SQLException {
	String[] columns = new String[] { KEY_ID, KEY_DATE, KEY_TIME };
	Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ID + "=" + l, null, null, null, null);
	if (c != null) {
	    c.moveToFirst();
	    String time = c.getString(2);
	    return time;
	}
	return null;
    }

    public void updateEntry(long lRow, String mDate, String mTime) throws SQLException {

	ContentValues cvUpdate = new ContentValues();
	cvUpdate.put(KEY_DATE, mDate);
	cvUpdate.put(KEY_TIME, mTime);
	ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ID + "=" + lRow, null);
    }

    public void deleteEntry(long lRowDel) throws SQLException {
	ourDatabase.delete(DATABASE_TABLE, KEY_ID + "=" + lRowDel, null);
    }
}
