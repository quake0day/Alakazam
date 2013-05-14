package com.example.trackbaidu;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


public class GPSService extends Service{
	// fetch gps info every 30 minutes
	public int EXTRA_GPS_FETCH_RATE = 30*60*1000;
	// post data every 3 hours
	public int EXTRA_POST_RATE = 3*60*60* 1000;


	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	private static final Class[] mStartForegroundSignature = new Class[]{
		int.class, Notification.class
	};
	private static final Class[] mStopForegroundSignature = new Class[]{boolean.class};
	private NotificationManager mNM;
	private Method mStartForeground;
	private Method mStopForeground;
	private Object[] mStartForegroundArgs = new Object[2];
	private Object[] mStopForegroundArgs = new Object[1];
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try{
        	mStartForeground = GPSService.class.getMethod("startForeground", mStartForegroundSignature);
        	mStopForeground = GPSService.class.getMethod("stopForeground", mStopForegroundSignature);
        }catch(NoSuchMethodException e){
        	mStartForeground = mStopForeground = null;
        }
        Notification notification = new Notification(R.drawable.gps_thm,"Location Reporter",System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this,MainActivity.class), 0);
        notification.setLatestEventInfo(this, "Mobile Experiment", "Location Reporter", contentIntent);
        startForegroundCompat(1,notification);
    }
    
    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.d("LBS:","OnStart");
        //Toast.makeText(getApplicationContext(), "LBS Tracking Service Start", Toast.LENGTH_SHORT).show();
        
        // Initial Baidu Map SDK
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
		// Start it!
		mLocationClient.start();
		
		// start post to server service
		// for post to server action
		Intent intent_post = new Intent(getBaseContext(),Alarmreceiver.class);
		intent_post.setAction("postserver.action");
		PendingIntent sender_post = PendingIntent.getBroadcast(getBaseContext(), 0, intent_post, 0);
		AlarmManager am_post = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// check every  5 min
		long firstime = SystemClock.elapsedRealtime();
		
		
		am_post.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstime,EXTRA_POST_RATE,sender_post);
		
		
		// Pull a request
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.requestLocation();
			//SharedValue sh = new SharedValue();
			//sh.setState("OK");
		}
		else
			Log.d("LBS","locClient is null or not started");
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mLocationClient.stop();
    }

    
    private void setLocationOption(){
    	LocationClientOption option = new LocationClientOption();
    	// open GPS when service start
    	option.setOpenGps(true);
    	// Set Result Return type default:gcj02
    	option.setCoorType("gcj02");
    	// Set request span
    	option.setScanSpan(EXTRA_GPS_FETCH_RATE);
    	// Enable cache
    	option.disableCache(false);
    	option.setPriority(LocationClientOption.GpsFirst);
    	mLocationClient.setLocOption(option);
   
    }
    
    private void startForegroundCompat(int id, Notification n){
    	if(mStartForeground != null){
    		mStartForegroundArgs[0] = id;
    		mStartForegroundArgs[1] = n;
    		

    			try {
					mStartForeground.invoke(this, mStartForegroundArgs);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			return;

    	}
    	stopForeground(false);
    	mNM.notify(id,n);
    }
    
    private void stopForegroundCompat(int id){
    	if(mStopForeground != null){
    		mStopForegroundArgs[0]  = Boolean.TRUE;
    		
    			try {
					mStopForeground.invoke(this, mStopForegroundArgs);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		return;
    	}
    	mNM.cancel(id);
    	stopForeground(true);
    }

}