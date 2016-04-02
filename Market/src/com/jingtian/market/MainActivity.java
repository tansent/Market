package com.jingtian.market;

import com.jingtian.market.fragment.AppFragment;
import com.jingtian.market.fragment.BaseFragment;
import com.jingtian.market.fragment.CategoryFragment;
import com.jingtian.market.fragment.GameFragment;
import com.jingtian.market.fragment.HomeFragment;
import com.jingtian.market.fragment.SubjectFragment;
import com.jingtian.market.fragment.TopFragment;
import com.jingtian.market.fragment.factory.FragmentFactory;
import com.jingtian.market.holder.MenuHolder;
import com.jingtian.market.utils.UIUtil;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;

/*
 * BaseActivity has already realized the onCreate() method so that 
 * we do not need to realize the onCreate() here 
 */
public class MainActivity extends BaseActivity implements OnQueryTextListener{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager mViewPager; //must extends FragmentActivity to use its adapter
	private PagerTabStrip pager_tab_strip;
	private String[] tab_names;
	
	private FrameLayout fl_menu; // side sliding bar
	@Override
	protected void init() {
		tab_names = UIUtil.getStringArray(R.array.tab_names);
	}
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl);
		mViewPager = (ViewPager) findViewById(R.id.vp);
		pager_tab_strip=(PagerTabStrip) findViewById(R.id.pager_tab_strip);
		// set underline color (this attribute can only be added in code)
		pager_tab_strip.setTabIndicatorColor(getResources().getColor(R.color.indicatorcolor));
		
		mViewPager.setAdapter(new MainAdpater(getSupportFragmentManager()));
		
		//to load content from server every time when pager changes
		//note the first page will not change by this method, 
		//so need to be done in HomeFragment manually
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				
				BaseFragment reloadedFragment = FragmentFactory.createFragment(position);
				reloadedFragment.show();
			}
		});
		
		// add side sliding bar
		fl_menu=(FrameLayout) findViewById(R.id.fl_menu);
		MenuHolder holder=new MenuHolder();
		//if the user has logged in before, keep user login status 
		//holder.setData(data) //data from local file or local database
		fl_menu.addView(holder.getContentView()); //use holder to separate code load. Avoid too much code in one class
	}
	
	@Override
	protected void initActionBar() {
		
		ActionBar actionBar = getActionBar();
		//these 2 below are for drawer image
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		//set drawer state listener (connect drawer toggle with the whole layout)
		drawerToggle = new ActionBarDrawerToggle(this,
				mDrawerLayout, R.drawable.ic_drawer_am, R.string.open_drawer,
				R.string.close_drawer){

					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						Toast.makeText(getApplicationContext(), "drawer closed", 0).show();
					}

					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						Toast.makeText(getApplicationContext(), "drawer opened", 0).show();
					}
			
		};
		mDrawerLayout.setDrawerListener(drawerToggle);
		//  build a relationship between actionbar and drawer
		drawerToggle.syncState();
		//------------------------------
	}
	
	/**
	 * menu section control
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
		
		return true;
	}
	
	/** handle actionBar menu event */
	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.action_search) {
//			Toast.makeText(getApplicationContext(), "搜索", 0).show();
//		}

		//trigger drawer first, if drawer does not handle; let parent to handle 
		return drawerToggle.onOptionsItemSelected(item)|super.onOptionsItemSelected(item);
	}
	
	/*for page adapter initialization*/
	private class MainAdpater extends FragmentStatePagerAdapter{
		public MainAdpater(FragmentManager fm) {
			super(fm);
		}
		// 每个条目返回的fragment
		//  0
		@Override
		public Fragment getItem(int position) {
			//  Fragment factory  creates a Fragment according to the position  
			return FragmentFactory.createFragment(position);
		}
		// how many fragment page in total
		@Override
		public int getCount() {
			return tab_names.length;
		}
		// each fragment page's title (show on strip bar)
		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position] ;
		}
	}
	

	//-------query button---------
	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(getApplicationContext(), query, 0).show();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		Toast.makeText(getApplicationContext(), newText, 0).show();
		return false;
	}
	//---------------------------	
	
//	/**
//	 *tab listener 
//	 */
//	private class MyTabListener implements TabListener{
//
//		//tab and pager should listen to each other
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction ft) {
//			mViewPager.setCurrentItem(tab.getPosition());
//		}
//
//		@Override
//		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onTabReselected(Tab tab, FragmentTransaction ft) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
	//----------------
}
