package com.jingtian.market.utils;

import java.io.File;

import android.os.Environment;

/*
 * use to manage cache path 
 */
public class FileUtils {

	public static final String CACHE = "cache"; //for json file
	public static final String ICON = "icon";	//for images
	public static final String ROOT = "Market";	//root dir
	
	/**
	 * get image cache path
	 */
	public static File getIconDir(){
		return getDir(ICON);
	}
	
	/**
	 * get file cache path
	 */
	public static File getCacheDir() {
		return getDir(CACHE);
	}
	
	public static File getDir(String cache) {
		StringBuilder path = new StringBuilder();
		if (isSDAvailable()) {
			//if the sdcard available, store externally
			path.append(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			path.append(File.separator);// '/'
			path.append(ROOT);// /mnt/sdcard/Market
			path.append(File.separator);
			path.append(cache);// /mnt/sdcard/Market/cache
			
		}else{
			//sdcard not available, store internally
			File filesDir = UIUtil.getContext().getCacheDir();    //  cache  getFileDir file
			path.append(filesDir.getAbsolutePath());// /data/data/com.jingtian.market/cache
			path.append(File.separator);///data/data/com.jingtian.market/cache/
			path.append(cache);///data/data/com.jingtian.market/cache/cache
		}
		File file = new File(path.toString());
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();// create directory
		}
		return file;

	}
	
	private static boolean isSDAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
