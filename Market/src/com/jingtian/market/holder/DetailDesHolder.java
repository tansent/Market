package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.utils.UIUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailDesHolder extends BaseHolder<AppInfo> implements OnClickListener {
	@ViewInject(R.id.des_content)
	private TextView des_content;
	@ViewInject(R.id.des_author)
	private TextView des_author;
	@ViewInject(R.id.des_arrow)
	private ImageView des_arrow;
	@ViewInject(R.id.des_layout)
	private RelativeLayout des_layout;
	
	@Override
	protected View initView() {
		View view=UIUtil.inflate(R.layout.detail_des);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		des_content.setText(info.getDes());
		des_author.setText("作者:"+info.getAuthor());
		des_layout.setOnClickListener(this);
		
		//des_content 起始高度7行的高度
		LayoutParams layoutParams = des_content.getLayoutParams();
		layoutParams.height=getShortMeasureHeight();
		des_content.setLayoutParams(layoutParams);
		des_arrow.setImageResource(R.drawable.arrow_down);
	}
	/**
	 * 获取7行的高度
	 * @return
	 */
	public int getShortMeasureHeight(){
		// 复制一个新的TextView 用来测量,最好不要在之前的TextView测量 有可能影响其它代码执行
		TextView textView=new TextView(UIUtil.getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//设置字体大小14sp
		textView.setMaxLines(7);
		textView.setLines(7);// make it has 7 empty lines
		int width=des_content.getMeasuredWidth(); // 开始宽度
		
		int widthMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);
		int heightMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);
		textView.measure(widthMeasureSpec, heightMeasureSpec); //measure the length of a View by its content
		return textView.getMeasuredHeight();
	}
	
	/**
	 * get entire height of unfolded TextView 
	 * @return
	 */
	public int getLongMeasureHeight(){
		int width=des_content.getMeasuredWidth(); // 开始宽度
		des_content.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;// 高度包裹内容
		
		int widthMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);
		int heightMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);
		des_content.measure(widthMeasureSpec,heightMeasureSpec);//core function
		return des_content.getMeasuredHeight();
	}

	@Override
	public void onClick(View v) {
		expand();
	}
	
	ScrollView scrollView;
//	scrollView.scrollTo(0, scrollView.getMeasuredHeight())
	/**
	 * recursively get ScollView (to scroll it down)
	 */
	public ScrollView getScrollView(View view){
		ViewParent parent = view.getParent();
		if(parent instanceof ViewGroup){ //ViewGroup includes all View types except framework
			ViewGroup group=(ViewGroup) parent;
			if(group instanceof ScrollView){
				return (ScrollView)group;
			}else{
				return getScrollView(group);
			}
			
		}else{
			return null;
		}
		
	}
	
	boolean flag;// true展开了 false 没有展开
	private void expand() {
		scrollView=getScrollView(des_layout);
		int startHeight;
		int targetHeight;
		if(!flag){
			flag=true;
			startHeight=getShortMeasureHeight();
			targetHeight=getLongMeasureHeight();
		}else{
			flag=false;
			startHeight=getLongMeasureHeight();
			targetHeight=getShortMeasureHeight();
		}
		final LayoutParams layoutParams = des_content.getLayoutParams();
		ValueAnimator animator=ValueAnimator.ofInt(startHeight,targetHeight);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value=(Integer) animation.getAnimatedValue();
				layoutParams.height=value;
				des_content.setLayoutParams(layoutParams);
				
				if (scrollView != null) {
					//.getMeasuredHeight() to get max height of a component
					scrollView.scrollTo(0, scrollView.getMeasuredHeight());// 让scrollView 移动到最下面
//					scrollView.scrollTo(0, getLongMeasureHeight());// 让scrollView 移动到第7行
				}
			}
		});
		animator.addListener(new AnimatorListener() {  // 监听动画执行
			//当动画开始执行的时候调用
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				if(flag){
					des_arrow.setImageResource(R.drawable.arrow_up);
				}else{
					des_arrow.setImageResource(R.drawable.arrow_down);
				}
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
		animator.setDuration(500);//设置动画持续时间
		animator.start();
	}
}
