package com.example.indoor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<String,Integer,Double>{

	@Override
	protected Double doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
		String urlStr = "http://128.205.39.117:8080";
		//String urlStr = params[0];
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(urlStr);
		ArrayList<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		//nvps.add(new BasicNameValuePair("username","tony"));
		//nvps.add(new BasicNameValuePair("password","123451"));
		nvps.add(new BasicNameValuePair(params[0],params[1]));
		UrlEncodedFormEntity entityIn = new UrlEncodedFormEntity(
				nvps, "UTF-8");
		httppost.setEntity(entityIn);
		HttpResponse response = httpclient.execute(httppost);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}


