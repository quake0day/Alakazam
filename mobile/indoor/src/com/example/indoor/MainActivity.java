package com.example.indoor;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener
{
    private TextView tvAccelerometer;
    private TextView tvMagentic;
    private TextView tvLight;
    private TextView tvOrientation;
    private TextView tvSensors;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //  获得SensorManager对象
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //  注册加速度传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        //  注册磁场传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);

        //  注册光线传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_FASTEST);

        //  注册方向传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);

        tvAccelerometer = (TextView) findViewById(R.id.tvAccelerometer);
        tvMagentic = (TextView) findViewById(R.id.tvMagentic);
        tvLight = (TextView) findViewById(R.id.tvLight);
        tvOrientation = (TextView) findViewById(R.id.tvOrientation);
        tvSensors = (TextView)findViewById(R.id.tvSensors);
        
        //  获得当前手机支持的所有传感器
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor:sensors)
        {
            //  输出当前传感器的名称
            tvSensors.append(sensor.getName() + "\n");
        }
        
        
       // WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
       // List list = wifiManager.getScanResults();
      //  Iterator wifilist = list.iterator();
        //WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        //Log.d("wifiInfo", wifiInfo.toString());
        //Log.d("SSID",wifiInfo.getSSID());
      //  while(wifilist.hasNext()){
        //	new MyAsyncTask().execute("Wifi",wifilist.next().toString().replaceAll(" ",""));
        //	Log.d("WIFI:",wifilist.next().toString());
       // }
        //Log.d("SSID:",list.iterator())
        
        
       // connect("http://128.205.39.117:8080");
        


        //

        
     
     

    }
    

    
 

	@Override
    public void onSensorChanged(SensorEvent event)
    {
        //  通过getType方法获得当前传回数据的传感器类型
		
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:            //  处理加速度传感器传回的数据
                String accelerometer = "加速度\n" + "X：" + event.values[0] + "\n"
                        + "Y:" + event.values[1] + "\n" + "Z:" + event.values[2] + "\n";
                tvAccelerometer.setText(accelerometer);
                new MyAsyncTask().execute("ACCELEROMETER",combineXYZ(event.values[0],event.values[1],event.values[2]));
                break;
            case Sensor.TYPE_LIGHT:                    //  处理光线传感器传回的数据
                tvLight.setText("亮度：" + event.values[0]);
                new MyAsyncTask().execute("LIGHT",event.values[0]+"");
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:            //  处理磁场传感器传回的数据
                String magentic = "磁场\n" + "X：" + event.values[0] + "\n" + "Y:"
                        + event.values[1] + "\n" + "Z:" + event.values[2] + "\n";
                new MyAsyncTask().execute("MAGNETIC_FIELD",combineXYZ(event.values[0],event.values[1],event.values[2]));
                tvMagentic.setText(magentic);
                break;
            case Sensor.TYPE_ORIENTATION:                //  处理方向传感器传回的数据
                String orientation = "方向\n" + "X：" + event.values[0] + "\n"
                        + "Y:" + event.values[1] + "\n" + "Z:" + event.values[2] + "\n";
                new MyAsyncTask().execute("ORIENTATION",combineXYZ(event.values[0],event.values[1],event.values[2]));
                tvOrientation.setText(orientation);
                break;
        }
        
    }
	
	public String combineXYZ(float values, float values2, float values3){
		String combined = new String();
		combined = "x,"+values+","+"y,"+values2+","+"z,"+values3;
		return combined;
		
	}
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}