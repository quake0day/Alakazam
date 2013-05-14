package com.example.trackbaidu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;


public class WifiBSSIDDetection {
	Context context;
	WifiManager mainWifi;
	List<ScanResult> wifiList;
	int returnType = 0;
	ScanWifiReceiver receiver;
	public WifiBSSIDDetection(Context context) {
		super();
		this.context = context;
		if(mainWifi==null)
			   mainWifi = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
		receiver = new ScanWifiReceiver();
		context.registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}
	
	public void startScan() {
		mainWifi.startScan();
		returnType = 0;
	}
	
	public void startScan(int reType) {
		returnType = reType;
		mainWifi.startScan();
	}
	
	public void endScan(){
		
		if(mainWifi!=null)
		{
			mainWifi=null;
		}
	}

	class ScanWifiReceiver extends BroadcastReceiver {

    	public void onReceive(Context c, Intent intent) {
	    	wifiList = mainWifi.getScanResults();
	    	JSONObject joMain = new JSONObject();
	    	try {
				joMain.put("type", "wifi");
				JSONArray pointArray = new JSONArray();
				Iterator<ScanResult> iterator = wifiList.iterator();
				while(iterator.hasNext())
				{
					ScanResult scanResult = iterator.next();
					JSONObject scanResultJO = new JSONObject();
					scanResultJO.put("BSSID", scanResult.BSSID);
					scanResultJO.put("level", scanResult.level);
					pointArray.put(scanResultJO);
				}
				
				joMain.put("pointArray", pointArray);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	String resultString = joMain.toString();
	    	
	    	if(returnType==0)
	    	{
	    		Log.e("RES",resultString);
	    		
	    		String uriAPI = "http://"+ SharedValue.getServerIPAddr() + ":"+SharedValue.getServerPort()+"/wifi";
	    		HashMap<String,String> postData = new HashMap<String,String>();
		    	postData.put("wifiRes",resultString);
	    		//scanWifiInterface.receiveResult(wifiList);	
		    	AsyncHttpPost asyncHttpPost = new AsyncHttpPost(postData);
		    	asyncHttpPost.execute(uriAPI);
		    	
	    	}
	    	else {
				//scanWifiInterface.receiveResult(resultString);
			}
	    }

    }

}
