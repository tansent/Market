package com.jingtian.market.holder;

import java.util.List;

import com.jingtian.market.R;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.utils.UIUtil;

import android.view.View;
import android.widget.ImageView;

public class DetailScreenHolder extends BaseHolder<AppInfo> {
	private ImageView[] ivs; //at most 5 images
	@Override
	protected View initView() {
		View view=UIUtil.inflate(R.layout.detail_screen);
		ivs=new ImageView[5];
		ivs[0]=(ImageView) view.findViewById(R.id.screen_1);
		ivs[1]=(ImageView) view.findViewById(R.id.screen_2);
		ivs[2]=(ImageView) view.findViewById(R.id.screen_3);
		ivs[3]=(ImageView) view.findViewById(R.id.screen_4);
		ivs[4]=(ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		List<String> screen = info.getScreen(); // image set may be less than 5
		for(int i=0;i<5;i++){
			if(i<screen.size()){
				ivs[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(ivs[i], HttpHelper.URL+"image?name="+screen.get(i));
			}else{
				ivs[i].setVisibility(View.GONE);
			}
			
		}
	}

}
