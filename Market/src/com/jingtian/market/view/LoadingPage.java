package com.jingtian.market.view;

import com.jingtian.market.BaseApplication;
import com.jingtian.market.R;
import com.jingtian.market.manager.ThreadManager;
import com.jingtian.market.utils.UIUtil;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/*
 * To extract create view part of the fragments from BaseFragment
 * To enhance readability, not efficiency
 */
public abstract class LoadingPage extends FrameLayout {

	public static final int STATE_UNKOWN = 0; //initial state, loading
	public static final int STATE_LOADING = 1;//loading
	public static final int STATE_ERROR = 2;  //result is error
	public static final int STATE_EMPTY = 3;  //result is null
	public static final int STATE_SUCCESS = 4;//result is successful
	public  int state = STATE_UNKOWN;//every pager has their own state, it should not be static
	
//	private FrameLayout frameLayout; //the default background waits for refill
	
	private View loadingView;// while loading
	private View errorView;// when loading error 
	private View emptyView;// when loading null
	private View successView;// when loading successfully
	
	/**
	 *state about loading, same as static constants
	 */
	public enum LoadResult{
		error(2),empty(3),success(4);
		int value;
		LoadResult(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	private void init() {
		loadingView = createLoadingView(); // create loading view
		if (loadingView != null) { //add to the default background
			this.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
		errorView = createErrorView(); // create error view
		if (errorView != null) {	//if error view occurs, override loading view
			this.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
		emptyView = createEmptyView(); // create loading null view
		if (emptyView != null) {	//if null view occurs, override background
			this.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
//		successView = createSuccessView(); // create loading success view
//		if (successView != null) {	//if success view occurs, override background
//			frameLayout.addView(successView, new FrameLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
		
		showPage();// show different view according to various states
	}
	
	private View createEmptyView() {//
		View view = View.inflate(UIUtil.getContext(), R.layout.loadpage_empty, null);
		return view;
	}

	private View createErrorView() {//UIUtil.getContext()
		View view = View.inflate(BaseApplication.getApplication(), R.layout.loadpage_error, null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show(); //obtain state info from server again
			}
		});
		return view;
	}

	private View createLoadingView() {
		View view = View.inflate(UIUtil.getContext(), R.layout.loadpage_loading, null);
		return view;
	}
	
	/*show specific frame page according to the state*/
	private void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
//		if (successView != null) {
//			successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE
//					: View.INVISIBLE);
//		}
		if (state == STATE_SUCCESS) {
			//wrong : judge first, then create
//			successView = createSuccessView(); // create loading success view
//			if (successView != null) {	//if success view occurs, override background
			if (successView == null) {
				successView = createSuccessView();
				this.addView(successView, new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//				successView.setVisibility(View.VISIBLE);
			}
			successView.setVisibility(View.VISIBLE);
		} 
		else {
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
		//show();
	}
	
	/*change frame page according to the state obtained from server*/
	public void show() {
		//loading failed last time, reset state as loading 
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}
		showPage(); //show default page first(loading ) //??deletable??
		new Thread(){
			public void run() {
//				SystemClock.sleep(1000); //fake loading
				//loadStateFromServer() should run in a branch thread
				final LoadResult result = loadStateFromServer();
				
//				if (getActivity() != null) {
					UIUtil.runOnUiThread(new Runnable() {
					public void run() {
						if (result!=null) {
						state = result.getValue();
						//invoke showPage() again to refresh current page when complete loading
						showPage(); //must run in main thread
						}
					  }
					});
//				}
			}

		}.start();
		
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {
			public void run() {
				SystemClock.sleep(1000); //fake loading
				//loadStateFromServer() should run in a branch thread
				final LoadResult result = loadStateFromServer();
				
//				if (getActivity() != null) {
					UIUtil.runOnUiThread(new Runnable() {
					public void run() {
						if (result!=null) {
						state = result.getValue();
						//invoke showPage() again to refresh current page when complete loading
						showPage(); //must run in main thread
						}
					  }
				}); 
			}
		});
	}
	
	//the only methods we should care in the child class
		/**
		 * create the fragment view when loading successfully from server
		 */
		public abstract View createSuccessView();
		/**
		 * loading from server to get the state result
		 */
		public abstract LoadResult loadStateFromServer();

}
