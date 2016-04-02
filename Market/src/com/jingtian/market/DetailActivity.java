package com.jingtian.market;

import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.holder.DetailBottomHolder;
import com.jingtian.market.holder.DetailDesHolder;
import com.jingtian.market.holder.DetailInfoHolder;
import com.jingtian.market.holder.DetailSafeHolder;
import com.jingtian.market.holder.DetailScreenHolder;
import com.jingtian.market.protocol.DetailProtocol;
import com.jingtian.market.utils.UIUtil;
import com.jingtian.market.view.LoadingPage;
import com.jingtian.market.view.LoadingPage.LoadResult;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class DetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo load;

	@Override
	protected void onCreate(Bundle arg0) {
		Intent intent = getIntent();
		packageName = intent.getStringExtra("packageName"); //get the packageName before super(initView() ) 
		
		super.onCreate(arg0);
		
	}
	
	@Override
	protected void init() {
	}
	
	@Override
	protected void initView() {
		LoadingPage loadingPage = new LoadingPage(this) {
			
			@Override
			public LoadResult loadStateFromServer() {
				return DetailActivity.this.load();
			}
			
			@Override
			public View createSuccessView() {
				return DetailActivity.this.createSuccessView();
			}
		};
		loadingPage.show();
		setContentView(loadingPage);
	}
	
	/**
	 * loading data from server
	 * @return
	 */
	protected LoadResult load() {
		DetailProtocol protocol = new DetailProtocol(packageName);
		load = protocol.load(0);
		if (load == null) {
			return LoadResult.error;	
		}else {
			return LoadResult.success;  //invoke createSuccessView()
		}
		
	}
	
	
	private FrameLayout bottom_layout,detail_info,detail_safe,detail_des;
	private HorizontalScrollView detail_screen;
	private DetailInfoHolder detailInfoHolder;
	private DetailScreenHolder screenHolder;
	private DetailSafeHolder safeHolder;
	private DetailDesHolder desHolder;
	private DetailBottomHolder bottomHolder;
	
	/**
	 * successfully loaded view
	 * @return
	 */
	protected View createSuccessView() {
		// set overall framework layout
		View view=UIUtil.inflate(R.layout.activity_detail);
		
		//every part below is a holder
		
		//----Basic App info at the top----
		detail_info=(FrameLayout) view.findViewById(R.id.detail_info);
		detailInfoHolder=new DetailInfoHolder(); //initView()
		detailInfoHolder.setInfo(load);	//refreshView
		detail_info.addView(detailInfoHolder.getContentView()); //add view to frame layout 
		
		//-----safe tag descriptions----
		detail_safe=(FrameLayout) view.findViewById(R.id.detail_safe);
		safeHolder=new DetailSafeHolder();
		safeHolder.setInfo(load);
		detail_safe.addView(safeHolder.getContentView());
				
		//-----images in the middle----
		detail_screen=(HorizontalScrollView) view.findViewById(R.id.detail_screen);
		screenHolder=new DetailScreenHolder();
		screenHolder.setInfo(load);
		detail_screen.addView(screenHolder.getContentView());
				
		//-----Text description below----
		detail_des=(FrameLayout) view.findViewById(R.id.detail_des);
		desHolder= new DetailDesHolder();
		desHolder.setInfo(load);
		detail_des.addView(desHolder.getContentView());
		
		//---bottom---
		bottom_layout=(FrameLayout) view.findViewById(R.id.bottom_layout);
		bottomHolder=new DetailBottomHolder();
		bottomHolder.setInfo(load);;
		bottom_layout.addView(bottomHolder.getContentView());
		
		return view;
	}

	@Override
	protected void initActionBar() {
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); //set incon back image
	}
}


