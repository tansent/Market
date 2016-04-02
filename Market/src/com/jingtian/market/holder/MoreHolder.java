package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.adapter.DefaultAdapter;
import com.jingtian.market.utils.UIUtil;

import android.view.View;
import android.widget.RelativeLayout;

public class MoreHolder extends BaseHolder<Integer> {
	public static final int HAS_NO_MORE=0;  // no extra data
	public static final int LOAD_ERROR=1;// load fail
	public static final int HAS_MORE=2;//  extra data
	
	private boolean hasMore;
	
	private RelativeLayout rl_more_loading,rl_more_error; //2 fragment from load_more layout
	
	private DefaultAdapter adapter;
	public MoreHolder(DefaultAdapter adapter, boolean hasMore) {
		super();
		this.adapter=adapter; //obtain adapter, it has listInfo set
		this.hasMore = hasMore;
		if (!hasMore) {
			setInfo(HAS_NO_MORE);
		}
	}
	
	/**
	 * show UI
	 * @return
	 */
	@Override
	protected View initView() {
		View view = UIUtil.inflate(R.layout.load_more);
		rl_more_loading=(RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rl_more_error=(RelativeLayout) view.findViewById(R.id.rl_more_error);
		return view;
	}
	
	/** if MoreHolder object invokes getContentView() method, it should load more data*/
	@Override
	public View getContentView() {
		if (hasMore) {
			loadMore();
		}
		
		return super.getContentView();
	}
	
	/**
	 * load more data from server
	 */
	private void loadMore() {
		//  request server to load more data
		//  let Adapter  load more data and append those data to its listInfo list
		adapter.loadMore();
	}

	@Override
	public void refreshView(Integer info) {
//		rl_more_error.setVisibility(info==LOAD_ERROR?View.VISIBLE:View.GONE);
		rl_more_error.setVisibility(info!=HAS_MORE?View.VISIBLE:View.GONE);
		rl_more_loading.setVisibility(info==HAS_MORE?View.VISIBLE:View.GONE);
	}
	
}
