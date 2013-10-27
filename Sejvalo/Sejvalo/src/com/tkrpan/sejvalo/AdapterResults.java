package com.tkrpan.sejvalo;
import java.util.ArrayList;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterResults extends ArrayAdapter {
	
	Activity context;
	
	int layoutID, titleID, dateID, priceID, imageID;
	
	private ArrayList<Ad> list;

	public AdapterResults(Activity context, int layoutID, int titleID, 
			int dateID, int priceID, int imageID, ArrayList<Ad> list) {
		
		super(context, layoutID, list);
		
		this.context = context;
		this.layoutID = layoutID;
		this.titleID = titleID;
		this.dateID = dateID;
		this.priceID = priceID;
		this.imageID = imageID;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = context.getLayoutInflater();
		View row = inflater.inflate(layoutID, null);
		
		TextView title = (TextView)row.findViewById(titleID);
		TextView date = (TextView)row.findViewById(dateID);
		TextView price = (TextView)row.findViewById(priceID);
		
		ImageView image = (ImageView)row.findViewById(imageID);
		
		title.setText(list.get(position).getTitle());
		date.setText(list.get(position).getDate());
		price.setText(list.get(position).getPrice());
		image.setImageDrawable(list.get(position).getImage());
		
		return row;
	}
}
