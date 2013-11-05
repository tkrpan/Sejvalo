package com.tkrpan.sejvalo;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.view.WindowManager;

public class InfoActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		final ActionBar actionbar = getSupportActionBar();
		
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setTitle(" O aplikaciji");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.info, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case android.R.id.home:
			finish();
			break;
			
		case R.id.moji_oglasi:
			Intent oglasi = new Intent(this, MojiOglasi.class);
			startActivity(oglasi);
			break;
			
		case R.id.moje_pretrage:
			Intent pretrage = new Intent(this, MojePretrage.class);
			startActivity(pretrage);
			break;
			
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
