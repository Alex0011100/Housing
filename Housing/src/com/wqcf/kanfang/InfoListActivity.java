package com.wqcf.kanfang;




import java.util.ArrayList;

import com.wqcf.kanfang.core.BaseFragment;
import com.wqcf.kanfang.data.core.UserInfo;
import com.wqcf.kanfang.interfaze.INetNotify;
import com.wqcf.kanfang.net.engine.QueryNet;
import com.wqcf.kanfang.net.param.QueryParam;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.TextHttpResponseHandler;
import com.wqcf.kanfang.ui.adapter.FragViewPagerAdapter;
import com.wqcf.kanfang.ui.view.TabViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoListActivity extends android.support.v4.app.FragmentActivity implements OnClickListener,INetNotify{

	private ImageView mBackBtn;
	private TextView mClkTxtAllCity,mClkTxtNearby,mClkTxtSearch;
	private TabViewPager viewPager;
	
	
	private BaseFragment mFragmentAllCity,mFragmentNearBy,mFragmentSearch;
	private FragmentManager fragmentManager;
	private FragmentStatePagerAdapter mViewPagerAdapter;
	private ArrayList<Fragment> fragList;
	
	private enum ClickTabIndex {AllCity,NearBy,Search}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infolist);
		
		getDataAndInitView();
		initClickListener();
		initData();
	}

	
	private void getDataAndInitView() {
		String title = getIntent().getStringExtra("title");
		((TextView)findViewById(R.id.Txt_title_infolist)).setText(title);

		
		mBackBtn = (ImageView) findViewById(R.id.Img_back_infolist);
		mClkTxtAllCity = (TextView)findViewById(R.id.Txt_act_infolist_AllCity);
		mClkTxtNearby = (TextView)findViewById(R.id.Txt_act_infolist_NearBy);
		mClkTxtSearch = (TextView)findViewById(R.id.Txt_act_infolist_Search);
		viewPager = (TabViewPager)findViewById(R.id.TabViewPager_act_infolist);
	}

	private void initClickListener() {
		mBackBtn.setOnClickListener(this);
		mClkTxtAllCity.setOnClickListener(this);
		mClkTxtNearby.setOnClickListener(this);
		mClkTxtSearch.setOnClickListener(this);
	}
	
	private void initData(){
		mFragmentAllCity = new FragmentAllCity();
		mFragmentNearBy = new FragmentNearby();
		mFragmentSearch = new FragmentSearch();
		fragmentManager = getSupportFragmentManager();
		fragList = new ArrayList<Fragment>();
		fragList.add(mFragmentAllCity);
		fragList.add(mFragmentNearBy);
		fragList.add(mFragmentSearch);
		mViewPagerAdapter = new FragViewPagerAdapter(fragmentManager, fragList);
		viewPager.setAdapter(mViewPagerAdapter);
		changeTabAndFragment(ClickTabIndex.AllCity);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Img_back_infolist:
			finish();
			return;
		case R.id.Txt_act_infolist_AllCity:
			changeTabAndFragment(ClickTabIndex.AllCity);
			return;
		case R.id.Txt_act_infolist_NearBy:
			changeTabAndFragment(ClickTabIndex.NearBy);
			return;
		case R.id.Txt_act_infolist_Search:
			changeTabAndFragment(ClickTabIndex.Search);
			return;
		
		}
	}
	private void changeTabAndFragment(ClickTabIndex index){
		switch(index){
		case AllCity:
			viewPager.setCurrentItem(0, true);
			return;
		case NearBy:
			viewPager.setCurrentItem(1, true);
			return;
		case Search:
			viewPager.setCurrentItem(2, true);
			return;
		}
	}
	


	public void queryRoomList(TextHttpResponseHandler httpHandler) {
		RequestParams params = new RequestParams();
		params.put(QueryParam.PARAM_TRADE_TYPE,QueryParam.TRADE_TYPE_RENT_HOUSE);
		params.put(QueryParam.PARAM_USER_ID, UserInfo.UserId);
		
		QueryNet.getRoomList(httpHandler, params);

	}


	@Override
	public void netRequest(TextHttpResponseHandler httpHandler,
			RequestParams params) {
		// TODO Auto-generated method stub
		QueryNet.getRoomList(httpHandler, params);
	}


}
