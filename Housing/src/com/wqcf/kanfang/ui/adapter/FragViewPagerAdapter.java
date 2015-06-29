package com.wqcf.kanfang.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragViewPagerAdapter extends FragmentStatePagerAdapter{
	
	private ArrayList<Fragment> fragmentList;
	private ArrayList<String> titleList;
	
	
	public FragViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList,ArrayList<String> titleList) {
		super(fm);
		this.fragmentList = fragmentList;
		this.titleList = titleList;
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		return fragmentList.get(index);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentList.size();
	}


	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titleList.get(position);
	}
	
}
