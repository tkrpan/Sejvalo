package com.tkrpan.sejvalo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkState {
	
	public NetworkState(){
		
	}

	public boolean checkConnection (Context context) {
		
		NetworkInfo networkInfo = (NetworkInfo)((ConnectivityManager)context
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		
		if (networkInfo == null || !networkInfo.isConnected()){
			return false;
		}
		
		return true;
	}
	
}
