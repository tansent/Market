package com.jingtian.market.fragment;

import java.util.List;

import com.jingtian.market.R;
import com.jingtian.market.domain.AppInfoGson.AppInfo;
import com.jingtian.market.utils.BitmapHelper;
import com.jingtian.market.utils.ViewUtils;
import com.jingtian.market.view.LoadingPage;
import com.jingtian.market.view.LoadingPage.LoadResult;
import com.lidroid.xutils.BitmapUtils;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/*
 * in order to simplify our code and make our code more readable, we can put some code
 * to another LoadingPage class based on the layout
 */
public abstract class BaseFragment extends Fragment {

//	public static final int STATE_UNKOWN = 0; //initial state, loading
//	public static final int STATE_LOADING = 1;//loading
//	public static final int STATE_ERROR = 2;  //result is error
//	public static final int STATE_EMPTY = 3;  //result is null
//	public static final int STATE_SUCCESS = 4;//result is successful
//	public  int state = STATE_UNKOWN;//every pager has their own state, it should not be static
//	
//	private FrameLayout frameLayout; //the default background waits for refill
//	
//	private View loadingView;// while loading
//	private View errorView;// when loading error 
//	private View emptyView;// when loading null
//	private View successView;// when loading successfully
	
//	/**
//	 *state about loading, same as static constants
//	 */
//	public enum LoadResult{
//		error(2),empty(3),success(4);
//		int value;
//		LoadResult(int value){
//			this.value = value;
//		}
//		public int getValue(){
//			return value;
//		}
//	}
	
	private LoadingPage loadingPage;
	protected BitmapUtils bitmapUtils;
	/**
	 * To create and return a fragment
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
		if (loadingPage == null) { // ???
			//create bitmap utils
			bitmapUtils = BitmapHelper.getBitmapUtils();
			//creating, setting views + loading servers
			loadingPage = new LoadingPage(getActivity()){ 

				//whenever invoking LoadingPage's createSuccessView()
				//BaseFragment's createSuccessView() will be invoked
				@Override
				public View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}

				@Override
				public LoadResult loadStateFromServer() {
					return BaseFragment.this.loadStateFromServer();
				}
				
			};
//			init();
		}
		else{ // ??? 
			ViewUtils.removeParent(loadingPage);// remove frameLayout's previous parent
		}
//		show(); 
		
		return loadingPage;
	}
	
	//the only methods we should care in the child class
	/**
	 * create the fragment view when loading successfully from server
	 */
	public abstract View createSuccessView();
	/**
	 * loading from server to get the state result
	 */
	protected abstract LoadResult loadStateFromServer();

	public void show() {
		if (loadingPage!=null) {
			loadingPage.show();
		}
	}
	
	/* get state according to the parse result*/
	public LoadResult checkData(List Infos) { //here should not use T, just a list without type
		if (Infos == null) {
			return LoadResult.error;
		}else {
			if (Infos.size() == 0) {
				return LoadResult.empty;
			}else {
				return LoadResult.success;
			}
		}
	}
//	/*add 4 various states*/
//	private void init() {
//		loadingView = createLoadingView(); // create loading view
//		if (loadingView != null) { //add to the default background
//			frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//		
//		errorView = createErrorView(); // create error view
//		if (errorView != null) {	//if error view occurs, override loading view
//			frameLayout.addView(errorView, new FrameLayout.LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//		
//		emptyView = createEmptyView(); // create loading null view
//		if (emptyView != null) {	//if null view occurs, override background
//			frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
//		
////		successView = createSuccessView(); // create loading success view
////		if (successView != null) {	//if success view occurs, override background
////			frameLayout.addView(successView, new FrameLayout.LayoutParams(
////					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
////		}
//		
//		showPage();// show different view according to various states
//	}
	

//	private View createEmptyView() {
//		View view = View.inflate(getActivity(), R.layout.loadpage_empty, null);
//		return view;
//	}
//
//	private View createErrorView() {
//		View view = View.inflate(getActivity(), R.layout.loadpage_error, null);
//		Button page_bt = (Button) view.findViewById(R.id.page_bt);
//		page_bt.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				show(); //obtain state info from server again
//			}
//		});
//		return view;
//	}
//
//	private View createLoadingView() {
//		View view = View.inflate(getActivity(), R.layout.loadpage_loading, null);
//		return view;
//	}
//	
//	/*show specific frame page according to the state*/
//	private void showPage() {
//		if (loadingView != null) {
//			loadingView.setVisibility(state == STATE_UNKOWN
//					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
//		}
//		if (errorView != null) {
//			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
//					: View.INVISIBLE);
//		}
//		if (emptyView != null) {
//			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
//					: View.INVISIBLE);
//		}
////		if (successView != null) {
////			successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE
////					: View.INVISIBLE);
////		}
//		if (state == STATE_SUCCESS) {
//			successView = createSuccessView(); // create loading success view
//			if (successView != null) {	//if success view occurs, override background
//				frameLayout.addView(successView, new FrameLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//				successView.setVisibility(View.VISIBLE);
//			}
//		} 
//		else {
//			if (successView != null) {
//				successView.setVisibility(View.INVISIBLE);
//			}
//		}
//		//show();
//	}

//	/*change frame page according to the state obtained from server*/
//	public void show() {
//		//loading failed last time, reset state as loading 
//		if (state == STATE_ERROR || state == STATE_EMPTY) {
//			state = STATE_LOADING;
//		}
//		showPage(); //show default page first(loading ) //??deletable??
//		new Thread(){
//			public void run() {
////				SystemClock.sleep(1000); //fake loading
//				//loadStateFromServer() should run in a branch thread
//				final LoadResult result = loadStateFromServer();
//				
//				if (getActivity() != null) {
//					getActivity().runOnUiThread(new Runnable() {
//					public void run() {
//						if (result!=null) {
//						state = result.getValue();
//						//invoke showPage() again to refresh current page when complete loading
//						showPage(); //must run in main thread
//						}
//					  }
//					});
//				}
//			}
//
//		}.start();
//	}
	
//	//the only method we should care in the child class
//	/**
//	 * create the fragment view when loading successfully from server
//	 */
//	public abstract View createSuccessView();
//	/**
//	 * loading from server to get the state result
//	 */
//	public abstract LoadResult loadStateFromServer();
	
}
