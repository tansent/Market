package com.jingtian.market;


import android.app.Application;
import android.content.Context;
import android.os.Handler;

/*
 * represent current app (context)
 * 
 * need config in manifest file: android:name="BaseApplication"
 */
public class BaseApplication extends Application {

	private static BaseApplication application;
	private static int mainThreadID;
	private static Handler handler;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		//android.os.Process.myTid(): get current thread id 
		mainThreadID = android.os.Process.myTid(); //Main thread id
		handler=new Handler();
	}

	public static Context getApplication() {
		return application;
	}
	
	public static int getMainTid() {
		return mainThreadID;
	}
	public static Handler getHandler() {
		return handler;
	}
}

