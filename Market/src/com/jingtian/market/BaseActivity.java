package com.jingtian.market;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/*
 * FragmentActivity extends from BaseActivity
 * 
 * The BaseActivity defined here is to define the sequence of the initialization,
 * also to make the code more readable
 * 
 */
public abstract class BaseActivity extends FragmentActivity {

	/**
	 * LinkedList is good for adding and deleting
	 * while ArrayList is good for searching
	 */
	public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
	
	//for judging if the current situation is a front-end(activity) or back-end(service / broadcastReceiver)
	public static BaseActivity activity; 
	
	// another way to kill all activities
//	private KillAllReceiver receiver;
//	private class KillAllReceiver extends BroadcastReceiver{
//		/**
//		 * what we do when we receive this broadcast
//		 */
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			finish();
//		}
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		activity = this;  //when the activity is running, make the tag has value
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		activity = null;  //when the activity does not show, set the tag as null (for Service and BroadcastReceiver)
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
//		// register in every activity (every activity extends base activity)
//		receiver = new KillAllReceiver();
//		IntentFilter filter = new IntentFilter("com.jingtian.market.killall");
//		registerReceiver(receiver, filter);
//		// add the code below to kill all activity
//		sendBroadcast(new Intent("com.jingtian.market.killall"));
		
		// one synchronized (mActivities) block can only be executed at one time
		synchronized (mActivities) { //mActivities is the lock (lock must be static)
			mActivities.add(this);
		}
		
		//the sequence of the initialization is here 
		init();
		initView();
		initActionBar();
		
	}

	
	protected abstract void init();
	
	protected abstract void initView();

	protected abstract void initActionBar();

	/**
	 * store all activities in the list, when we want to quit the app
	 * finish all activities in the list.
	 */
	public void killAllActivities(){
		//copy a list of activities (original list cannot be used due to onDestroy()-remove(this))
		List<BaseActivity> copy; 
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>(mActivities);
		}
		//the copy linklist can still do the kill-all job but will not be influenced by onDestroy()
		for (BaseActivity activity : copy) {
			activity.finish();
		}
		
		//shut down current process
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
			mActivities.remove(this);
		}
		
//		if (receiver != null) {
//			unregisterReceiver(receiver);
//			receiver = null;
//		}
	}
	
}
