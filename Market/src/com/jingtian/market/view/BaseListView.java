package com.jingtian.market.view;


import com.jingtian.market.R;
import com.jingtian.market.utils.UIUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*
 * To replace ListView: eliminate background color 
 */
public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseListView(Context context) {
		super(context);
		init();
	}

	private void init() {
//		setSelector  background color while clicking
//		setCacheColorHint  color while dragging
//		setDivider  divider of each item
		this.setSelector(R.drawable.nothing);  
		this.setCacheColorHint(R.drawable.nothing);
		this.setDivider(UIUtil.getDrawalbe(R.drawable.nothing));
	}

	
}
