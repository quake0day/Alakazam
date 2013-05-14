package com.example.trackbaidu;

import android.app.Application;

public class TrackingValue extends Application {
	private double lat = 0;
	private double lng = 0;
	
	public TrackingValue() {
		// TODO Auto-generated constructor stub
	}
	public double getlat(){
		return this.lat;
	}
	public double getlng(){
		return this.lng;
	}
	public void setlat(double lat){
		this.lat = lat;
	}
	public void setlng(double lng){
		this.lng = lng;
	}
}
