package com.wqcf.kanfang;

import java.util.ArrayList;

import com.wqcf.kanfang.ui.adapter.FragViewPagerAdapter;
import com.wqcf.kanfang.ui.view.TabViewPager;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RegisterActivity extends android.support.v4.app.FragmentActivity implements OnClickListener{
	
	private ArrayList<Fragment> fragList;
	private ArrayList<String> titleList;
	private TabViewPager viewPager;
	private FragmentStatePagerAdapter mAdapter;
	private FragmentManager fragmentManager;
	private FragmentMailReg mFragMail;
	private FragmentPhoneReg mFragPhone;
	
	private View mViewPhone,mViewMail;
	private TextView mClkTxtRegPhone,mClkTxtRegMail;
	
	private Resources res;

	
	private enum ClickTabIndex {PhoneTab,MailTab}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		initData();
		initListener();
	}
	
	private void initView(){
		viewPager = (TabViewPager)findViewById(R.id.ViewPager_act_register_ContentTab);
		mClkTxtRegPhone = (TextView)findViewById(R.id.Txt_act_register_PhoneReg);
		mClkTxtRegMail = (TextView)findViewById(R.id.Txt_act_register_MailReg);
		mViewPhone = (View)findViewById(R.id.View_act_register_Phone);
		mViewMail = (View)findViewById(R.id.View_act_register_Mail);
		
		
	}
	private void initListener(){
		mClkTxtRegPhone.setOnClickListener(this);
		mClkTxtRegMail.setOnClickListener(this);
	}
	private void initData(){
		res = getResources();
		
		fragmentManager = getSupportFragmentManager();
		mFragMail = new FragmentMailReg();
		mFragPhone = new FragmentPhoneReg();
		fragList = new ArrayList<Fragment>();
		titleList = new ArrayList<String>();
		titleList.add("手机注册");
		titleList.add("邮箱注册");
		fragList.add(mFragPhone);
		fragList.add(mFragMail);
		
		mAdapter = new FragViewPagerAdapter(fragmentManager, fragList, titleList);
		viewPager.setAdapter(mAdapter);
		changeTabAndFragment(ClickTabIndex.PhoneTab);
	}
	
	private void changeTabAndFragment(ClickTabIndex index){
		switch(index){
		case PhoneTab:
			mViewPhone.setBackgroundResource(R.color.red2);
			mClkTxtRegPhone.setTextColor(res.getColor(R.color.red2));
			mViewMail.setBackgroundResource(R.color.gray_txt);
			mClkTxtRegMail.setTextColor(res.getColor(R.color.gray_txt));
			viewPager.setCurrentItem(0, true);
			return;
		case MailTab:
			mViewPhone.setBackgroundResource(R.color.gray_txt);
			mClkTxtRegPhone.setTextColor(res.getColor(R.color.gray_txt));
			mViewMail.setBackgroundResource(R.color.red2);
			mClkTxtRegMail.setTextColor(res.getColor(R.color.red2));
			viewPager.setCurrentItem(1, true);
			return;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Txt_act_register_PhoneReg:
			changeTabAndFragment(ClickTabIndex.PhoneTab);
			break;
		case R.id.Txt_act_register_MailReg:
			changeTabAndFragment(ClickTabIndex.MailTab);
			break;
		}
	}

}
