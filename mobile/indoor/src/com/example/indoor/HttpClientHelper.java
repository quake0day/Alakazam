package com.example.indoor;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpClientHelper {

	final static String TAG = "httpclient";
//	private static DefaultHttpClient httpClient = null;
	private static HttpClientHelper httpClientHelper = null;
	private static CookieStore cookieStore = null;
	private static HttpContext localContext = null;
	private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.25) Gecko/20111212 Firefox/3.6.25";

	private HttpClientHelper() {
//		httpClient = new DefaultHttpClient();
		// Create a local instance of cookie store
		cookieStore = new BasicCookieStore();
		// Create local HTTP context
		localContext = new BasicHttpContext();
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
//		httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
//				USER_AGENT);
	}

	public static HttpClientHelper getInstance() {
		if (httpClientHelper == null) {
			httpClientHelper = new HttpClientHelper();
		}
		return httpClientHelper;
	}

	private String responseToFile(HttpResponse httpResponse) {
		String filename = "/data/data/com.honeywell.ComfortPoint/details.html";
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			httpResponse.getEntity().writeTo(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filename;
	}

	private String reponseToString(HttpResponse httpResponse) {

		String result = null;
		try {
			InputStream is = httpResponse.getEntity().getContent();
			String line = "";
			int index = 0;
			BufferedReader buReader = new BufferedReader(new InputStreamReader(
					is));
			StringBuilder sb = new StringBuilder();
			while ((line = buReader.readLine()) != null) {
				// result += (line +"\n");
				sb.append(line + "\n");
				Log.d(TAG, "line:" + (index++) + line);
			}
			result = sb.toString();
			Log.d(TAG, "result ---------->" + result);
			is.close();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		}
		return result;
	}

	public String getURLToFile(String url) {
		String filename = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
			
			String encodeUri = URLEncoder.encode(url, "utf-8");
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse;
			httpResponse = httpClient.execute(httpGet, localContext);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				filename = responseToFile(httpResponse);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		}
		return filename;
	}

	public String getURL(String url) {
		String result = null;
		// try {

		HttpGet httpGet = new HttpGet(url);

		HttpResponse httpResponse;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
			
			httpResponse = httpClient.execute(httpGet, localContext);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "ClientProtocolException Message: " + e.getMessage());
			Log.d(TAG, "ClientProtocolException Text: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "IOException Message: " + e.getMessage());
			Log.d(TAG, "IOException Text: " + e.toString());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Log.d(TAG, "Exceptoin Message: " + e.getMessage());
		Log.d(TAG, "Exception Text: " + e.toString());
	}

		return result;
	}
	public void getCookies()
	{
		List<Cookie> cookies = cookieStore.getCookies();
		
        if (cookies.isEmpty())
        {
            Log.d(TAG, "-----cookies is null");
        } 
        else
        {
            for (int i = 0; i < cookies.size(); i++) 
            {
                Log.d(TAG, "-----cooki value" +i + ":" + cookies.get(i));
            }
        }
		
	}
	public String postURLToFile(List<NameValuePair> params, String url) {

		String filename = null;
		try {
			
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
			
			HttpPost request = new HttpPost(url);
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(request, localContext);
			if (response.getStatusLine().getStatusCode() == 200) {
				//result = EntityUtils.toString(response.getEntity());
				filename = responseToFile(response);
			}
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		}
		return filename;
	}
	public String postURL(List<NameValuePair> params, String url) {

		String result = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
			
			HttpPost request = new HttpPost(url);
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			
			HttpResponse response = httpClient.execute(request, localContext);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d(TAG, "ClientProtocolException Message: " + e.getMessage());
			Log.d(TAG, "ClientProtocolException Text: " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d(TAG, "IOException Message: " + e.getMessage());
			Log.d(TAG, "IOException Text: " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		}
		return result;
	}

	public String postURL(String url) {

		String result = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
			
			HttpPost request = new HttpPost(url);
			HttpResponse response = httpClient.execute(request, localContext);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Exceptoin Message: " + e.getMessage());
			Log.d(TAG, "Exception Text: " + e.toString());
		}
		return result;
	}

	public void releaseConnection() {
		// TODO Auto-generated method stub

//		httpClient.getConnectionManager().shutdown();

	}

}
