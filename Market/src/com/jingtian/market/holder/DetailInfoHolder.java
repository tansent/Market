package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.utils.UIUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailInfoHolder extends BaseHolder<AppInfo> {

	@ViewInject(R.id.item_icon)
	private ImageView item_icon;
	@ViewInject(R.id.item_title)
	private TextView item_title;
	@ViewInject(R.id.item_rating)
	private RatingBar item_rating;
	@ViewInject(R.id.item_download)
	private TextView item_download;
	@ViewInject(R.id.item_version)
	private TextView item_version;
	@ViewInject(R.id.item_date)
	private TextView item_date;
	@ViewInject(R.id.item_size)
	private TextView item_size;

	/**
	 * initiate layout the components
	 */
	@Override
	protected View initView() {
		View view=UIUtil.inflate(R.layout.detail_app_info);
		ViewUtils.inject(this, view);
		return view;
	}

	/**
	 * set layout and components' data
	 */
	@Override
	public void refreshView(AppInfo info) {
		bitmapUtils.display(item_icon, HttpHelper.URL+"image?name="+info.getIconUrl());
		item_title.setText(info.getName());
		item_rating.setRating(info.getStars());
		item_download.setText("下载:"+info.getDownloadNum());
		item_version.setText("版本:"+info.getVersion());
		item_date.setText("时间:"+info.getDate());
		item_size.setText("大小:"+Formatter.formatFileSize(UIUtil.getContext(), info.getSize()));
	}

}
