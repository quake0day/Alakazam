package com.example.trackbaidu;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PostGeoData extends Service{
	// Server ip
	public String SERVER_IP_ADDR = "localhost";
	public String APPNAME = "test";
	public String USERNAME = "test";
	int k = 0;

	
	private GeoDataSource datasource;

	public PostGeoData() {

		
	}
	
	private synchronized void relayPostToServer(final GeoData data) throws ParseException, ClientProtocolException, IOException{
		String geoinfo = data.getGeoData();
		String[] geoPart = geoinfo.split(",");
		if(geoPart.length > 1){
			String raw_time = geoPart[0];
			String error_code_raw = geoPart[1];
			String latitude_raw = geoPart[2];
			String longitude_raw = geoPart[3];
			// For time
			String[] raw_time1 = raw_time.split("time:");
			String time = null;
	    	String unixDate="";
			if(raw_time1.length > 1){
				time = raw_time1[1].split("\n")[0];
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = df.parse(time);
				long s = date.getTime();
				unixDate = String.valueOf(s);
				//unixDate = String.valueOf(s).substring(0,10);
				Log.d("GEOINFO Time:",unixDate);
			}
			// For errorcdoe
			
			// check if has internet connection
			int error_code_extra = -1;
			NetworkInfo networkInfo = null;
			ConnectivityManager mag = null;
			try{
				mag = (ConnectivityManager) SharedValue.getInstance().getSystemService(SharedValue.getInstance().CONNECTIVITY_SERVICE);
				networkInfo = mag.getActiveNetworkInfo();
			} catch (Exception e){
				Log.e("connectivity",e.toString());
			}
			if(networkInfo != null && mag != null){
				boolean avaliable = networkInfo.isAvailable();
				if(avaliable){
					State state = null;
					state = mag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
					if(State.CONNECTED == state){ // device is connected to wifi
						error_code_extra = 0;
					}
					state = mag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
					if(State.CONNECTED == state){ // device is connected to 3G
						error_code_extra = 1;  
					}
				}
				Log.d("errorcodeextra",String.valueOf(error_code_extra));
			}
			String[] error_code1 = error_code_raw.split(":");
			String errorcode = "error";
			if(error_code1.length > 1){
				errorcode = error_code1[1].split("\n")[0];
				if(errorcode.matches("161") && error_code_extra == 0){
					errorcode = "Wifi";
				}
				else if(errorcode.matches("161") && error_code_extra == 1){
					errorcode = "3G";
				} else if(errorcode.matches("61")){
					errorcode = "GPS";
				}else if(errorcode.matches("65")){
					errorcode = "Cache";
				}else if(errorcode.matches("68")){
					errorcode = "Offline";
				}
				else{
					errorcode = "Unknown";
				}
			}
			Log.d("Errorcode",errorcode);
			// For Latitude
			String[] lat_raw1 = latitude_raw.split(":");
			String latitude = null;
			if(lat_raw1.length > 1){
				latitude = lat_raw1[1].split("\n")[0];
				Log.d("GEOINFO Latitude:",latitude);
			}
			// For Longitude
			String[] long_raw1 = longitude_raw.split(":");
			String longitude = null;
			if(long_raw1.length > 1){
				longitude = long_raw1[1];
				Log.d("GEOINFO longitude:",longitude);
			}

	        
	    	SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(SharedValue.getInstance());
	    	USERNAME = SP.getString("username", "NA");
	    	APPNAME = SP.getString("apptype", "test");
	    	SERVER_IP_ADDR = SP.getString("ip", "202.120.38.222");
	    	String uriAPI = "http://"+ SERVER_IP_ADDR + ":8080/loc";
	    	
	    	// create new http post
	    	//final HttpPost httpRequest = new HttpPost(uriAPI);
	    	DecimalFormat df = new DecimalFormat("###0.0###");
	    	HashMap<String,String> postData = new HashMap<String,String>();
	    	postData.put("user",USERNAME);
	    	postData.put("app",APPNAME);
	    	postData.put("ltype",errorcode);
	    	postData.put("latitude",latitude);
	    	postData.put("longitude",longitude);
	    	postData.put("time",unixDate);
	    	AsyncHttpPost asyncHttpPost = new AsyncHttpPost(postData,data);
	    	asyncHttpPost.execute(uriAPI);

		}
	}
	    	/*
	    	final List <NameValuePair> params = new ArrayList<NameValuePair>();
	    	
	    	params.add(new BasicNameValuePair("user",USERNAME));
	    	params.add(new BasicNameValuePair("app",APPNAME));
	    	params.add(new BasicNameValuePair("ltype",errorcode));
	    	params.add(new BasicNameValuePair("latitude",latitude));
	    	
	    	params.add(new BasicNameValuePair("longitude",longitude));

			params.add(new BasicNameValuePair("time",unixDate));
			
			    	
			final Thread postThread = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
						httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
						
						String s = "\nparams to string \n"
								+ params.toString()
								+ "\nhttpRequest params \n"
								+ httpRequest.getParams().toString()
								+ "\nhttpRequest \n"
								+ httpRequest.getRequestLine().toString();
						Log.d("LBS",s);
						
						HttpClient httpclient = new DefaultHttpClient();
						
						// fire the post request
						HttpResponse httpResponse = httpclient.execute(httpRequest);
						String response = null;
						
						if(httpResponse.getStatusLine().getStatusCode() == 200){
							response = EntityUtils.toString(httpResponse.getEntity());
							
							datasource.updateGeoData(data);
							Log.d("LBS",response);

						}
						else
						{
							response = httpResponse.getStatusLine().toString();
							Log.d("LBS","ERROR RESPONSE: "+response);
						}
						if(datasource != null){
							datasource.close();
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
	    		
	    	});
			  postThread.start();

	}
	*/
	
	
	
	/*
	
	private synchronized void relayPostToServer(List<GeoData> gd) throws ParseException, InterruptedException{
		Iterator<GeoData> dataset = gd.iterator();
		while(dataset.hasNext()){
			final GeoData data = dataset.next();
			
			//datasource.updateGeoData(data);
			String geoinfo = data.getGeoData();
			String[] geoPart = geoinfo.split(",");
			if(geoPart.length > 1){
				String raw_time = geoPart[0];
				String error_code_raw = geoPart[1];
				String latitude_raw = geoPart[2];
				String longitude_raw = geoPart[3];
				// For time
				String[] raw_time1 = raw_time.split("time:");
				String time = null;
		    	String unixDate="";
				if(raw_time1.length > 1){
					time = raw_time1[1].split("\n")[0];
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = df.parse(time);
					long s = date.getTime();
					unixDate = String.valueOf(s);
					//unixDate = String.valueOf(s).substring(0,10);
					Log.d("GEOINFO Time:",unixDate);
				}
				// For errorcdoe
				
				// check if has internet connection
				int error_code_extra = -1;
				NetworkInfo networkInfo = null;
				ConnectivityManager mag = null;
				try{
					mag = (ConnectivityManager) SharedValue.getInstance().getSystemService(SharedValue.getInstance().CONNECTIVITY_SERVICE);
					networkInfo = mag.getActiveNetworkInfo();
				} catch (Exception e){
					Log.e("connectivity",e.toString());
				}
				if(networkInfo != null && mag != null){
					boolean avaliable = networkInfo.isAvailable();
					if(avaliable){
						State state = null;
						state = mag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
						if(State.CONNECTED == state){ // device is connected to wifi
							error_code_extra = 0;
						}
						state = mag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
						if(State.CONNECTED == state){ // device is connected to 3G
							error_code_extra = 1;  
						}
					}
					Log.d("errorcodeextra",String.valueOf(error_code_extra));
				}
				String[] error_code1 = error_code_raw.split(":");
				String errorcode = "error";
				if(error_code1.length > 1){
					errorcode = error_code1[1].split("\n")[0];
					if(errorcode.matches("161") && error_code_extra == 0){
						errorcode = "Wifi";
					}
					else if(errorcode.matches("161") && error_code_extra == 1){
						errorcode = "3G";
					} else if(errorcode.matches("61")){
						errorcode = "GPS";
					}else if(errorcode.matches("65")){
						errorcode = "Cache";
					}else if(errorcode.matches("68")){
						errorcode = "Offline";
					}
				}
				Log.d("Errorcode",errorcode);
				// For Latitude
				String[] lat_raw1 = latitude_raw.split(":");
				String latitude = null;
				if(lat_raw1.length > 1){
					latitude = lat_raw1[1].split("\n")[0];
					Log.d("GEOINFO Latitude:",latitude);
				}
				// For Longitude
				String[] long_raw1 = longitude_raw.split(":");
				String longitude = null;
				if(long_raw1.length > 1){
					longitude = long_raw1[1];
					Log.d("GEOINFO longitude:",longitude);
				}
		    	SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(SharedValue.getInstance());
		    	USERNAME = SP.getString("username", "NA");
		    	APPNAME = SP.getString("apptype", "test");
		    	SERVER_IP_ADDR = SP.getString("ip", "202.120.38.222");
		    	String uriAPI = "http://"+ SERVER_IP_ADDR + ":8080/submit-report";
		    	
		    	// create new http post
		    	final HttpPost httpRequest = new HttpPost(uriAPI);
		    	
		    	final List <NameValuePair> params = new ArrayList<NameValuePair>();
		    	DecimalFormat df = new DecimalFormat("###0.0###");
		    	params.add(new BasicNameValuePair("user",USERNAME));
		    	params.add(new BasicNameValuePair("app",APPNAME));
		    	params.add(new BasicNameValuePair("ltype",errorcode));
		    	params.add(new BasicNameValuePair("latitude",latitude));
		    	
		    	params.add(new BasicNameValuePair("longitude",longitude));

				params.add(new BasicNameValuePair("time",unixDate));
				    	
				// Sleep for 2 sec.. For sync

		    	new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try{
							httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
							
							String s = "\nparams to string \n"
									+ params.toString()
									+ "\nhttpRequest params \n"
									+ httpRequest.getParams().toString()
									+ "\nhttpRequest \n"
									+ httpRequest.getRequestLine().toString();
							Log.d("LBS",s);
							
							HttpClient httpclient = new DefaultHttpClient();
							
							// fire the post request
							HttpResponse httpResponse = httpclient.execute(httpRequest);
							String response = null;
							
							if(httpResponse.getStatusLine().getStatusCode() == 200){
								response = EntityUtils.toString(httpResponse.getEntity());
								
								datasource.updateGeoData(data);
								Log.d("LBS",response);
								datasource.close();
							}
							else
							{
								response = httpResponse.getStatusLine().toString();
								Log.d("LBS","ERROR RESPONSE: "+response);
							}
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}
		    		
		    	}).start();
		    	

		    


		    }

				
			}
			
			//String latitude = geoPart[1];
			//String latitude = geoinfo.split("\n")[2].split(":")[1];
			//String longitude = geoinfo.split("\n")[3].split(":")[1];
			
			//Log.d("GEOINFO Longitude:",longitude);
		
	}
	
	
	*/

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public synchronized void onStart(Intent intent, int startId){
		// TODO Auto-generated constructor stub
		Log.d("GEOPOST","IN");
		int code = -1;
		datasource = new GeoDataSource(SharedValue.getInstance());
		datasource.open();
		/*
		try {
			relayPostToServer(datasource.getAllGeoData());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
		List<GeoData> gd = datasource.getAllGeoData();
		Iterator<GeoData> dataset = gd.iterator();
		List toPost = new ArrayList();
		k = k +1;
		Log.e("DATA::",String.valueOf(k));
		int j = 0;
		int total = 0;
		while(dataset.hasNext()){
			GeoData data = dataset.next();
				Log.e("DATA::",String.valueOf(j));
				j = j + 1;
				toPost.add(data);
				total = total + 1;
		}
		Iterator dataToPost = toPost.iterator();
		
		
		while(dataToPost.hasNext()){
			GeoData data = (GeoData) dataToPost.next();
			try {
				relayPostToServer(data);
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(datasource != null){
			datasource.close();
		}

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if(datasource != null){
			datasource.close();
		}
	}

}
