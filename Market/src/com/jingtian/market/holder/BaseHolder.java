package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.SubjectInfo;
import com.jingtian.market.protocol.BaseProtocol;
import com.jingtian.market.utils.BitmapHelper;
import com.jingtian.market.utils.UIUtil;
import com.lidroid.xutils.BitmapUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * extract common part for each holder
 * @param <T>
 */
public abstract class BaseHolder <T>{

	View contentView; //convertView
	T info;
	protected BitmapUtils bitmapUtils;
	public BaseHolder(){
		bitmapUtils = BitmapHelper.getBitmapUtils();
		contentView = initView();
		contentView.setTag(this);
	}

	/**
	 * every holder has different initView()
	 */
	protected abstract View initView();
	
	public View getContentView() {
		return contentView;
	}

	/**
	 * after set, you should invoke refreshView(info) 
	 * @param info
	 */
	public void setInfo(T info) {
		this.info = info;
		refreshView(info);
	}
	
	/**
	 * every holder has different refresh view function;
	 * change the UI according to the data
	 */
	public abstract void refreshView(T info);
}
