package com.wqcf.kanfang.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ParamTextView extends TextView{
	
	public enum Param_Type {LOCATION,ROOMTYPE,PRICE};
	
	public Param_Type paramType;
	public String param;
	
	public ParamTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ParamTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public ParamTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void checkParam(){
		param = (String) getText();
	}


}
