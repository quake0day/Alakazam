package com.example.trackbaidu;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import com.baidu.location.BDLocationListener;

import com.baidu.location.LocationClient;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	// Server ip
	public String SERVER_IP_ADDR = "localhost:8080";
	public String APPNAME = "test";
	public String USERNAME = "test";
	
	private GeoDataSource datasource;
	private static final String TBL_NAME = "track_tbl";
	//43.002501214656945,-78.78754287958145
	//43.016386208091305,-78.84657606504334
	//43.00252867654171,-78.78754019737244
	//43.00295466112032,-78.78766983748164
	double lat = 43.00295466112032; // ¾­¶È
    double lng = -78.78766983748164;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// set mysqlite
		/*
		datasource = new GeoDataSource(this);
		datasource.open();
		List<GeoData> values = datasource.getAllGeoData();
		*/
		
		//ArrayAdapter <GeoData> adapter = new ArrayAdapter<GeoData>(this, android.R.layout.simple_list_item_1,values);
		//setListAdapter(adapter);
		Button myButton1 = (Button) findViewById(R.id.button1);
		final Activity main_activity = this;
		myButton1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(main_activity, AppPreferences.class));
			}
		});
		
		//startService(new Intent(this, PostWifiData.class));
		//startService(new Intent(this, GPSService.class));
		


	}
	public void onClick(View view) throws ParseException, InterruptedException{
		//ArrayAdapter<GeoData> adapter = (ArrayAdapter<GeoData>) getListAdapter();
		GeoData gd = null;
		DatabaseHelper mHelper;
		Iterator<GeoData> tt;
		switch(view.getId()){
		case R.id.add:
			startService(new Intent(this, PostStepDetectionService.class));
			ConnectivityManager mag = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = mag.getActiveNetworkInfo();
			if(info == null){
				Toast.makeText(getApplicationContext(), "Network not avaliable", Toast.LENGTH_SHORT).show();
			}
			else{
				//relayPostToServer(datasource.getAllGeoData());
				//startService(new Intent(this, PostGeoData.class));
				startService(new Intent(this, PostWifiData.class));
				//startService(new Intent(this, PostStepDetectionService.class));
				Toast.makeText(getApplicationContext(), "Finished..", Toast.LENGTH_SHORT).show();
			}
			//adapter.add(gd);
			break;
		case R.id.openmap:
			//Intent intent = new Intent(getApplicationContext(), GMapActivity.class);
			//startActivity(intent);
			/*
	    	TrackingValue appState = ((TrackingValue)getApplicationContext());
	        lat = appState.getlat();
	        lng = appState.getlng();
	        
			mHelper = new DatabaseHelper(getApplicationContext());
			SQLiteDatabase db = mHelper.getWritableDatabase();
			datasource = new GeoDataSource(this);
			datasource.open();
			List<GeoData> values1 = datasource.getAllGeoData();
			datasource.close();
			tt = values1.iterator();
			
			
			while(tt.hasNext()){
				GeoData data = (GeoData) tt.next();
				Log.e("data",data.getGeoData());
			}
			//GeoData k = values1.get(tt);
			//String datak = k.getGeoData();
			//Log.e("k",datak);
			 * *
			 */
			stopService(new Intent(this, PostStepDetectionService.class));
			mHelper = new DatabaseHelper(getApplicationContext());
			SQLiteDatabase db = mHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			db.delete(TBL_NAME, null, null);
			values.put("length", 0);
			values.put("lat", lat);
			values.put("lng", lng);
			db.insert(TBL_NAME, null, values);
		

		}
		//adapter.notifyDataSetChanged();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	protected void onResume(){
		//datasource.open();
		super.onResume();
	}
	
	@Override 
	protected void onDestroy(){
		super.onDestroy();
		if(datasource != null){
			datasource.close();
		}
	}
	
	


}
