package com.jingtian.market.fragment;

import java.util.List;
import java.util.Random;

import com.jingtian.market.R;
import com.jingtian.market.protocol.TopProtocol;
import com.jingtian.market.utils.DrawableUtils;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.Flowlayout;
import com.jingtian.market.view.LoadingPage.LoadResult;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TopFragment extends BaseFragment {

	private List<String> infos;
	
	@Override
	public View createSuccessView() {
		ScrollView scrollView=new ScrollView(UIUtil.getContext());
		scrollView.setBackgroundResource(R.drawable.grid_item_bg_normal);
		Flowlayout layout=new Flowlayout(UIUtil.getContext());

		int padding=UIUtil.dip2px(13);
		layout.setPadding(padding, padding, padding, padding);
		int backColor = 0xffcecece;
		Drawable pressedDrawable=DrawableUtils.createShape(backColor);// create pressed background color
		for(int i=0;i<infos.size();i++){
			TextView textView=new TextView(UIUtil.getContext());
			final String str=infos.get(i);
			textView.setText(str);
		
			Random random=new Random();   //random color
			int red = random.nextInt(200)+22;    //to filter black and white color
			int green = random.nextInt(200)+22;  
			int blue = random.nextInt(200)+22;     
			int color=Color.rgb(red, green, blue);//range: 0-255 
			 // create default background image
			GradientDrawable createShape = DrawableUtils.createShape(color);
			// create pressed background selector
			StateListDrawable createSelectorDrawable = DrawableUtils.createSelectorDrawable(pressedDrawable, createShape);
			//set background image
			textView.setBackgroundDrawable(createSelectorDrawable);
			textView.setTextColor(Color.WHITE);
			//textView.setTextSize(UiUtils.dip2px(14));
			int textPaddingV = UIUtil.dip2px(4);
			int textPaddingH = UIUtil.dip2px(7);
			textView.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV); //set textView padding 
			textView.setClickable(true);//make textView clickable
			textView.setOnClickListener(new OnClickListener() {  // set click listener (only set clickListener can the selector works)
				
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtil.getContext(), str, 0).show();
				}
			});
			layout.addView(textView,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, -2));// -2 == WRAP_CONTENT
		}
		
		scrollView.addView(layout);
		return scrollView;
	}

	@Override
	public LoadResult loadStateFromServer() {
		TopProtocol protocol=new TopProtocol();
		infos = protocol.load(0);
		return checkData(infos);
	}
	
}
