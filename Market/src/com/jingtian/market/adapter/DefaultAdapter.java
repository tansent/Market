package com.jingtian.market.adapter;

import java.util.List;

import com.jingtian.market.domain.AppInfoGson.AppInfo;
import com.jingtian.market.holder.BaseHolder;
import com.jingtian.market.holder.ListBaseHolder;
import com.jingtian.market.holder.MoreHolder;
import com.jingtian.market.manager.ThreadManager;
import com.jingtian.market.utils.UIUtil;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * extract common part for each adapter
 * (method: if there are two similar functions, specify one function first.)
 */
public abstract class DefaultAdapter<Data> extends BaseAdapter implements OnItemClickListener{

	protected List<Data> listInfo;
	private ListView lv;
	
	public static final int DEFAULT_ITEM = 0;
	public static final int MORE_ITEM = 1;
	
	//position: latest shown visible item on the screen
	//convertView: old holder (based on tag)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder = null;
		
//		//set view
//		if(convertView==null){ //create new item
//			if (getItemViewType(position) == DEFAULT_ITEM) {
//				holder = getHolder(); 
//			} else if(getItemViewType(position) == MORE_ITEM){
//				holder = showMoreHolder();
//			}
//		}else{ //reuse item
//			if (getItemViewType(position) == DEFAULT_ITEM) {
//				holder=(BaseHolder) convertView.getTag(); //convertView means "old view". It stores a "tag" already 
//			}else {
//				//holder=showMoreHolder(); //(create a new more_holder, not reuse)
//				holder=(BaseHolder) convertView.getTag();
//			}
//		}
//		
//		//set data
//		if (getItemViewType(position) == DEFAULT_ITEM) { //only default data item should be set data
//			//Data is a generic type
//			Data appInfo=listInfo.get(position);
//			holder.setInfo(appInfo);
//		}
//		
//		return holder.getContentView(); //if this holder is MoreHolder, we should load more data
		
		
		//----------------------
		switch (getItemViewType(position)) {  // judge the type of the item
		case MORE_ITEM:
			if(convertView==null){
				holder=showMoreHolder();
			}else{
				holder=(BaseHolder) convertView.getTag();
			}
			break;
		default: //all types but MORE_ITEM item should be set data
			//set view
			if (convertView == null) {
				holder = getHolder();
			} else {
				holder = (BaseHolder) convertView.getTag();
			}
			//set data
			if (position < listInfo.size()) {
				holder.setInfo(listInfo.get(position));
			}
			break;
		}
		
		return holder.getContentView();
		//-----------------------
		
	}
	
	private MoreHolder moreHolder; //for recording
	private BaseHolder showMoreHolder() {
		if (moreHolder != null) {
			return moreHolder;
		} else {
			moreHolder = new MoreHolder(this, hasMore());
			return moreHolder;
		}
//		return moreHolder;

	}

	/**
	 * whether the fragment has more data to load at the bottom.
	 * By default, return true
	 * @return
	 */
	protected boolean hasMore() {
		return true;
	}

	/**
	 * every holder is different, as long as the parent class cannot do, let child class do.
	 * To realize every item view
	 * @return
	 */
	protected abstract BaseHolder<Data> getHolder();

	/*
	 * use constructor to transfer data
	 */
	public DefaultAdapter(List<Data> listInfo, ListView lv) {
		this.listInfo = listInfo;
		lv.setOnItemClickListener(this); //invoke onItemClick() function
		this.lv = lv;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		position=position-lv.getHeaderViewsCount();// total item quantity - headers
		onInnerItemClick(position);
	}
	
	/**on item click for every item*/
	public void onInnerItemClick(int position) {
		
	}

	@Override
	public int getCount() {
		return listInfo.size() + 1; //  + 1 for load_more (more holder)
	}

	@Override
	public Object getItem(int position) {
		return listInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<Data> getListInfo() {
		return listInfo;
	}

	public void setListInfo(List<Data> listInfo) {
		this.listInfo = listInfo;
	}

	/**
	 * which position reuses which type of item
	 */
	@Override
	public int getItemViewType(int position) {
		if (position == listInfo.size()) {
			return MORE_ITEM;
		}
		return getInnerItemViewType(position);
	}
	
	protected int getInnerItemViewType(int position) {
		return DEFAULT_ITEM;
	}

	/**
	 * how many types of items can the listView store
	 */
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1; //able to reuse 2 types of item
	}

	/**
	 * when loading more data from server
	 */
	public void loadMore() {
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {
			@Override
			public void run() {
				//every fragment has different requesting address, so does different onload() method
				final List<Data> newData = onload();
				
				UIUtil.runOnUiThread(new Runnable() {
					public void run() {
						if (newData == null) { //loading data fail
							moreHolder.setInfo(MoreHolder.LOAD_ERROR);  //setInfo will also refresh UI
						}else if (newData.size() == 0) { //no more data
							moreHolder.setInfo(MoreHolder.HAS_NO_MORE);
						}else { //loaded more data
							moreHolder.setInfo(MoreHolder.HAS_MORE);
							listInfo.addAll(newData);
							notifyDataSetChanged();
						}
						
					}
				});
				
			}
		});
		
	}

	//every fragment has different requesting address, so does different onload() method
	protected abstract List<Data> onload(); //loading data from server
	
}
