package com.tkrpan.sejvalo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "adMenager";
	private static final String TABLE_ADS = "ads";
	
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DATE = "date";
	private static final String KEY_PRICE = "price";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_LINK = "link";
	private static final String KEY_IMAGE_URL = "imageURL";

	private static final String TEXT_TYPE = " TEXT,";
			
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
	public void onCreate(SQLiteDatabase dp) {
		String CREATE_ADS_TABLE = "CREATE TABLE " + TABLE_ADS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + TEXT_TYPE
				+ KEY_DATE + TEXT_TYPE + KEY_PRICE + TEXT_TYPE 
				+ KEY_DESCRIPTION + TEXT_TYPE + KEY_LINK + TEXT_TYPE 
				+ KEY_IMAGE_URL + " TEXT" + ")";
		dp.execSQL(CREATE_ADS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADS);
		//ponovno kreiraj tablicu
		onCreate(db);
	}
	//Dodaj novi oglas
	public void addAd(Ad ad){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, ad.getTitle());
		values.put(KEY_DATE, ad.getDate());
		values.put(KEY_PRICE, ad.getPrice());
		values.put(KEY_DESCRIPTION, ad.getDescription());
		values.put(KEY_LINK, ad.getLink());
		values.put(KEY_IMAGE_URL, ad.getImageUrl());

		db.insert(TABLE_ADS, null, values);
	}
	//Dohvaæanje pojedinaènog oglasa
	public Ad getAd(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_ADS, new String[] {KEY_ID, KEY_NAME, KEY_DATE, 
				KEY_PRICE, KEY_DESCRIPTION, KEY_LINK, KEY_IMAGE_URL}, KEY_ID + "?", 
				new String[] {String.valueOf(id)}, null, null, null, null);
		
		if (cursor != null)
			cursor.moveToFirst();
		
		Ad ad = new Ad(Integer.parseInt(cursor.getString(0)), cursor.getString(1), 
				cursor.getString(2), cursor.getString(3), cursor.getString(4), 
				cursor.getString(5), cursor.getString(6));
		
		return ad;
	}
	//Dohvati sve oglase
	public ArrayList<Ad> getAllAds() {
		ArrayList<Ad> adList = new ArrayList<Ad>();
		//Selektiranje svih upita
		String selectQuery = "SELECT * FROM " + TABLE_ADS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				Ad ad = new Ad();
				ad.setId(Integer.parseInt(cursor.getString(0)));
				ad.setTitle(cursor.getString(1));
				ad.setDate(cursor.getString(2));
				ad.setPrice(cursor.getString(3));
				ad.setDescription(cursor.getString(4));
				ad.setLink(cursor.getString(5));
				ad.setImageUrl(cursor.getString(6));
				adList.add(ad);
			}while (cursor.moveToNext());
		}
		
		return adList;
		}
	
	// Brisanje oglasa
	public void deleteAd(Ad ad) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ADS, KEY_ID + " =?", 
				new String []{String.valueOf(ad.getId())});
		db.close();
	}
	
	public void deleteMultipleAd(ArrayList<Ad> adList) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		for(int i=0;i<adList.size();i++){
		if(adList.get(i).isBoxChecked()){
		db.delete(TABLE_ADS, KEY_ID + " =?",
		new String []{String.valueOf(adList.get(i).getId())});
		}
		}
		db.close();
		}
	
	public void deleteAllAd() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ADS, null, null);
		db.close();  
	}
	
	/*public void deleteRecipe(Recipe recipe){
		open();
	    db.delete(TABLE_RECIPES, C_ID + " = ?",
	            new String[] { String.valueOf(recipe.getId())});
	    close();
		File file = new File(Environment.getExternalStorageDirectory()+ "/MakeMeFood/Images",
				recipe.getId() + ".jpeg");
		file.delete();
	}
	
	public void deleteAllRecipes() {
		open();
		db.delete(TABLE_RECIPES, null, null);
		close();
		File dir = new File(Environment.getExternalStorageDirectory()+ "/MakeMeFood/Images");
		deleteDirectory(dir);
	}*/
}
