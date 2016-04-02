package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.protocol.HomeProtocol;
import com.jingtian.market.utils.UIUtil;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Holder for HomeFragment, AppFragment, GameFragment 
 *
 */
public class ListBaseHolder extends BaseHolder<AppInfo> {
	ImageView item_icon;
	TextView item_title,item_size,item_bottom;
	RatingBar item_rating;
	
	@Override
	protected View initView() {
		View contentView=View.inflate(UIUtil.getContext(), R.layout.item_app, null);
		this.item_icon=(ImageView) contentView.findViewById(R.id.item_icon);
		this.item_title=(TextView) contentView.findViewById(R.id.item_title);
		this.item_size=(TextView) contentView.findViewById(R.id.item_size);
		this.item_bottom=(TextView) contentView.findViewById(R.id.item_bottom);
		this.item_rating=(RatingBar) contentView.findViewById(R.id.item_rating);
		return contentView;
	}
	
	public void refreshView(AppInfo info){
		
		this.item_title.setText(info.getName());// set app name
		String size=Formatter.formatFileSize(UIUtil.getContext(), info.getSize());
		this.item_size.setText(size);
		this.item_bottom.setText(info.getDes());
		float stars = info.getStars();
		this.item_rating.setRating(stars); // set ratingBar's value
		//get complete url according to the server side
		String iconUrl = info.getIconUrl();  // eg: http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg
		
		
		//1,which component loads the icon
		//2,icon cache path
		bitmapUtils.display(this.item_icon,HomeProtocol.URL + "image?name=" + iconUrl);//, displayConfig
		// set image controller
//		bitmapUtils.display(holder.item_icon, HttpHelper.URL+"image?name="+iconUrl);
	}

}
