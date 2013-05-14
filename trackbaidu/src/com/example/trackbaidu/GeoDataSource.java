package com.example.trackbaidu;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GeoDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private int DEFAULT_FLAG = 0;
	private int MARKED_FLAG = 1;

	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_LAT,MySQLiteHelper.COLUMN_FLAG};
	

	public GeoDataSource(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper = new MySQLiteHelper( context );
		

	}
	public synchronized void open() throws SQLException{
		database = dbHelper.getReadableDatabase();
		//dbHelper.onUpgrade(database, 1, 2);
	}
	public synchronized void close(){
		if (database != null)
			database.close();
		if(dbHelper != null)
			dbHelper.close();
	}
	
	public synchronized void createGeoData(String Geodata){
		ContentValues values = new ContentValues();
		long insertId = -1;
		values.put(MySQLiteHelper.COLUMN_LAT, Geodata);
		values.put(MySQLiteHelper.COLUMN_FLAG, DEFAULT_FLAG);
		try{
			database.beginTransaction();
			insertId = database.insert(MySQLiteHelper.TABLE_GEO, null, values);
			database.setTransactionSuccessful();
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			database.endTransaction();
		}
	}
	private synchronized GeoData cursorToGeoData(Cursor cursor) {
		// TODO Auto-generated method stub
		GeoData gd = new GeoData();
		gd.setId(cursor.getLong(0));
		gd.setGeoData(cursor.getString(1));
		return gd;
	}
	
	public synchronized void deleteGeoData(GeoData gd){
		long id = gd.getId();
		System.out.println("Comment deleted with id:" + id);
		database.delete(MySQLiteHelper.TABLE_GEO, MySQLiteHelper.COLUMN_ID + " = "+id, null);
		
	}
	public synchronized void updateGeoData(GeoData gd){
		long id = gd.getId();
		System.out.println("Geodata update with id:" + id);
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_FLAG, MARKED_FLAG);
		database.update(MySQLiteHelper.TABLE_GEO, values, MySQLiteHelper.COLUMN_ID+" = " + id, null);
	}
	
	public synchronized void updateGeoData(long id){
		System.out.println("Geodata update with id:" + id);
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_FLAG, MARKED_FLAG);
		database.update(MySQLiteHelper.TABLE_GEO, values, MySQLiteHelper.COLUMN_ID+" = " + id, null);
	}
	
	public void saveGeoData(String lat, String lng){
		Log.e("KKK","DAVE");
		String Geodata = lat + lng;
		ContentValues values = new ContentValues();
		long insertId = -1;
		values.put(MySQLiteHelper.COLUMN_LAT, Geodata);
		values.put(MySQLiteHelper.COLUMN_FLAG, DEFAULT_FLAG);
		Log.e("latlng",lat+lng);
		try{
			database.beginTransaction();
			insertId = database.insert(MySQLiteHelper.TABLE_GEO, null, values);
			database.setTransactionSuccessful();
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			database.endTransaction();
		}
	}
	public synchronized List<GeoData> getAllGeoData(){
		List<GeoData> gd = new ArrayList<GeoData>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_GEO, allColumns, MySQLiteHelper.COLUMN_FLAG + "= 0", null, null, null, null);
		cursor.moveToFirst();
		//int total = 0;
		while(!cursor.isAfterLast()){
			GeoData geodata = cursorToGeoData(cursor);
			gd.add(geodata);
			cursor.moveToNext();
			//total = total + 1;
		}
		//Log.e("total",String.valueOf(total));
		if(cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		return gd;
	}


}
