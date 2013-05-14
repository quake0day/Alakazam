package com.example.trackbaidu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PostStepDetectionService extends Service implements StepTrigger{
	String TAG = "PostStepDetectionService";
	int distance = 0;
	private StepDetection mStepDetection;
	public PostStepDetectionService() {
		// TODO Auto-generated constructor stub
		Log.e("Enter PostStep Service","HERE");
		
	}
	
	@Override
	public void onCreate() {
		Log.i(TAG, "[StepDetectionService] onCreate");
		super.onCreate();
		distance = 0;
		//sharedPreferences = getSharedPreferences("stepInfo", Context.MODE_PRIVATE);
		//mHelper = new DatabaseHelper(this);
		//db = mHelper.getWritableDatabase();
		mStepDetection = new StepDetection(this, this);
		startSensor();
	}
	
	@Override
	public void onDestroy() {
		stopSensor();
		super.onDestroy();
	}
	
	/**
	 * 启用传感器
	 */
	public void startSensor() {
		mStepDetection.startSensor();
	}
	
	/**
	 * 停止传感器
	 */
	public void stopSensor() {
		mStepDetection.stopSensor();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public synchronized void onStart(Intent intent, int startId){
		Log.e("Enter PostStep Service","HERE");

	}
	
	public void trigger(int stepCount, float strideLength, float[] orientation){
		Log.e("In call back func","CALL BACK");
	}

}
