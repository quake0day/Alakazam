package com.example.trackbaidu;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncHttpPost extends AsyncTask<String, String, String> {
	private HashMap<String,String> mData = null; // post data
	private GeoData geoData = null;
	private GeoDataSource datasource;
	private static final String TBL_NAME = "track_tbl";
	
	
	public AsyncHttpPost(HashMap<String, String> data,GeoData geoData){
		this.mData = data;
		this.geoData = geoData;
	}
	public AsyncHttpPost(HashMap<String, String> data){
		this.mData = data;
		this.geoData = null;
	}
	@Override
	protected String doInBackground(String... params) {
    	// create new http post
		byte[] result = null;
		String str = "";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(params[0]);
		
		try{
			ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			Iterator<String> it = mData.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				nameValuePair.add(new BasicNameValuePair(key,mData.get(key)));
			}
			
			post.setEntity(new UrlEncodedFormEntity(nameValuePair,"UTF-8"));
			HttpResponse httpResponse = client.execute(post);
			StatusLine statusLine = httpResponse.getStatusLine();
			
			if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
				result = EntityUtils.toByteArray(httpResponse.getEntity());
				str = new String(result,"UTF-8");
				//Pattern pat = Pattern.compile("(.*?)");
				//String res[] = pat.split(str);
	
	
				
				//new_lng = p.split(new_lng)[0];
				//Log.d("PostRES",res[1]);
				//Log.d("PostRES",lng_f);
				Pattern p = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
				Matcher matcher = p.matcher(str);
				while(matcher.find())
				{
				    //System.out.println("found match:"+matcher.group(1));
				    
				    String data = matcher.group(1);
				    //Log.e("MATCH",data);
				    String[] process_data = data.split(",");
				    //Log.e("MATCH",process_data[0]);
				    if (process_data.length > 1){
				    	String new_lat = process_data[0];
				    	String new_lng = process_data[1];
				    	Log.e("MATCH",new_lat);
				    	Log.e("MATCH",new_lng);
						datasource = new GeoDataSource(SharedValue.getInstance());
						Log.e("MATCH","HEHH");
						datasource.open();
						Log.e("MATCH","HEHH");
						datasource.saveGeoData(new_lat,new_lng);
						datasource.close();
				    	/*
				    	TrackingValue appState = ((TrackingValue)getApplication());
				        appState.setlat(Double.valueOf(new_lat));
				        appState.setlng(Double.valueOf(new_lng));
				        
						DatabaseHelper mHelper = new DatabaseHelper(getApplication());
						SQLiteDatabase db = mHelper.getWritableDatabase();
						Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);
						ContentValues values = new ContentValues();
						values.put("length", 0);
						values.put("lat", new_lat);
						values.put("lng", new_lng);
						db.insert(TBL_NAME, null, values);
						*/

					
				    	
				    }
				}
				/*
				if(geoData != null){
				datasource = new GeoDataSource(SharedValue.getInstance());
				datasource.open();
				datasource.updateGeoData(geoData);
				}
				*/
				
			}
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return str;
	}
	

	private Toast toast = null;
    
    private void showTextToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(SharedValue.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    /*
	@Override
	protected void onPostExecute(String result){
		if(result.contains("success")){
			showTextToast("Posting....");
		}
		else{
			toast.cancel();
		}
	}
*/
}
