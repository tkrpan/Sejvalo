package com.tkrpan.sejvalo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MojiOglasi extends SherlockActivity implements 
		OnItemClickListener {

	private ListView listView;

	private String query;
	private String nest;

	private ArrayList<Ad> list;
	private ArrayList chekededItems;

	private String titleBookmarks, dateBookmarks, priceBookmarks,
			descriptionBookmarks, linkBookmarks, imageUrlBookmarks;

	private Drawable imageBookmarks;

	private Ad checkedAd = new Ad();
	private Boolean isCheckedAd, isCheckedFlag = false;
	private Ad adOnLongClick;

	DatabaseHandler db;
	
	MenuItem delete; // ***
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moji_oglasi);
		
		// ***
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final ActionBar actionbar = getSupportActionBar();

		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setTitle("   Moji oglasi");
		// ***
		
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(this);
		
		loadBookmarks();
		
		if (list.isEmpty()) listView.setVisibility(View.GONE); // ***
	}

	// ***
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.moji_oglasi, menu);	
		
		// Ovdje ide pojavljivanje i sakrivanje kante za brisanje iz ActionBara
		
		/*delete = menu.findItem(R.id.obrisi);
		if (isCheckedFlag == true) 
	    {
			delete.setVisible(true);
	    }
		else
		{
			delete.setVisible(false);
		}*/
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.moje_pretrage:
			finish();
			Intent pretrage = new Intent(this, MojePretrage.class);
			startActivity(pretrage);
			break;
		case R.id.obrisi:
			UIstrings uiString = new UIstrings();
			getChekededItems();
			if (isCheckedFlag == true) {
				db.deleteMultipleAd(chekededItems);
				loadBookmarks();
				isCheckedFlag = false;
			} else {
				Toast.makeText(this, uiString.getToastDelete(),
						Toast.LENGTH_SHORT).show();
			}
			if (list.isEmpty()) listView.setVisibility(View.GONE);
			
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}// ***

	public void loadBookmarks() {

		db = new DatabaseHandler(this);
		list = (ArrayList<Ad>) db.getAllAds();

		for (int i = 0; i < list.size(); i++) {
			imageUrlBookmarks = list.get(i).getImageUrl();

			try {
				imageBookmarks = new DownloadDrawable(this).execute(
						imageUrlBookmarks).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			list.get(i).setImage(imageBookmarks);
		}
		AdapterBookmark adapterBookmark = new AdapterBookmark(this, list);
		listView.setAdapter(adapterBookmark);
		
	
	}

	public void getChekededItems() {
		
		chekededItems = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			checkedAd = list.get(i);
			isCheckedAd = checkedAd.isBoxChecked();
			if (isCheckedAd == true) {
				chekededItems.add(checkedAd);
				isCheckedFlag = true;
				// setEnableDelete(isCheckedAd);
			}
		}
	}
	


	/*
	 * public void setEnableDelete(Boolean check){
	 * 
	 * if(check == false){ buttonDelete.setEnabled(false);
	 * buttonDelete.setVisibility(View.GONE); } if(check == true){
	 * buttonDelete.setEnabled(true); buttonDelete.setVisibility(View.VISIBLE);
	 * } }
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// Ad clickedAd = (Ad)parent.getItemAtPosition(position);
		Ad clickedAd = (Ad) parent.getAdapter().getItem(position);
		Toast.makeText(getApplicationContext(),
				"Clicked on Row: " + clickedAd.getTitle(), Toast.LENGTH_SHORT)
				.show();

		getChekededItems();
	}


	

	
}

