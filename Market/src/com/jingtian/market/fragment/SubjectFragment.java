package com.jingtian.market.fragment;

import java.util.List;

import com.jingtian.market.R;
import com.jingtian.market.adapter.DefaultAdapter;
import com.jingtian.market.domain.AppInfoGson.AppInfo;
import com.jingtian.market.holder.BaseHolder;
import com.jingtian.market.domain.SubjectInfo;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.protocol.AppProtocol;
import com.jingtian.market.protocol.BaseProtocol;
import com.jingtian.market.protocol.HomeProtocol;
import com.jingtian.market.protocol.SubjectProtocol;
import com.jingtian.market.utils.BitmapHelper;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.BaseListView;
import com.jingtian.market.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;

import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SubjectFragment extends BaseFragment {

	private List<SubjectInfo> list;

	//when loadStateFromServer() successfully
	@Override
	public View createSuccessView() {
		/*
		 *Every View in XML file will be reflected into the java file as code
		 *Layout can be defined in java file as well 
		 */
		BaseListView listView=new BaseListView(UIUtil.getContext());
//		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);  // icon shown while loading
//        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);// icon shown if loading fails
		listView.setAdapter(new SubjectAdapter(list, listView)); 
		return listView;
	}
	
	private class SubjectAdapter extends DefaultAdapter<SubjectInfo>{

		public SubjectAdapter(List<SubjectInfo> list, ListView lv) {
			super(list, lv);
		}

		@Override
		protected BaseHolder<SubjectInfo> getHolder() {
			return new SubjectHolder();
		}

		//load more
		@Override
		protected List<SubjectInfo> onload() {
			SubjectProtocol protocol=new SubjectProtocol();
			List<SubjectInfo> loadmore = protocol.load(listInfo.size());			
			listInfo.addAll(loadmore);
			return loadmore;
		}
		
		@Override
		public void onInnerItemClick(int position) {
			super.onInnerItemClick(position);
			
		}

	}
	
	class SubjectHolder extends BaseHolder<SubjectInfo>{
		ImageView item_icon;
		TextView item_txt;
		@Override
		protected View initView() {
			View contentView=UIUtil.inflate(R.layout.item_subject);
			this.item_icon=(ImageView) contentView.findViewById(R.id.item_icon);
			this.item_txt=(TextView) contentView.findViewById(R.id.item_txt);
			return contentView;
		}

		@Override
		public void refreshView(SubjectInfo info) {
			this.item_txt.setText(info.getDes());
			bitmapUtils.display(this.item_icon, BaseProtocol.URL+"image?name="+info.getUrl());
		}
		
		
	}

	@Override
	public LoadResult loadStateFromServer() {
		SubjectProtocol subjectProtocol = new SubjectProtocol();
		list = subjectProtocol.load(0);
		return checkData(list);
	}
	
}
