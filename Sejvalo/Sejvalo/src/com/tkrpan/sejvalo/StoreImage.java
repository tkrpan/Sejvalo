package com.tkrpan.sejvalo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;

import android.os.Environment;


public class StoreImage {

	//vraca true ako je read only 
	public static boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}

	//vraca true ako je read writable 
	public static boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    return Environment.MEDIA_MOUNTED.equals(state);
	}
	
	// return true ako je available i writable, 
	public static boolean isExternalStorageAvailable() {
	    if (!isExternalStorageWritable()) return false;
	    return true;
	}
	
	// return the File from the given filename.
	public static File getImageFile(String filename) {
	    if (!isExternalStorageReadable()) return null;
	    // The images folder path.
	    String imagesFolder = Environment.getExternalStorageDirectory().getPath()
	                          + "Android/data/com.tkrpan.savesearch/images/";
	    // Creating the file.
	    File file = new File(imagesFolder + filename);        
	    return file;
	}

	/**
	 * Write the contents of the HTTP entity to the external 
	 * storage if available and writable.
	 */
	public static boolean storeImage(HttpEntity entity, String filename) throws IOException     {
	    if (isExternalStorageAvailable()) {
	        File file = getImageFile(filename);
	        if (file == null) return false;
	        // Write to file output stream.
	        FileOutputStream os = new FileOutputStream(file);
	        entity.writeTo(os);
	        os.close();
	        return true;
	    }
	    return false;
	}
	
}
