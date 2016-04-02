package com.jingtian.market.view;

import java.util.ArrayList;
import java.util.List;

import com.jingtian.market.utils.UIUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Flowlayout extends ViewGroup {

	private int horizontolSpacing=UIUtil.dip2px(13);
	private int verticalSpacing=UIUtil.dip2px(13);
	private Line currentline;// current line / current child
	private int useWidth=0;// occupied width in current line
	private List<Line> mLines=new ArrayList<Flowlayout.Line>();
	private int width;
	/**
	 * measure all children views based on parent views (each child is a TextView here, but will set into a line)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		MeasureSpec.EXACTLY;
//		MeasureSpec.AT_MOST;
//		MeasureSpec.UNSPECIFIED;
		mLines.clear();
		currentline=null;
		useWidth=0;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);  //  get parent's layout mode (Flowlayout)
		width = MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight(); //parent's size - paddings
		int height = MeasureSpec.getSize(heightMeasureSpec)-getPaddingBottom()-getPaddingTop(); // get overall height without padding
		int childeWidthMode;
		int childeHeightMode;
	//  specify child's mode according to the parent's mode
		childeWidthMode=widthMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:widthMode;
		childeHeightMode=heightMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode;
		
		int childWidthMeasureSpec=MeasureSpec.makeMeasureSpec(childeWidthMode,  width); //create new width
		int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(childeHeightMode,  height);
		
		currentline=new Line();// create a new line to contain children
		for(int i=0;i<getChildCount();i++)	{
			View child=getChildAt(i);
			//set new width and height to each child(TextView)
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec); 
			
			int measuredWidth = child.getMeasuredWidth();
			useWidth+=measuredWidth;// current line consumes more width 
			if(useWidth<=width){ //current line still have room
				currentline.addChild(child);//add textview to the current line
				useWidth+=horizontolSpacing;
				if(useWidth>width){ //no more room for this line
					//add current line to the set and create a new line
					newLine();
				}
			}else{
				if(currentline.getChildCount()<1){ //a single TextView could be wider than a line
					currentline.addChild(child);  // to guarantee at least one child for a line
				}
				//add current line to the set and create a new line
				newLine();
			}
			
		}
		if(!mLines.contains(currentline)){
			mLines.add(currentline);// add last line to the set
		}
		int  totalheight=0;
		for(Line line:mLines){
			totalheight+=line.getHeight();
		}
		totalheight+=verticalSpacing*(mLines.size()-1)+getPaddingTop()+getPaddingBottom();
		
//		System.out.println(totalheight);
		 setMeasuredDimension(width+getPaddingLeft()+getPaddingRight(),resolveSize(totalheight, heightMeasureSpec));
		
	}
	
	private void newLine() {
		mLines.add(currentline);// add current line 
		currentline=new Line(); // create a new line
		useWidth=0;				// reset current line used room to 0
	}
	
	
	private class Line{
		int height=0; //current line height
		int lineWidth=0;
		private List<View> children=new ArrayList<View>();
		/**
		 * add a child
		 * @param child
		 */
		public void addChild(View child) {
			children.add(child);
			if(child.getMeasuredHeight()>height){ //use the highest child's height as the standard 
				height=child.getMeasuredHeight();
			}
			lineWidth+=child.getMeasuredWidth();
		}
		public int getHeight() {
			return height;
		}
		/**
		 * return children's number
		 * @return
		 */
		public int getChildCount() {
			return children.size();
		}
		public void layout(int l, int t) {
			lineWidth+=horizontolSpacing*(children.size()-1);
			int surplusChild=0;
			int surplus=width-lineWidth; //leftover empty space
			if(surplus>0){
				surplusChild=surplus/children.size();
			}
			for(int i=0;i<children.size();i++){
				View child=children.get(i);
				//  getMeasuredWidth()   get set width
				// getWidth()  get shown width(if it is invisible, it is 0)
				
				//layout() can set a component to a specific position
				child.layout(l, t, l+child.getMeasuredWidth()+surplusChild, t+child.getMeasuredHeight());
				l+=child.getMeasuredWidth()+surplusChild;
				l+=horizontolSpacing;
			}
		}
		
	}
	
	
	/**
	 * set each child's layout, run after onMeasure() 
	 * But here, we only set each line's layout;
	 * Each TextView's layout will be set inside each line
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		l+=getPaddingLeft(); //getPaddingLeft(): get parent padding
		t+=getPaddingTop();
		for(int i=0;i<mLines.size();i++){  
			Line line=mLines.get(i);
			line.layout(l,t);  //Each TextView's layout will be set inside each line
			t+=line.getHeight()+verticalSpacing;
		}
	}
	
	
	//----unuseful constructors--------
	public Flowlayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public Flowlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Flowlayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
