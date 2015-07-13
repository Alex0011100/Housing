package com.wqcf.kanfang;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqcf.kanfang.core.BaseFragment;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.data.core.UserInfo;
import com.wqcf.kanfang.interfaze.INetNotify;
import com.wqcf.kanfang.net.param.QueryParam;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.TextHttpResponseHandler;
import com.wqcf.kanfang.opensrc.xlistview.XListView;
import com.wqcf.kanfang.opensrc.xlistview.XListView.IXListViewListener;
import com.wqcf.kanfang.ui.adapter.MyspinnerDropdownAdapter;
import com.wqcf.kanfang.ui.adapter.RoomListAdapter;
import com.wqcf.kanfang.ui.view.SlideShowView;
import com.wqcf.kanfang.util.Logger;

public class FragmentNearby extends BaseFragment implements IXListViewListener,OnItemClickListener,OnClickListener,OnDismissListener{
	
	private XListView lv;
	private TextView mClkTxtRoomtype,mClkTxtPrice,mClkTxtLive;
	private RoomListAdapter<RoomInfoBean> adapter;
	private ArrayList<RoomInfoBean> list;
	private PopupWindow mPopupWindow;
	private LayoutInflater mInflater;
	private LinearLayout mLlayoutSelect;
	private ArrayList<String> mArraySelectPrice,mArraySelectRoomtype;
	private Activity act;
	private INetNotify netInstance;
	/*
	 * 用来标记不同PopWindow的enum类型
	 */
	private enum PopWindowIndex {Price,RoomType,Live}
	private enum QueryInfoType{Refresh,Loadmore}
	private QueryInfoType mQueryType = QueryInfoType.Refresh;
	
	public FragmentNearby(){
		this.list = new ArrayList<RoomInfoBean>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_act_infolist_nearby, null);
		initData();
		initTestData();
		initView(view);
		setView();
		mInflater = inflater;
		act = getActivity();
		netInstance = (INetNotify) act;
		
		RequestParams params = new RequestParams();
		params.put(QueryParam.PARAM_TRADE_TYPE,QueryParam.TRADE_TYPE_RENT_HOUSE);
		params.put(QueryParam.PARAM_USER_ID, UserInfo.UserId);

		netInstance.netRequest(httpHandler, params);
		
		return view;
	}
	
	private void initView(View view){
		mClkTxtRoomtype = (TextView)view.findViewById(R.id.Txt_frag_allcity_Roomtype);
		mClkTxtPrice = (TextView)view.findViewById(R.id.Txt_frag_allcity_Price);
		mClkTxtLive = (TextView)view.findViewById(R.id.Txt_frag_allcity_Live);
		lv = (XListView)view.findViewById(R.id.ListView_fragment);
		mLlayoutSelect = (LinearLayout)view.findViewById(R.id.Llayout_frag_allcity_Select);
	}
	private void setView(){
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(false);
		lv.setOnItemClickListener(this);
		list = new ArrayList<RoomInfoBean>();
		adapter = new RoomListAdapter<RoomInfoBean>(getActivity(),lv);
		adapter.setList(list);
	}
	
	private void initData(){
		mArraySelectRoomtype = new ArrayList<String>();
		mArraySelectPrice = new ArrayList<String>();
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> viewParent, View view, int position, long id) {
		// TODO Auto-generated method stub
		DataManager.getInstance().setRoomInfo(list.get(position-1)); 
		Intent intent = new Intent(act,InfoActivity.class);
		startActivity(intent);
	}
	
	private void showPopWindow(PopWindowIndex index){
		/*
		 * 每次弹出popupwindow需要重新实例化？？
		 */
		switch(index){

		case Price:
			initPopWindow(index);
			mPopupWindow.showAsDropDown(mLlayoutSelect,0,0);
			return;
		case RoomType:
			initPopWindow(index);
			mPopupWindow.showAsDropDown(mLlayoutSelect,0,0);
			return;
		}
	}
	
	private void initPopWindow(PopWindowIndex index){
		switch(index){

		case Price:
			View view = getPopWindowView(mInflater,PopWindowIndex.Price);
			mPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
			mPopupWindow.setOutsideTouchable(true);  
			mPopupWindow.setFocusable(true);  
			mPopupWindow.setOnDismissListener(this);
			return ;
		case RoomType:
			view = getPopWindowView(mInflater,PopWindowIndex.RoomType);
			mPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
			mPopupWindow.setOutsideTouchable(true);  
			mPopupWindow.setFocusable(true);  
			mPopupWindow.setOnDismissListener(this);
			return ;
		}
	}
	
	private View getPopWindowView(LayoutInflater inflater,PopWindowIndex index){
		switch(index){

		case Price:
			View popViewPrice = inflater.inflate(R.layout.popwindow_act_infolist_price, null);
			MyspinnerDropdownAdapter adapterPrice = new MyspinnerDropdownAdapter(act,mArraySelectPrice);
			((ListView)popViewPrice.findViewById(R.id.listView_popWindow_Price)).setAdapter(adapterPrice);
			return popViewPrice;
		case RoomType:
			View popViewRoomtype = inflater.inflate(R.layout.popwindow_act_infolist_roomtype, null);
			MyspinnerDropdownAdapter adapterRoomtype = new MyspinnerDropdownAdapter(act,mArraySelectRoomtype);
			((ListView)popViewRoomtype.findViewById(R.id.listView_popWindow_Roomtype)).setAdapter(adapterRoomtype);
			return popViewRoomtype;
		}
		return null;
	}
	
	private void initTestData() {
		
		String[] tingshis = {"不限", "一室", "两室", "三室", "四室", "四室以上"};
		String[] zujins = {"不限", "600元以下", "600-1000元", "1000-1500元", "1000-1500元", "1500-2000元", "2000-3000元", "3000-5000元", "5000-8000元", "8000元以上"};
		
		Collections.addAll(mArraySelectRoomtype, tingshis);
		Collections.addAll(mArraySelectPrice, zujins);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){

		case R.id.Txt_frag_allcity_Roomtype:
			showPopWindow(PopWindowIndex.RoomType);
			return;
		case R.id.Txt_frag_allcity_Price:
			showPopWindow(PopWindowIndex.Price);
			return;
		case R.id.Txt_frag_allcity_Live:
			showPopWindow(PopWindowIndex.Live);
			return;
			
		}
	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void call() {
		// TODO Auto-generated method stub
		
	}
	
	private TextHttpResponseHandler httpHandler = new TextHttpResponseHandler(){
		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			//Toast.makeText(getActivity(),"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			Logger.log("rent_response", responseString);
			switch(mQueryType){
			case Refresh:
				JSONArray jsonArr = JSON.parseArray(responseString);
				adapter.clear();
				for(int i = 0; i < jsonArr.size();++i){
					JSONObject jobj = (JSONObject)jsonArr.get(i);
					RoomInfoBean testBean = JSON.toJavaObject(jobj, RoomInfoBean.class);
					adapter.add(testBean);
				}
				lv.setAdapter(adapter);
				return;
			case Loadmore:
				return;
			}
			
		}
		
	};
	
}
