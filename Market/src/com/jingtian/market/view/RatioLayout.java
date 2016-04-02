package com.jingtian.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

/*
 * Defined item view in subject fragment to fix ratio problem
 */
public class RatioLayout extends FrameLayout {

	// show item according with this ratio
	private float ratio; 
		
	public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		// param1 namespace, param2 attr_name, param3 value
		float ratio = attrs.getAttributeFloatValue(
				"http://schemas.android.com/apk/res/com.jingtian.market",
				"ratio", 2.43f);
		this.ratio = ratio;
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0); // to invoke 3 params constructor(make these 2 constructors do same thing)
	}

	public RatioLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	// measure current layout
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec 宽度的规则 包含了两部分 模式 值
		int widthMode = MeasureSpec.getMode(widthMeasureSpec); // 模式
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);// 宽度大小
		int width = widthSize - getPaddingLeft() - getPaddingRight();// 去掉左右两边的padding

		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 模式
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);// 高度大小
		int height = heightSize - getPaddingTop() - getPaddingBottom();// 去掉上下两边的padding

		if (widthMode == MeasureSpec.EXACTLY // EXACTLY == match_parent   AT_MOST == wrap_content
				&& heightMode != MeasureSpec.EXACTLY) {
			// 修正一下 高度的值 让高度=宽度/比例
			height = (int) (width / ratio + 0.5f); // rounding
		} else if (widthMode != MeasureSpec.EXACTLY
				&& heightMode == MeasureSpec.EXACTLY) {
			// 由于高度是精确的值 ,宽度随着高度的变化而变化
			width = (int) ((height * ratio) + 0.5f);
		}
		// 重新制作了新的规则
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				width + getPaddingLeft() + getPaddingRight());
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				height + getPaddingTop() + getPaddingBottom());

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
