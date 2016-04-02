package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.CategoryInfo;
import com.jingtian.market.utils.UIUtil;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tv;
	@Override
	protected View initView() {
		tv = new TextView(UIUtil.getContext());
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundDrawable(UIUtil.getDrawalbe(R.drawable.grid_item_bg));
		return null;
	}

	@Override
	public void refreshView(CategoryInfo info) {
		tv.setText(info.getTitle());
	}

}
