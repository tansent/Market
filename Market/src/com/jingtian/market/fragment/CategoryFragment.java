package com.jingtian.market.fragment;


import java.util.List;

import com.jingtian.market.adapter.DefaultAdapter;
import com.jingtian.market.domain.CategoryInfo;
import com.jingtian.market.holder.BaseHolder;
import com.jingtian.market.holder.CategoryContentHolder;
import com.jingtian.market.holder.CategoryTitleHolder;
import com.jingtian.market.protocol.CategoryProtocol;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.BaseListView;
import com.jingtian.market.view.LoadingPage.LoadResult;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment {

//	public static int ITEM_TITLE = 2; //here must be 2
	
	private List<CategoryInfo> infos;
	
	@Override
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtil.getContext()); 
		listView.setAdapter(new CategoryAdapter(infos, listView));
		return listView;
	}

	
	@Override
	public LoadResult loadStateFromServer() {
		CategoryProtocol protocol = new CategoryProtocol();
		infos = protocol.load(0);
		return checkData(infos);
	}
	
	 class CategoryAdapter extends DefaultAdapter<CategoryInfo>{
//		private int position;// record current position
		 
		public CategoryAdapter(List<CategoryInfo> listInfo, ListView lv) {
			super(listInfo, lv);
		}
		
		@Override
		protected BaseHolder<CategoryInfo> getHolder() {
			return new CategoryContentHolder();
		}
		
//		@Override
//		protected BaseHolder<CategoryInfo> getHolder() {
//			if (!infos.get(position).isTitle()) {
//				return new CategoryContentHolder();
//			}else{
//				return new CategoryTitleHolder();
//			}
//		}

		//for taking record of current position in order to decide item type
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			this.position = position;
			return super.getView(position, convertView, parent);
		}

		@Override
		protected List<CategoryInfo> onload() { 
			//because hasMore() returns false, the LoadMore holder will not show
			//we do not to implement this function
			return null;
		}
		 
		@Override
		protected boolean hasMore() {
			return false; //no more data for Category Fragment
		}

//		@Override
//		public int getViewTypeCount() {
//			return super.getViewTypeCount() + 1; // one more type for title bar
//		}

//		@Override
//		protected int getInnerItemViewType(int position) {
//			if (infos.get(position).isTitle()) {
//				return ITEM_TITLE;
//			} else {
//				return super.getInnerItemViewType(position);
//			}
//		}
		
		
	 }
	
}

//public class CategoryFragment extends BaseFragment {
//	private List<CategoryInfo> datas;
//	public static int ITEM_TITLE =2;
//
//	// 创建成功的界面
//	@Override
//	public View createSuccessView() {
//		BaseListView listView = new BaseListView(UIUtil.getContext());
//		listView.setAdapter(new CategoryAdapter(datas, listView));
//
//		return listView;
//	}
//
//	private class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
//		private int position;// 当前条目位置记录
//
//		public CategoryAdapter(List<CategoryInfo> datas, ListView lv) {
//			super(datas, lv);
//		}
//
//		// 实现每个条目的界面
//		@Override
//		protected BaseHolder<CategoryInfo> getHolder() {
//			if (!datas.get(position).isTitle()) {
//				return new CategoryContentHolder();
//			}else{
//				return new CategoryTitleHolder();
//			}
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			this.position = position;
//			return super.getView(position, convertView, parent);
//		}
//
//		@Override
//		protected boolean hasMore() { // 当前方法 如果为false onload就不会被调用了
//			return false;
//		}
//
//		@Override
//		protected int getInnerItemViewType(int position) {
//			if (datas.get(position).isTitle()) {
//				return ITEM_TITLE;
//			} else {
//				return super.getInnerItemViewType(position);
//			}
//		}
//
//		@Override
//		protected List<CategoryInfo> onload() {
//			return null;
//		}
//		//  集合 管理三个convertView   
//		@Override
//		public int getViewTypeCount() {
//			return super.getViewTypeCount() + 1; // 又额外多了一种条目类型  现在又三种  1 标题 2 内容 3 加载更多(没有显示)
//		}
//
//	}
//
//	// 请求服务器
//	@Override
//	protected LoadResult loadStateFromServer() {
//		CategoryProtocol protocol = new CategoryProtocol();
//		datas = protocol.load(0);
//		return checkData(datas);
//	}
//
//}
