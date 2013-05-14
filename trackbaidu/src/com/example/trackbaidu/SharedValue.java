package com.example.trackbaidu;

import android.app.Application;
import android.content.Context;

public class SharedValue extends Application {
	private String _myState;
	private static String _serverIPAddr="198.58.100.146";
	private static String _serverPort = "8080";
	// 手机放置位置设定
	private final static int _HAND_HELD = 1;
	private final static int _TROUSER_POCKET = 2;
	private final static int MAGNETIC_BASED_ALGORITHM = 1;
	private final static int GYROSCOPE_BASED_ALGORITHM = 2;
	private final static int HDE_BASED_ALGORITHM = 3;
	private final static int PSP_BASED_ALGORITHM = 4;
	private int _SENSITIVITY = 800;
	private static SharedValue instance = null;

	public SharedValue() {
		// TODO Auto-generated constructor stub
		instance = this;
		_myState="quake0day";
	}

	public void onCreate(){
		super.onCreate();
		instance = this;
	}
	public static String getServerIPAddr(){
		//_serverIPAddr = "128.205.39.117";
		return _serverIPAddr;
	}
	public static String getServerPort(){
		return _serverPort;
	}
	public static Context getInstance(){
		if(instance == null) instance = new SharedValue();
		return instance;
	}
	public boolean getStepLengthMode(){
		return true;
	}
	public int getPhonePosition(){
		return _HAND_HELD;
	}
	public float getStepLength(){
		return 40.5f;
	}
	public int getSensitivity(){
		return _SENSITIVITY;
	}
	public int getAlgorithms(){
		return HDE_BASED_ALGORITHM;
	}
	public String getState(){
		return _myState;
	}
	public void setState(String s){
		_myState = s;
	}

}
