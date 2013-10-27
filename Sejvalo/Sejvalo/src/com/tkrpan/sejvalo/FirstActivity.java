package com.tkrpan.sejvalo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class FirstActivity extends SherlockActivity implements OnClickListener {


	private EditText editText;
	private Button buttonSearch;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final ActionBar actionbar = getSupportActionBar();

		actionbar.setHomeButtonEnabled(false);
		actionbar.setTitle("");

	
		editText = (EditText) findViewById(R.id.editText1);
		buttonSearch = (Button) findViewById(R.id.buttonSearch1);

		buttonSearch.setOnClickListener(this);

      
		// pojavljivanje buttona za brisanje   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.first, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.moji_oglasi:
			Intent oglasi = new Intent(this, MojiOglasi.class);
			startActivity(oglasi);
			break;
		case R.id.moje_pretrage:
			Intent pretrage = new Intent(this, MojePretrage.class);
			startActivity(pretrage);
			break;
		case R.id.info:
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		UIstrings uiString = new UIstrings();

		switch (v.getId()) {

		case R.id.buttonSearch1:

			if ((new NetworkState().checkConnection(this)) == true) {

				query = editText.getText().toString();

				if (query.length() > 1) {
					Intent intent = new Intent(this, SecondActivity.class);
					intent.putExtra("query", query);

					editText.setText("");
					startActivity(intent);

				} else {
					Toast toast = Toast.makeText(this,
							uiString.getToastSearch(), Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
							0, 270);
					toast.show();
				}

			} else {
				AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
				alert1.setTitle(uiString.getTitleAlert());
				alert1.setMessage(uiString.getMsgAlert());
				alert1.setNegativeButton(uiString.getButtonNegative(),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent internetIntent = new Intent(
										android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
								startActivity(internetIntent);
							}
						});
				alert1.setPositiveButton(uiString.getButtonPositive(),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				alert1.show();
			}
			break;

		default:
			break;
		}
	}

}
