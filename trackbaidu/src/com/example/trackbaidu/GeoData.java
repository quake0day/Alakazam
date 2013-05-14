package com.example.trackbaidu;

public class GeoData {
	private long id;
	private String geodata;
	private int flag;
	
	public int getFlag(){
		return this.flag;
	}
	
	public long getId(){
		return id;
	}
	
	public void setFlat(int flag){
		this.flag = flag;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public String getGeoData(){
		return geodata;
	}
	
	public void setGeoData(String data){
		this.geodata = data;
	}
	
	@Override
	public String toString(){
		return geodata;
	}

	public GeoData() {
		// TODO Auto-generated constructor stub
	}

}
