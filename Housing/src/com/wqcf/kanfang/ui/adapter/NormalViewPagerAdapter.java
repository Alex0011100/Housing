package com.wqcf.kanfang.ui.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public class NormalViewPagerAdapter extends PagerAdapter{
	
	
	private ArrayList<View> viewArray;
	
	public NormalViewPagerAdapter(ArrayList<View> viewArray){
		this.viewArray = viewArray;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewArray.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

}
