package com.jingtian.market.holder;

import java.util.List;

import com.jingtian.market.R;
import com.jingtian.market.domain.AppInfo;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.utils.UIUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailSafeHolder extends BaseHolder<AppInfo> implements OnClickListener {

	@ViewInject(R.id.safe_layout)
	private RelativeLayout safe_layout;
	@ViewInject(R.id.safe_content)
	private LinearLayout safe_content;
	@ViewInject(R.id.safe_arrow)
	private ImageView safe_arrow;
	ImageView[] ivs;
	ImageView[] iv_des;
	TextView[] tv_des;
	LinearLayout[] des_layout;

	@Override
	protected View initView() {
		View view = UIUtil.inflate(R.layout.detail_safe);
		ViewUtils.inject(this, view);

		ivs = new ImageView[4]; // initialize tag images (up to 4)
		ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
		ivs[3] = (ImageView) view.findViewById(R.id.iv_4);
		iv_des = new ImageView[4]; // initialize images next to the descriptions
									// (up to 4)
		iv_des[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		iv_des[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		iv_des[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		iv_des[3] = (ImageView) view.findViewById(R.id.des_iv_4);
		tv_des = new TextView[4]; // initialize each descriptions (up to 4)
		tv_des[0] = (TextView) view.findViewById(R.id.des_tv_1);
		tv_des[1] = (TextView) view.findViewById(R.id.des_tv_2);
		tv_des[2] = (TextView) view.findViewById(R.id.des_tv_3);
		tv_des[3] = (TextView) view.findViewById(R.id.des_tv_4);

		des_layout = new LinearLayout[4]; // initialize each description layout
											// (1 image + 1 description)
		des_layout[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		des_layout[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		des_layout[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		des_layout[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

		//set tags initial state
		LayoutParams layoutParams = safe_content.getLayoutParams();
		layoutParams.height=0;
		safe_content.setLayoutParams(layoutParams);
		safe_arrow.setImageResource(R.drawable.arrow_down);
		
		return view;
	}

	@Override
	public void refreshView(AppInfo info) {
		safe_layout.setOnClickListener(this);
		
		List<String> safeUrl = info.getSafeUrl();
		List<String> safeDesUrl = info.getSafeDesUrl();
		List<String> safeDes = info.getSafeDes();
		List<Integer> safeDesColor = info.getSafeDesColor(); // color code that will responded: 0 1 2 3

		for (int i = 0; i < 4; i++) { // all components have up to 4 members
			if (i < safeUrl.size() && i < safeDesUrl.size() && i < safeDes.size() && i < safeDesColor.size()) {
				ivs[i].setVisibility(View.VISIBLE);
				des_layout[i].setVisibility(View.VISIBLE);
				// if it is visible, set data inside the components
				bitmapUtils.display(ivs[i], HttpHelper.URL + "image?name=" + safeUrl.get(i));
				bitmapUtils.display(iv_des[i], HttpHelper.URL + "image?name=" + safeDesUrl.get(i));
				tv_des[i].setText(safeDes.get(i));
				// show different text color according to the code responded
				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0);
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				tv_des[i].setTextColor(color);

			} else {
				// make components invisible(GONE) to "reduce" the items
				ivs[i].setVisibility(View.GONE);
				des_layout[i].setVisibility(View.GONE);
			}

		}

	}

	
	boolean unfolded; // whether the description is folded
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.safe_layout) {
			int startHeight;
			int targetHeight;
			if (!unfolded) { // 展开的动画
				startHeight = 0;
				targetHeight = getMeasureHeight();

				unfolded = true;
				// safe_content.setVisibility(View.VISIBLE);
				safe_content.getMeasuredHeight(); // 0
			} else {
				unfolded = false;
				// safe_content.setVisibility(View.GONE);
				startHeight = getMeasureHeight();
				targetHeight = 0;
			}
		

		// 值动画
		ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
		final RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) safe_content
				.getLayoutParams();
		animator.addUpdateListener(new AnimatorUpdateListener() { // 监听值的变化

			/**Animation process */
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				int value = (Integer) animator.getAnimatedValue();// 运行当前时间点的一个值
				layoutParams.height = value;
				safe_content.setLayoutParams(layoutParams);// 刷新界面 (mush have)
				System.out.println(value);
			}
		});

		animator.addListener(new AnimatorListener() { // 监听动画执行
			// 当动画开始执行的时候调用
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				if (unfolded) {
					safe_arrow.setImageResource(R.drawable.arrow_up);
				} else {
					safe_arrow.setImageResource(R.drawable.arrow_down);
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});

		animator.setDuration(500);
		animator.start();
		
		}
	}

	// onMeasure() 制定测量的规则
	// measure() 实际测量
	/**
	 * 获取控件实际的高度
	 */
	public int getMeasureHeight() {
		int width = safe_content.getMeasuredWidth(); // 由于宽度不会发生变化 宽度的值取出来
		safe_content.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;// 让高度包裹内容

		// 参数1 测量控件mode 参数2 大小
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width); // mode+size
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);// 我的高度
																						// 最大是1000
		// 测量规则 宽度是一个精确的值width, 高度最大是1000,以实际为准
		safe_content.measure(widthMeasureSpec, heightMeasureSpec); // 通过该方法重新测量控件

		return safe_content.getMeasuredHeight();

	}

}
