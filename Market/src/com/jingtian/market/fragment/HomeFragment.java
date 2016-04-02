package com.jingtian.market.fragment;

import java.util.List;

import com.jingtian.market.DetailActivity;
import com.jingtian.market.R;
import com.jingtian.market.adapter.ListBaseAdapter;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.holder.HomePictureHolder;
import com.jingtian.market.protocol.HomeProtocol;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.BaseListView;
import com.jingtian.market.view.LoadingPage.LoadResult;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
 * first, we can add functions in the class, and then, we extract the common functions
 * to another common father class
 */
public class HomeFragment extends BaseFragment {

//	public static final int STATE_UNKOWN = 0;
//	public static final int STATE_LOADING = 1;
//	public static final int STATE_ERROR = 2;
//	public static final int STATE_EMPTY = 3;
//	public static final int STATE_SUCCESS = 4;
//	public static int state = STATE_UNKOWN;
//	
//	private FrameLayout frameLayout; //the default background waits for refill
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
	
	private List<AppInfo> listInfo;
    private List<String> pictures; //for viewPager
//	private List<AppInfo> appInfos;
	


	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			show();
		}
//	/**
//	 * To create and return a fragment
//	 */
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//		if (frameLayout == null) { // ???
//			frameLayout = new FrameLayout(getActivity());
//			init();
//		}
//		else{ // ??? 
//			ViewUtils.removeParent(frameLayout);// remove frameLayout's previous parent
//		}
//		show();
//		
//		return frameLayout;
//	}
//
//	private View loadingView;// while loading
//	private View errorView;// when loading error 
//	private View emptyView;// when loading null
//	private View successView;// when loading successfully
	
	
	
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
//		successView = createSuccessView(); // create loading success view
//		if (successView != null) {	//if success view occurs, override background
//			frameLayout.addView(successView, new FrameLayout.LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		}
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
	@Override
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UIUtil.getContext());
		
		HomePictureHolder holder = new HomePictureHolder(); //initView()
		holder.setInfo(pictures);	//refreshView(List<String> info)
		View contentView = holder.getContentView(); //get the view object inside the holder
//		contentView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		listView.addHeaderView(contentView);
		
		listView.setAdapter(new ListBaseAdapter(listInfo, listView){

			@Override
			protected List<AppInfo> onload() {
				HomeProtocol homeProtocol = new HomeProtocol();
				List<AppInfo> loadMore = homeProtocol.load(listInfo.size());
				listInfo.addAll(loadMore);
				return loadMore;
			}

			//move onInnerItemClick(int position) to ListBaseAdapter 
//			@Override
//			public void onInnerItemClick(int position) {
//				super.onInnerItemClick(position);
//				AppInfo appInfo = listInfo.get(position);
//				Intent intent=new Intent(UIUtil.getContext(), DetailActivity.class);
//				intent.putExtra("packageName", appInfo.getPackageName());
//				startActivity(intent); //system's method
//			}
			
		});
		
		
		// 2.  loading image while scroll slowly. false-load   true-not load
		// 3.  loading image while fling.  false-load   true-not load
		// here means: loading image only when scroll slowly, not while fling
		listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);  // icon shown while loading
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);// icon shown if loading fails
        
		return listView;
	}
	
//	private class HomeAdapter extends DefaultAdapter <AppInfo>{ //after extend class is where you use generic type
//
//		public HomeAdapter(List<AppInfo> listInfo){
//			super(listInfo);
//		}
//
//		@Override
//		protected BaseHolder<AppInfo> getHolder() {
//			return new ListBaseHolder();
//		}
//		
//	}
	
//	static class HomeHolder extends BaseHolder<AppInfo>{
//		ImageView item_icon;
//		TextView item_title,item_size,item_bottom;
//		RatingBar item_rating;
//		
//		@Override
//		protected View initView() {
//			View contentView=View.inflate(UIUtil.getContext(), R.layout.item_app, null);
//			this.item_icon=(ImageView) contentView.findViewById(R.id.item_icon);
//			this.item_title=(TextView) contentView.findViewById(R.id.item_title);
//			this.item_size=(TextView) contentView.findViewById(R.id.item_size);
//			this.item_bottom=(TextView) contentView.findViewById(R.id.item_bottom);
//			this.item_rating=(RatingBar) contentView.findViewById(R.id.item_rating);
//			return contentView;
//		}
//		
//		public void refreshView(AppInfo info){
//			
//			this.item_title.setText(info.getName());// set app name
//			String size=Formatter.formatFileSize(UIUtil.getContext(), info.getSize());
//			this.item_size.setText(size);
//			this.item_bottom.setText(info.getDes());
//			float stars = info.getStars();
//			this.item_rating.setRating(stars); // set ratingBar's value
//			//get complete url according to the server side
//			String iconUrl = info.getIconUrl();  // eg: http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg
//			
//			
//			//1,which component loads the icon
//			//2,icon cache path
//			bitmapUtils.display(this.item_icon,HomeProtocol.URL + "image?name=" + iconUrl);//, displayConfig
//			// set image controller
////			bitmapUtils.display(holder.item_icon, HttpHelper.URL+"image?name="+iconUrl);
//		}
//
//	}

		
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
//		if (successView != null) {
//			successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE
//					: View.INVISIBLE);
//		}
//	}

//	/*change frame page according to the state obtained from server*/
//	private void show() {
//		//loading failed last time, reset state as loading 
//		if (state == STATE_ERROR || state == STATE_EMPTY) {
//			state = STATE_LOADING;
//		}
//		showPage(); //show default page first(loading )
//		new Thread(){
//			public void run() {
//				SystemClock.sleep(1000);
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
//			};
//		}.start();
//	}

	@Override
	public LoadResult loadStateFromServer() {
//		//to get and parse json file from server 
//		HomeProtocol homeProtocol = new HomeProtocol();
//		appInfos = homeProtocol.load(0);
//		return checkData(appInfos);
		
		//to get and parse json file from server using gson
		//save json to cache
		HomeProtocol homeProtocol = new HomeProtocol();
		listInfo = homeProtocol.load(0);
		pictures = homeProtocol.getPictures();
		return checkData(listInfo);
	}
	
	
	
	
	
	
}
