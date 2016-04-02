package com.jingtian.market.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
	public static void removeParent(View v){
		//  find parent, then remove its child
		ViewParent parent = v.getParent();
		// the parent of the component is normally ViewGroup
		if(parent instanceof ViewGroup){
			ViewGroup group=(ViewGroup) parent;
			group.removeView(v);
		}
	}
}
