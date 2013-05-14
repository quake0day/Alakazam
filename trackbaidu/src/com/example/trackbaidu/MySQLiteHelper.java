package com.example.trackbaidu;

import android.content.Context;
//import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_GEO = "geoinfo";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_FLAG = "flag";
	
	private static final String DATABASE_NAME = "geodata.db";
	private static final int DATABASE_VERSION = 2;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GEO + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_LAT
			+ " text not null, "+COLUMN_FLAG+" integer not null );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEO);
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEO);
		db.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEO);
		onCreate(db);

	}

}
