package com.tkrpan.sejvalo;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends SherlockActivity {
	
	ImageView img_splash;
	Animation animFadein;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		img_splash = (ImageView) findViewById(R.id.img_splash);
	       
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);  
		
		img_splash.startAnimation(animFadein);
		
		/*img_splash.post(new Runnable(){
	        public void run(){
	        	img_splash.startAnimation(animFadein);
	        	
	        }
	    });*/
	
		
		Thread splash = new Thread(){
			public void run(){
				try{
					sleep(2500);
					Intent i = new Intent("First");
					startActivity(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		
		splash.start();
	}


}
