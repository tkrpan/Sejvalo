package com.tkrpan.sejvalo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;

public class DownloadStringFromURL extends AsyncTask<String, Void, String> {
	
	private Context context;
	private OnDownloadCompletedInterface listener;
	
	public DownloadStringFromURL(Context context, OnDownloadCompletedInterface listener) { 
		
		this.context = context; 
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... urls) {
		
		String response = null;
		
		try {

		for (String url : urls) {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
		}
			} catch (UnsupportedEncodingException e){
		
			} catch (ClientProtocolException e) {

			} catch (IOException e) {
				
			}
		return response;
	}

	@Override
	protected void onPostExecute(String result) {

		if(listener != null){ 
			listener.onDownloadCompleted(result); 
		}
		super.onPostExecute(result);
	}
}
