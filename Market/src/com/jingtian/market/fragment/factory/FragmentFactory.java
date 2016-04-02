package com.jingtian.market.fragment.factory;

import java.util.HashMap;
import java.util.Map;

import com.jingtian.market.fragment.AppFragment;
import com.jingtian.market.fragment.BaseFragment;
import com.jingtian.market.fragment.CategoryFragment;
import com.jingtian.market.fragment.GameFragment;
import com.jingtian.market.fragment.HomeFragment;
import com.jingtian.market.fragment.SubjectFragment;
import com.jingtian.market.fragment.TopFragment;

import android.support.v4.app.Fragment;

public class FragmentFactory {

	//Map, List, Array are cache techniques
	private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();
	
	/**
	 * create a fragment (cache is used)
	 * @param position
	 * @return
	 */
	public static BaseFragment createFragment(int position){
		BaseFragment fragment = null;
		fragment = mFragments.get(position);
		
		//first time create fragments, other than first time using cache(get from map)
		if (fragment == null) { 
			if (position == 0) {	
				fragment = new HomeFragment();
			}
			else if (position == 1) {
				fragment = new AppFragment();
			}
			else if (position == 2) {
				fragment = new GameFragment();
			}
			else if (position == 3) {
				fragment = new SubjectFragment();
			}
			else if (position == 4) {
				fragment = new CategoryFragment();
			}
			else if (position == 5) {
				fragment = new TopFragment();
			}
			//store the fragment to cache(Map) after new
			if (fragment != null) { 
				mFragments.put(position, fragment);
			}
		}
		
		return fragment;
		
	}
}
