package com.jingtian.market.utils;

import com.lidroid.xutils.BitmapUtils;

/*
 * set image cache path,
 * make BitmapHelper singleton
 */
public class BitmapHelper {

	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils should be singleton
	 * 
	 * @param appContext
	 *        application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils() {
		if (bitmapUtils == null) {
			//1.context
			//2.image cache url (Where the images are stored)
			//3.maximum ratio ram
			bitmapUtils = new BitmapUtils(UIUtil.getContext(), FileUtils
					.getIconDir().getAbsolutePath(), 0.3f);
		}
		return bitmapUtils;
	}
}
