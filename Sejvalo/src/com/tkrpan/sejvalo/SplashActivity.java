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
	Animation scale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		img_splash = (ImageView) findViewById(R.id.img_splash);
	       
		
		final AnimationDrawable anim = new AnimationDrawable();
		anim.addFrame(getResources().getDrawable(R.drawable.nulta), 100);
		anim.addFrame(getResources().getDrawable(R.drawable.prva), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.druga), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.treca), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.cetvrta), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.peta), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.sesta), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.sedma), 150);
		anim.addFrame(getResources().getDrawable(R.drawable.osma), 350);
		anim.addFrame(getResources().getDrawable(R.drawable.sedma), 150);
		
		
		img_splash.setImageDrawable(anim);
		
		img_splash.post(new Runnable(){
	        public void run(){
	        	anim.start();
	        	anim.setOneShot(true);
	        }
	    });
	
		
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
