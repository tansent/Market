package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.CategoryInfo;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.utils.UIUtil;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryContentHolder extends BaseHolder <CategoryInfo>{
	ImageView [] ivs;
	TextView [] tvs;
	@Override
	protected View initView() {
		View view=UIUtil.inflate(R.layout.item_category_content);
		ivs=new ImageView[3];
		ivs[0]=(ImageView) view.findViewById(R.id.iv_1);
		ivs[1]=(ImageView) view.findViewById(R.id.iv_2);
		ivs[2]=(ImageView) view.findViewById(R.id.iv_3);
		
		tvs=new TextView[3];
		tvs[0]=(TextView) view.findViewById(R.id.tv_1);
		tvs[1]=(TextView) view.findViewById(R.id.tv_2);
		tvs[2]=(TextView) view.findViewById(R.id.tv_3);
		
		return view;
	}

	@Override
	public void refreshView(CategoryInfo info) {
		// first block 
		if(!TextUtils.isEmpty(info.getName1())&&!TextUtils.isEmpty(info.getUrl1())){
			tvs[0].setText(info.getName1());
			bitmapUtils.display(ivs[0], HttpHelper.URL+"image?name="+info.getUrl1());
			tvs[0].setVisibility(View.VISIBLE);
			ivs[0].setVisibility(View.VISIBLE);
		}else{
			tvs[0].setVisibility(View.INVISIBLE);
			ivs[0].setVisibility(View.INVISIBLE);
		}
		// second block 
		if(!TextUtils.isEmpty(info.getName2())&&!TextUtils.isEmpty(info.getUrl2())){
			tvs[1].setText(info.getName2());
			bitmapUtils.display(ivs[1], HttpHelper.URL+"image?name="+info.getUrl2());
			tvs[1].setVisibility(View.VISIBLE);
			ivs[1].setVisibility(View.VISIBLE);
		}else{
			tvs[1].setVisibility(View.INVISIBLE);
			ivs[1].setVisibility(View.INVISIBLE);
		}
		// third block 
		if(!TextUtils.isEmpty(info.getName3())&&!TextUtils.isEmpty(info.getUrl3())){
			tvs[2].setText(info.getName3());
			bitmapUtils.display(ivs[2], HttpHelper.URL+"image?name="+info.getUrl3());
			tvs[2].setVisibility(View.VISIBLE);
			ivs[2].setVisibility(View.VISIBLE);
		}else{
			tvs[2].setVisibility(View.INVISIBLE);
			ivs[2].setVisibility(View.INVISIBLE);
		}
	}

}
