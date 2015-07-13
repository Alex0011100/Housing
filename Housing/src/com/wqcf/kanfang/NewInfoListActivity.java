package com.wqcf.kanfang;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.Header;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqcf.kanfang.core.BaseNetFragment;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.core.UserInfo;
import com.wqcf.kanfang.interfaze.IListViewNotify;
import com.wqcf.kanfang.interfaze.INetNotify;
import com.wqcf.kanfang.net.engine.QueryNet;
import com.wqcf.kanfang.net.param.QueryParam;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.TextHttpResponseHandler;
import com.wqcf.kanfang.ui.adapter.FragViewPagerAdapter;
import com.wqcf.kanfang.ui.adapter.MyspinnerDropdownAdapter;
import com.wqcf.kanfang.ui.manager.ProgressDialogManager;
import com.wqcf.kanfang.ui.view.TabViewPager;
import com.wqcf.kanfang.util.Logger;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewInfoListActivity extends android.support.v4.app.FragmentActivity implements OnClickListener,OnDismissListener{

	private TextView mClkTxtRent,
	mClkTxtBuyHouse,
	mClkTxtSecondHand,
	mClkTxtQifang,
	mClkTxtArea,
	mClkTxtRoomtype,
	mClkTxtPrice;

	private View mViewRent,
	mViewBuy,
	mViewSec,
	mViewQF;
	
	private ImageView mImgDrawerBtn;
	
	private DrawerLayout mDrawerLayout;
	
	private FragmentManager fragmentManager;
	private BaseNetFragment mFragmentRent,mFragmentBuy,mFragmentSec,mFragmentQF;
	private ArrayList<Fragment> fragList;
	private TabViewPager viewPager;
	private FragmentStatePagerAdapter mViewPagerAdapter;

	private PopupWindow mPopupWindowArea,mPopupWindowPrice,mPopupWindowRoomtype;
	private LinearLayout mLLayoutSelect;
	private ArrayList<String> mArraySelectArea,mArraySelectPrice,mArraySelectRoomtype;
	private LayoutInflater mInflater;


	/*
	 * 用来标记不同Tab的enum类型
	 */
	private enum ClickTabIndex {RentTab,BuyTab,SecTab,QfTab}
	/*
	 * 用来标记不同PopWindow的enum类型
	 */
	private enum PopWindowIndex {Area,Price,RoomType}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infolist_new);

		initView();
		initData();
		initTestData();
		initClickListener();
		//mProgressDailog = ProgressDialogManager.showPD(this, "Loading", "Please wait...");

	}



	private void initView(){
		mClkTxtRent = (TextView)findViewById(R.id.Txt_act_infolistnew_Rent);
		mClkTxtBuyHouse = (TextView)findViewById(R.id.Txt_act_infolistnew_Buy);
		mClkTxtSecondHand = (TextView)findViewById(R.id.Txt_act_infolistnew_Sec);
		mClkTxtQifang = (TextView)findViewById(R.id.Txt_act_infolistnew_Qifang);
		mClkTxtArea = (TextView)findViewById(R.id.Txt_act_infolistnew_Area);
		mClkTxtRoomtype = (TextView)findViewById(R.id.Txt_act_infolistnew_Roomtype);
		mClkTxtPrice = (TextView)findViewById(R.id.Txt_act_infolistnew_Price);

		mViewRent = (View)findViewById(R.id.View_act_infolist_Tab_1);
		mViewBuy = (View)findViewById(R.id.View_act_infolist_Tab_2);
		mViewSec = (View)findViewById(R.id.View_act_infolist_Tab_3);
		mViewQF = (View)findViewById(R.id.View_act_infolist_Tab_4);
		mImgDrawerBtn = (ImageView)findViewById(R.id.Img_act_infolist_User);
		mLLayoutSelect = (LinearLayout)findViewById(R.id.Llayout_act_infolistnew_Select);
		viewPager = (TabViewPager)findViewById(R.id.TabViewPager_act_infolist);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout_act_infolistnew);
		//initPopWindow();
	}

	private void initTestData() {
		String[] didians = {"全城", "朝阳", "海淀", "东城", "西城", "崇文", "宣武", "丰台", "通州", "石景山", "房山", "昌平", "大兴", "顺义", "密云", "怀柔", "延庆", "平谷", "门头沟", "燕郊", "北京周边"};
		String[] tingshis = {"不限", "一室", "两室", "三室", "四室", "四室以上"};
		String[] zujins = {"不限", "600元以下", "600-1000元", "1000-1500元", "1000-1500元", "1500-2000元", "2000-3000元", "3000-5000元", "5000-8000元", "8000元以上"};
		Collections.addAll(mArraySelectArea, didians);
		Collections.addAll(mArraySelectRoomtype, tingshis);
		Collections.addAll(mArraySelectPrice, zujins);
	}

	private void initData(){
		mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mArraySelectArea = new ArrayList<String>();
		mArraySelectPrice = new ArrayList<String>();
		mArraySelectRoomtype = new ArrayList<String>();

		fragmentManager = getSupportFragmentManager();

//		mFragmentRent = new FragmentRent();
//		mFragmentBuy = new FragmentBuy();
//		mFragmentSec = new FragmentSec();
//		mFragmentQF = new FragmentQF();

		fragList = new ArrayList<Fragment>();
		fragList.add(mFragmentRent);
		fragList.add(mFragmentBuy);
		fragList.add(mFragmentSec);
		fragList.add(mFragmentQF);
		mViewPagerAdapter = new FragViewPagerAdapter(fragmentManager, fragList);
		viewPager.setAdapter(mViewPagerAdapter);
		changeTabAndFragment(ClickTabIndex.RentTab);

	}

	private void initClickListener(){
		mClkTxtRent.setOnClickListener(this);
		mClkTxtBuyHouse.setOnClickListener(this);
		mClkTxtSecondHand.setOnClickListener(this);
		mClkTxtQifang.setOnClickListener(this);

		mClkTxtArea.setOnClickListener(this);
		mClkTxtRoomtype.setOnClickListener(this);
		mClkTxtPrice.setOnClickListener(this);
		mImgDrawerBtn.setOnClickListener(this);

	}
	/*
	 * 更换tab的选中条，和更换相应的fragment
	 */
	private void changeTabAndFragment(ClickTabIndex index){
		switch(index){
		case RentTab:
			mViewRent.setVisibility(View.VISIBLE);
			mViewBuy.setVisibility(View.INVISIBLE);
			mViewSec.setVisibility(View.INVISIBLE);
			mViewQF.setVisibility(View.INVISIBLE);
			viewPager.setCurrentItem(0, true);
			return;
		case BuyTab:
			mViewRent.setVisibility(View.INVISIBLE);
			mViewBuy.setVisibility(View.VISIBLE);
			mViewSec.setVisibility(View.INVISIBLE);
			mViewQF.setVisibility(View.INVISIBLE);
			viewPager.setCurrentItem(1, true);
			return;
		case SecTab:
			mViewRent.setVisibility(View.INVISIBLE);
			mViewBuy.setVisibility(View.INVISIBLE);
			mViewSec.setVisibility(View.VISIBLE);
			mViewQF.setVisibility(View.INVISIBLE);
			viewPager.setCurrentItem(2, true);
			return;
		case QfTab:
			mViewRent.setVisibility(View.INVISIBLE);
			mViewBuy.setVisibility(View.INVISIBLE);
			mViewSec.setVisibility(View.INVISIBLE);
			mViewQF.setVisibility(View.VISIBLE);
			viewPager.setCurrentItem(3, true);
			return;
		}
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Txt_act_infolistnew_Rent:
			changeTabAndFragment(ClickTabIndex.RentTab);
			return;
		case R.id.Txt_act_infolistnew_Buy:
			changeTabAndFragment(ClickTabIndex.BuyTab);
			return;
		case R.id.Txt_act_infolistnew_Sec:
			changeTabAndFragment(ClickTabIndex.SecTab);
			return;
		case R.id.Txt_act_infolistnew_Qifang:
			changeTabAndFragment(ClickTabIndex.QfTab);
			return;
		case R.id.Txt_act_infolistnew_Area:
			showPopWindow(PopWindowIndex.Area);
			return;
		case R.id.Txt_act_infolistnew_Price:
			showPopWindow(PopWindowIndex.Price);
			return;
		case R.id.Txt_act_infolistnew_Roomtype:
			showPopWindow(PopWindowIndex.RoomType);
			return;
		case R.id.Img_act_infolist_User:
			mDrawerLayout.openDrawer(Gravity.RIGHT);
			return;
		}

	}

	private void showPopWindow(PopWindowIndex index){

		/*
		 * 每次弹出popupwindow需要重新实例化？？
		 */
		switch(index){
		case Area:
			initPopWindow(index);
			mPopupWindowArea.showAsDropDown(mLLayoutSelect,0,0);
			return;
		case Price:
			initPopWindow(index);
			mPopupWindowPrice.showAsDropDown(mLLayoutSelect,0,0);
			return;
		case RoomType:
			initPopWindow(index);
			mPopupWindowRoomtype.showAsDropDown(mLLayoutSelect,0,0);
			return;
		}
	}

	private void initPopWindow(PopWindowIndex index){
		switch(index){
		case Area:
			View view = getPopWindowView(mInflater,PopWindowIndex.Area);
			mPopupWindowArea = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			mPopupWindowArea.setBackgroundDrawable(new BitmapDrawable());//new ColorDrawable(0)
			mPopupWindowArea.setOutsideTouchable(true);  
			mPopupWindowArea.setFocusable(true);  
			mPopupWindowArea.setOnDismissListener(this);
			return ;
		case Price:
			view = getPopWindowView(mInflater,PopWindowIndex.Price);
			mPopupWindowPrice = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			mPopupWindowPrice.setBackgroundDrawable(new ColorDrawable(0));
			mPopupWindowPrice.setOutsideTouchable(true);  
			mPopupWindowPrice.setFocusable(true);  
			mPopupWindowPrice.setOnDismissListener(this);
			return ;
		case RoomType:
			view = getPopWindowView(mInflater,PopWindowIndex.RoomType);
			mPopupWindowRoomtype = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			mPopupWindowRoomtype.setBackgroundDrawable(new ColorDrawable(0));
			mPopupWindowRoomtype.setOutsideTouchable(true);  
			mPopupWindowRoomtype.setFocusable(true);  
			mPopupWindowRoomtype.setOnDismissListener(this);
			return ;
		}
	}

	private View getPopWindowView(LayoutInflater inflater,PopWindowIndex index){
		switch(index){
		case Area:
			View popViewArea = inflater.inflate(R.layout.popwindow_act_infolist_area, null);
			MyspinnerDropdownAdapter adapterArea = new MyspinnerDropdownAdapter(this,mArraySelectArea);
			((ListView)popViewArea.findViewById(R.id.listView_popWindow_Area)).setAdapter(adapterArea);
			return popViewArea;
		case Price:
			View popViewPrice = inflater.inflate(R.layout.popwindow_act_infolist_price, null);
			MyspinnerDropdownAdapter adapterPrice = new MyspinnerDropdownAdapter(this,mArraySelectPrice);
			((ListView)popViewPrice.findViewById(R.id.listView_popWindow_Price)).setAdapter(adapterPrice);
			return popViewPrice;
		case RoomType:
			View popViewRoomtype = inflater.inflate(R.layout.popwindow_act_infolist_roomtype, null);
			MyspinnerDropdownAdapter adapterRoomtype = new MyspinnerDropdownAdapter(this,mArraySelectRoomtype);
			((ListView)popViewRoomtype.findViewById(R.id.listView_popWindow_Roomtype)).setAdapter(adapterRoomtype);
			return popViewRoomtype;
		}
		return null;
	}

	/*
	 * PopupWindow dismiss listener
	 */
	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub

	}



}
