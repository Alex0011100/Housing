package com.wqcf.kanfang.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListViewNoScroll extends ListView{

	public ListViewNoScroll(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ListViewNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	public ListViewNoScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	            MeasureSpec.AT_MOST);  
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}
