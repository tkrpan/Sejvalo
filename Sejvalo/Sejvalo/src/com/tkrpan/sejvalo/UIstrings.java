package com.tkrpan.sejvalo;

public class UIstrings {
	
	public UIstrings(){	
	}
	
	private String titleAlert = "Mrežni pristup";
	private String msgAlert = "Provjeri Internet konekciju!";
	private String buttonNegative = "PROVJERI";
	private String buttonPositive = "Zatvori";
	
	private String toastSearch = "Unesi minimalno 2 znaka!";
	private String toastDelete = "Nema oznaèenih oglasa!";

	public String getTitleAlert() {
		return titleAlert;
	}

	public String getMsgAlert() {
		return msgAlert;
	}

	public String getButtonNegative() {
		return buttonNegative;
	}

	public String getButtonPositive() {
		return buttonPositive;
	}

	public String getToastSearch() {
		return toastSearch;
	}

	public String getToastDelete() {
		return toastDelete;
	}
}
