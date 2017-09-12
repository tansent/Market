# Market


A holder-oriented app, super easy to maintain and expand:

The most noteworthy technique in this app is that I have separated and decoupled this app to a great degree. Running the app you can see many pages, some has customized views inside, some is a relatively complicated introduction and downloading fragment. Yet, in the code, there is no massive class with all the adapters, holders, downloading and JSON parsing code jammed all together . I have used interface, abstracted methods, generic type to decouple so that if there is any need to expand the app in the future, it can be done in an instant.

Other features in this app include: ActionBar, Searching, Drawer, Value Animation, Customized views and components.

This app needs a server(some servlet program) and data to show all the contents. But the code I uploaded is enough to demostrates insights. Holders, Listviews, ViewPagers, Adapters, Protocols(for downloading and parsing Json) are all separated.



<p>
<p>
<p>
There is a snapshot to show a game page in this app. Here is the code of this page fragment:
Only 2 functions inside for such a complicated page. That's the power of decoupling!


/*****************************************************************************
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

/*****************************************************************************
