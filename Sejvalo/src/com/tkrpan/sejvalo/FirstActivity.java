package com.tkrpan.sejvalo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class FirstActivity extends SherlockActivity implements OnClickListener,
		OnItemSelectedListener, OnScrollListener, OnItemClickListener,
		OnDownloadCompletedInterface, OnTouchListener {

	private Button buttonSearch2, buttonSeave, buttonRefine;

	private EditText editText2;
	private TextView textQuery, textNumber;
	private ListView listView2;
	private Spinner spinner;

	LinearLayout bottomLayout;

	String query, number;

	String htmlString;
	Document document;
	Elements elements;
	Element element;
	Drawable drawable;

	String title, date, price, imageUrl;
	String description, link;
	Integer choice = null;
	String lastQuery = null, lastSort = null;
	Integer nextPage = 1;

	ArrayList<Ad> list;

	int numberAd = 0, iteration = 0, currentAds = 0;

	String titleBookmark, dateBookmark, priceBookmark, descriptionBookmark,
			linkBookmark, imageUrlBookmark;

	// DetailsActivity
	RelativeLayout layoutIvisible;
	TextView textDescription;
	TextView textLink;
	Button buttonBookmark;
	Button buttonCancel;

	// Bookmarks

	Ad adOnLongClick;

	UIstrings uiStrings = new UIstrings();

	// InfoLayout

	RelativeLayout infoLayout;
	Button buttonOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final ActionBar actionbar = getSupportActionBar();

		actionbar.setHomeButtonEnabled(false);
		actionbar.setTitle("");

		buttonSearch2 = (Button) findViewById(R.id.buttonSearch2);
		editText2 = (EditText) findViewById(R.id.editText2);
		textQuery = (TextView) findViewById(R.id.textQuery);
		textNumber = (TextView) findViewById(R.id.textNumber);
		buttonSeave = (Button) findViewById(R.id.buttonSeave);
		buttonRefine = (Button) findViewById(R.id.buttonRefine);
		listView2 = (ListView) findViewById(R.id.listView2);
		spinner = (Spinner) findViewById(R.id.spinner);

		buttonSearch2.setOnClickListener(this);
		buttonSeave.setOnClickListener(this);
		buttonRefine.setOnClickListener(this);
		spinner.setOnItemSelectedListener(this);

		editText2.setOnTouchListener(this);

		//query = getIntent().getExtras().getString("query");
		list = new ArrayList<Ad>();

		listView2.setOnItemClickListener(this);

		// Details Activity
		textDescription = (TextView) findViewById(R.id.descriptionID);
		textLink = (TextView) findViewById(R.id.linkID);
		buttonBookmark = (Button) findViewById(R.id.buttonBookmark);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);

		buttonBookmark.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);

		layoutIvisible = (RelativeLayout) findViewById(R.id.relativeLayoutInvisible);
		bottomLayout = (LinearLayout) findViewById(R.id.linearLayout2);

		// Info Layout
		infoLayout = (RelativeLayout) findViewById(R.id.infoLayout);
		buttonOK = (Button) findViewById(R.id.buttonOK);
		buttonOK.setOnClickListener(this);
		setEnableElements();
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
			infoLayout.setVisibility(View.VISIBLE);
			setDisableElements();
			layoutIvisible.setVisibility(View.GONE);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		switch (arg2) {
		case 0:
			String newer = "&sort=new";
			Log.d("TAGG", newer);
			list.clear();
			//pretraga(query, newer, 1);
			choice = arg2;
			nextPage = 1;
			break;

		case 1:
			String older = "&sort=old";
			Log.d("TAGG", older);
			list.clear();
			pretraga(query, older, 1);
			choice = arg2;
			nextPage = 1;
			break;

		case 2:
			String cheap = "&sort=cheap";
			Log.d("TAGG", cheap);
			list.clear();
			pretraga(query, cheap, 1);
			choice = arg2;
			nextPage = 1;
			break;

		case 3:
			String expensive = "&sort=expensive";
			Log.d("TAGG", expensive);
			list.clear();
			pretraga(query, expensive, 1);
			choice = arg2;
			nextPage = 1;
			break;

		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.buttonSearch2:

			if ((new NetworkState().checkConnection(this)) == true) {

				list.clear();
				String queryNew = editText2.getText().toString();
				nextPage = 1;

				if (queryNew.length() > 1) {
					if (choice == 0)
						pretraga(queryNew, "&sort=new", 1);

					if (choice == 1)
						pretraga(queryNew, "&sort=old", 1);

					if (choice == 2)
						pretraga(queryNew, "&sort=cheap", 1);

					if (choice == 3)
						pretraga(queryNew, "&sort=expensive", 1);
				} else {
					Toast toast = Toast.makeText(this,
							uiStrings.getToastSearch(), Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
							0, 250);
					toast.show();
				}
			} else {
				AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
				alert2.setTitle(uiStrings.getTitleAlert());
				alert2.setMessage(uiStrings.getMsgAlert());
				alert2.setNegativeButton(uiStrings.getButtonNegative(),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent internetIntent = new Intent(
										android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
								startActivity(internetIntent);
							}

						});
				alert2.setPositiveButton(uiStrings.getButtonPositive(),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				alert2.show();
			}

			editText2.setText("");
			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			bottomLayout.setVisibility(View.VISIBLE);
			break;

		case R.id.buttonRefine: 
			break;

		// DetailsActivity
		case R.id.buttonBookmark:

			bookmarkAd();
			setEnableElements();
			break;

		case R.id.buttonCancel:

			setEnableElements();
			break;

		case R.id.buttonOK:
			setEnableElements();
			break;

		default:
			break;
		}
	}

	@Override
	public void onDownloadCompleted(String result) {
		htmlString = result;
		downloadElemtns();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		Log.d("TAGG", "onScroll");

		// Èekiraj ako je zadnji element vidljiv
		if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
			Log.d("TAGG", firstVisibleItem + "/" + visibleItemCount + "/"
					+ totalItemCount);
			nextPage++;
			pretraga(lastQuery, lastSort, nextPage);
			listView2.setSelection(totalItemCount - 1);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		adOnLongClick = (Ad) arg0.getAdapter().getItem(arg2);

		setDisableElements();

		textDescription.setText(adOnLongClick.getDescription());
		textLink.setText(adOnLongClick.getLink());

	}

	public void setDisableElements() {

		editText2.setEnabled(false);
		buttonSearch2.setEnabled(false);
		buttonSeave.setEnabled(false);
		buttonRefine.setEnabled(false);
		spinner.setEnabled(false);
		listView2.setEnabled(false);

		layoutIvisible.setVisibility(View.VISIBLE);
		buttonCancel.setEnabled(true);
		buttonBookmark.setEnabled(true);
		textDescription.setEnabled(true);
		textLink.setEnabled(true);
	}

	public void setEnableElements() {

		editText2.setEnabled(true);
		buttonSearch2.setEnabled(true);
		buttonSeave.setEnabled(true);
		buttonRefine.setEnabled(true);
		spinner.setEnabled(true);
		listView2.setEnabled(true);

		layoutIvisible.setVisibility(View.GONE);
		buttonCancel.setEnabled(false);
		buttonBookmark.setEnabled(false);
		textDescription.setEnabled(false);
		textLink.setEnabled(false);

		infoLayout.setVisibility(View.GONE);
	}

	public void bookmarkAd() {

		DatabaseHandler bookmarksDB = new DatabaseHandler(this);

		titleBookmark = adOnLongClick.getTitle();
		dateBookmark = adOnLongClick.getDate();
		priceBookmark = adOnLongClick.getPrice();
		descriptionBookmark = adOnLongClick.getDescription();
		linkBookmark = adOnLongClick.getLink();
		imageUrlBookmark = adOnLongClick.getImageUrl();

		bookmarksDB.addAd(new Ad(titleBookmark, dateBookmark, priceBookmark,
				descriptionBookmark, linkBookmark, imageUrlBookmark));

		Log.d("TAGG", titleBookmark);
	}

	private void pretraga(String queryFunction, String sort, Integer numberPage) {

		if ((new NetworkState().checkConnection(this)) == true) {

			textQuery.setText(queryFunction);
			String queryIn = queryFunction.replace(" ", "+");

			new DownloadStringFromURL(this, this)
					.execute("http://www.njuskalo.hr/index.php?ctl=search2&f_keywords="
							+ queryIn + sort + "&page=" + numberPage);

		} else {
			AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
			alert2.setTitle(uiStrings.getTitleAlert());
			alert2.setMessage(uiStrings.getMsgAlert());
			alert2.setNegativeButton(uiStrings.getButtonNegative(),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent internetIntent = new Intent(
									android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
							startActivity(internetIntent);
						}

					});
			alert2.setPositiveButton(uiStrings.getButtonPositive(),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			alert2.show();
		}

		lastQuery = queryFunction;
		query = queryFunction;
		lastSort = sort;
	}

	public void downloadElemtns() {
		document = Jsoup.parse(htmlString);
		elements = document.select("div.ad_item");
		number = document.select("div.sort").first().childNode(2).toString()
				.trim();
		String numArray[] = number.split(":");
		numberAd = Integer.parseInt(numArray[1].trim());

		for (Element iterableElement : elements) {

			element = iterableElement
					.select("img[src~=(?i)\\.(png|jpe?g|gif)]").first();

			title = element.attr("alt").toString().trim();
			date = iterableElement.select("div.desc").first().childNode(4)
					.toString().trim();
			price = iterableElement.select("div.price_value").get(0).text()
					.trim();

			imageUrl = "http:" + element.attr("src").toString().trim();

			try {
				drawable = new DownloadDrawable(this).execute(imageUrl).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			description = iterableElement.select("div.desc").select("p").text()
					.trim();
			link = "http://www.njuskalo.hr"
					+ iterableElement.getElementsByAttribute("href").first()
							.attr("href").toString().trim();

			Ad ad = new Ad(title, date, price, drawable, description, link,
					imageUrl);
			list.add(ad);

			AdapterResults adapterResults = new AdapterResults(this,
					R.layout.adapter_results, R.id.titleAdapter,
					R.id.dateAdapter, R.id.priceAdapter, R.id.imageAdapter,
					list);

			listView2.setAdapter(adapterResults);
			textNumber.setText(number);

			listView2.setOnScrollListener(this);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		bottomLayout.setVisibility(View.GONE);
		return false;
	}

}
