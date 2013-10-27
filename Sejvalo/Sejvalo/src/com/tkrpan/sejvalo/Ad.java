package com.tkrpan.sejvalo;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Ad implements Serializable {

	private static final long serialVersionUID = -919123438731967529L;
	
	private String title;
	private String date;
	private String price;
	private Drawable image;
	
	private String description;
	private String link;
	
	private String imageUrl;
	
	private Boolean isBoxChecked = false;
	
	int id;
		
	
	public Ad() {
	}
	

	//sa svime
	public Ad(String title, String date, String price, Drawable image,
			String description, String link, String imageUrl,
			Boolean isBoxChecked, int id) {
		super();
		this.title = title;
		this.date = date;
		this.price = price;
		this.image = image;
		this.description = description;
		this.link = link;
		this.imageUrl = imageUrl;
		this.isBoxChecked = isBoxChecked;
		this.id = id;
	}

	
	
	//nema Drawable
	public Ad(int id, String title, String date, String price, String description,
			String link, String imageUrl) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.price = price;
		this.description = description;
		this.link = link;
		this.imageUrl = imageUrl;
	}

	//nema Drawable niti Id
	public Ad(String title, String date, String price, String description,
			String link, String imageUrl) {
		super();
		this.title = title;
		this.date = date;
		this.price = price;
		this.description = description;
		this.link = link;
		this.imageUrl = imageUrl;
	}


	//Bez Id-a
	public Ad(String title, String date, String price, Drawable image,
			String description, String link, String imageUrl) {
		super();
		this.title = title;
		this.date = date;
		this.price = price;
		this.image = image;
		this.description = description;
		this.link = link;
		this.imageUrl = imageUrl;
	}

	//Bez Id-a i image url-a
	public Ad(String title, String date, String price, Drawable image,
			String description, String link) {
		super();
		this.title = title;
		this.date = date;
		this.price = price;
		this.image = image;
		this.description = description;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public Boolean isBoxChecked() {
		return isBoxChecked;
	}


	public void setIsBoxChecked(Boolean isBoxChecked) {
		this.isBoxChecked = isBoxChecked;
	}
	
}
