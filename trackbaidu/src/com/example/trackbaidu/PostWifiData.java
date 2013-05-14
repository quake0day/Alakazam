package com.example.trackbaidu;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PostWifiData extends Service {
	WifiBSSIDDetection mWifiBSSIDDetection;


	// 客户通过mBinder和服务进行通信
	private final IBinder mBinder = new WifiPostBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	/**
     * 通讯类，用于和客户端绑定。  
     * 因为StepDetectionService和其客户运行在同一个进程，所以不需要IPC。
     */
	public class WifiPostBinder extends Binder {
		public PostWifiData getService() {
			// 返回StepDetectionService实例，这样客户就可以调用服务的公共方法
			return PostWifiData.this;
		}
	}

	public void onStart(Intent intent, int startId){
		mWifiBSSIDDetection.startScan();
	}
	public void onCreate(){
		Log.e("enter","enter");
		//mStepDetection = new StepDetection(this, this);
		mWifiBSSIDDetection = new WifiBSSIDDetection(this);
		mWifiBSSIDDetection.startScan();
	}

}
