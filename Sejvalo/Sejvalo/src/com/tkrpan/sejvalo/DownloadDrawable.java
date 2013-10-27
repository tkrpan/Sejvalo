package com.tkrpan.sejvalo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class DownloadDrawable extends AsyncTask<String, Void, Drawable> {

	private  Context context;
	public DownloadDrawable (Context cont){
		this.context=cont;
	}
	
	@Override
	protected Drawable doInBackground(String... params) {
		
		Drawable drawable = null;
		
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(params [0]);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream inputStream = httpResponse.getEntity().getContent();
			drawable = Drawable.createFromStream(inputStream, "slika");
			inputStream.close();
			
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return drawable;
	}

}
