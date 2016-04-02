package com.jingtian.market.fragment;

import java.util.List;

import com.jingtian.market.adapter.DefaultAdapter;
import com.jingtian.market.adapter.ListBaseAdapter;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.holder.BaseHolder;
import com.jingtian.market.holder.ListBaseHolder;
import com.jingtian.market.protocol.AppProtocol;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.BaseListView;
import com.jingtian.market.view.LoadingPage.LoadResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AppFragment extends BaseFragment {

	private List<AppInfo> appInfos;

	/**
	 * show page when loading successfully
	 */
	@Override
	public View createSuccessView() {
		BaseListView  listView=new BaseListView(UIUtil.getContext());
		listView.setAdapter(new ListBaseAdapter(appInfos, listView){

			@Override
			protected List<AppInfo> onload() {
				AppProtocol protocol=new AppProtocol();
				List<AppInfo> loadmore = protocol.load(appInfos.size());			
				listInfo.addAll(loadmore);
				return loadmore;
			}
		});
		return listView;
	}
	
//	public class AppAdapter extends DefaultAdapter<AppInfo>{
//
//		public AppAdapter(List<AppInfo> listInfo) {
//			super(listInfo);
//		}
//
//		@Override
//		protected BaseHolder<AppInfo> getHolder() {
//			return new ListBaseHolder();
//		}
//		
//	}
	
	/**
	 * load AppInfo data from server
	 */
	@Override
	public LoadResult loadStateFromServer() {
		AppProtocol protocol=new AppProtocol();
		appInfos = protocol.load(0);
		return checkData(appInfos); // check if loading data success, error, empty
	}
	
}
