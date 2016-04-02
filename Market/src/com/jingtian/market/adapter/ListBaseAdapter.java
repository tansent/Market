package com.jingtian.market.adapter;

import java.util.List;

import com.jingtian.market.DetailActivity;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.holder.BaseHolder;
import com.jingtian.market.holder.ListBaseHolder;
import com.jingtian.market.utils.UIUtil;

import android.content.Intent;
import android.widget.ListView;

/**
 * for replacing/extracting HomeAdapter, AppAdapter, GameAdapter 
 *
 */
public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> {

	public ListBaseAdapter(List<AppInfo> listInfo, ListView lv) {
		super(listInfo, lv);
	}

	@Override
	protected BaseHolder<AppInfo> getHolder() {
		return new ListBaseHolder();
	}

	/**
	 * load extra data in each fragment
	 */
	@Override
	protected abstract List<AppInfo> onload();
	
	
	public void onInnerItemClick(int position) {
		super.onInnerItemClick(position);
		AppInfo appInfo = listInfo.get(position);
		Intent intent=new Intent(UIUtil.getContext(), DetailActivity.class);
		intent.putExtra("packageName", appInfo.getPackageName());
		UIUtil.startActivity(intent);
	}
}
