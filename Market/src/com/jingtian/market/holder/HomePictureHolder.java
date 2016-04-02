package com.jingtian.market.holder;

import java.util.LinkedList;
import java.util.List;

import com.jingtian.market.R;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.utils.UIUtil;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;

public class HomePictureHolder extends BaseHolder<List<String>> {

	private ViewPager viewPager;
	private List<String> info;
	
	/**
	 * invoke when new HomePictureHolder()
	 */
	@Override
	protected View initView() {
		viewPager = new ViewPager(UIUtil.getContext());
		//view pager will be added to the listview, so the AbsListView is in use
		viewPager.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,UIUtil.getDimens(R.dimen.home_picture_height)));
		return viewPager;
	}

	/**
	 * invoke when holder.setInfo();
	 */
	@Override
	public void refreshView(List<String> info) {
		this.info = info;
		viewPager.setAdapter(new HomePictureAdapter());
		viewPager.setCurrentItem(2000*info.size());// set start position: (so we can slide left from outset)   Integer.Max_Vlue/2
	
		viewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: //when push down, no more sliding
					runTask.stop();   
					break;
				//-----------------
				case MotionEvent.ACTION_CANCEL:  // after pushing and moving to other components
				case MotionEvent.ACTION_UP: //when bounce up, continue to slide
					runTask.start();
					break;
				//-----------------
				}
				
				return false; // false: do not intercept viewPager's touch event(so it can still )
			}
		});
		runTask = new AutoRunTask();
		runTask.start();
	}

	class HomePictureAdapter extends PagerAdapter{
		// viewPager's current items
		LinkedList<ImageView> convertView=new LinkedList<ImageView>();
		
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view=(ImageView) object;
			convertView.add(view);// save the image to memory cache (try not to new it every time)
			container.removeView(view); //remove view from screen
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int index=position%info.size();
			ImageView imageView;
			if(convertView.size()>0){
				imageView=convertView.remove(); //retrieve the image from memory cache and remove from the memory
			}else{
				imageView=new ImageView(UIUtil.getContext()); //new only for the first time
			}
			bitmapUtils.display(imageView, HttpHelper.URL+"image?name="+info.get(index));
			container.addView(imageView);  //loading view
			return imageView; // return view
		}
		
	}
	
	
	boolean flag; //a tag to allow pager change
	private AutoRunTask runTask;
	public class AutoRunTask implements Runnable{ //runnable - task

		@Override
		public void run() {
			if(flag){
				UIUtil.cancel(this);  // cancel current task
				int currentItem = viewPager.getCurrentItem();
				currentItem++;
				viewPager.setCurrentItem(currentItem);
				//  run current task with a 5 second delay
				UIUtil.postDelayed(this, 5000);// run recursively (run function will be invoked )
			}
		}
		public void start(){
			if(!flag){
				UIUtil.cancel(this);  // cancel current task
				flag=true;
				UIUtil.postDelayed(this, 5000);// run current task recursively (run function will be invoke)
			}
		}
		public  void stop(){
			if(flag){
				flag=false;
				UIUtil.cancel(this);
			}
		}
		
	}
}
