package com.example.trackbaidu;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MyLocationListener implements BDLocationListener {
	// Server ip
	public String SERVER_IP_ADDR = "202.120.38.222";
	public String APPNAME = "test";
	public String USERNAME = "test";
	private GeoDataSource datasource;
	
 

	@Override
	public void onReceiveLocation(BDLocation location) {
		GeoData gd = null;
		// TODO Auto-generated method stub
		StringBuffer sbuf = new StringBuffer(256);
		sbuf.append("time:");
		sbuf.append(location.getTime());
		sbuf.append("\n ,error code:");
		sbuf.append(location.getLocType());
		sbuf.append("\n ,Latitude:");
		sbuf.append(location.getLatitude());
		sbuf.append("\n ,Lontitude:");
		sbuf.append(location.getLongitude());
		Log.d("DATA:",sbuf.toString());
		int errorCode = location.getLocType();
		// If loc success code = 61
		if(errorCode == 61 || errorCode == 161 || errorCode == 65 || errorCode == 68){ // GPS or network positioning success
			// set mysqlite
			datasource = new GeoDataSource(SharedValue.getInstance());
			datasource.open();
			Log.e("GEOMEA",sbuf.toString());
			datasource.createGeoData(sbuf.toString());
		}

	}

	@Override
	public void onReceivePoi(BDLocation arg0) {
		// TODO Auto-generated method stub

	}
    
    public void onDestroy(){
    	if(datasource != null){
    		datasource.close();
    	}
    	
    }


}
