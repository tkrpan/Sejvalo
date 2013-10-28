package com.tkrpan.sejvalo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdapterBookmark extends BaseAdapter {
	
	private Context context;
	private ArrayList<Ad> bookmarksList;
	private LayoutInflater inflater;
	
	
	public AdapterBookmark(Context context, ArrayList<Ad> bookmarksList) {
		this.context = context;
		this.bookmarksList = bookmarksList;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return bookmarksList.size();
	}


	@Override
	public Object getItem(int position) {
		return bookmarksList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return bookmarksList.indexOf(bookmarksList.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			convertView = inflater.inflate(R.layout.adapter_bookmark, parent, false);
		}
		
		final CheckBox checkBoxBookmark = (CheckBox)convertView.findViewById(R.id.checkBoxBookmark);
		
		TextView titeBookmark = (TextView)convertView.findViewById(R.id.titleAdapterBookmark);
		TextView dateBookmark = (TextView)convertView.findViewById(R.id.dateAdapterBookmark);
		TextView priceBookmark = (TextView)convertView.findViewById(R.id.priceAdapterBookmark);
		
		ImageView imageBookmark = (ImageView)convertView.findViewById(R.id.imageAdapterBookmark);
		
		titeBookmark.setText(bookmarksList.get(position).getTitle());
		dateBookmark.setText(bookmarksList.get(position).getDate());
		priceBookmark.setText(bookmarksList.get(position).getPrice());
		
		imageBookmark.setImageDrawable(bookmarksList.get(position).getImage());
		
		checkBoxBookmark.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Ad bookmarkAd = (Ad)checkBoxBookmark.getTag();
				bookmarkAd.setIsBoxChecked(buttonView.isChecked());
			}
		});
		checkBoxBookmark.setTag(bookmarksList.get(position));
		checkBoxBookmark.setChecked(bookmarksList.get(position).isBoxChecked());

		return convertView;
	}
}
