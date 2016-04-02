package com.jingtian.market.fragment;


import java.util.List;

import com.jingtian.market.adapter.ListBaseAdapter;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.protocol.GameProtocol;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.BaseListView;
import com.jingtian.market.view.LoadingPage.LoadResult;

import android.view.View;

public class GameFragment extends BaseFragment {

	private List<AppInfo> infos;

	@Override
	public View createSuccessView() {
		BaseListView  listView=new BaseListView(UIUtil.getContext());
		listView.setAdapter(new ListBaseAdapter(infos, listView){

			@Override
			protected List<AppInfo> onload() {
				GameProtocol protocol = new GameProtocol();
				List<AppInfo> loadMore = protocol.load(infos.size()); //0,1,2
				listInfo.addAll(loadMore);
				return loadMore;
			}
			
		});
		return listView;
	}

	@Override
	public LoadResult loadStateFromServer() {
		GameProtocol protocol = new GameProtocol();
		infos = protocol.load(0);
		return checkData(infos);
	}
	
}
