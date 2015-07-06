package com.wqcf.kanfang;


import java.util.ArrayList;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqcf.kanfang.core.BaseNetFragment;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.data.core.UserInfo;
import com.wqcf.kanfang.interfaze.IListViewNotify;
import com.wqcf.kanfang.interfaze.INetNotify;
import com.wqcf.kanfang.interfaze.INetNotify.RequestDataType;
import com.wqcf.kanfang.net.engine.QueryNet;
import com.wqcf.kanfang.net.param.QueryParam;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.TextHttpResponseHandler;
import com.wqcf.kanfang.opensrc.xlistview.XListView;
import com.wqcf.kanfang.opensrc.xlistview.XListView.IXListViewListener;
import com.wqcf.kanfang.ui.adapter.RoomListAdapter;
import com.wqcf.kanfang.util.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class FragmentSec extends BaseNetFragment implements IXListViewListener,OnItemClickListener{
	
	
	private XListView lv;
	private RoomListAdapter<RoomInfoBean> adapter;
	private ArrayList<RoomInfoBean> list;
	private RequestDataType mRequestDataType;
	
	
	private TextHttpResponseHandler httpHandler = new TextHttpResponseHandler(){
		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			Toast.makeText(getActivity(),"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			Logger.log("sec_response", responseString);
			switch(mRequestDataType){
			case ReQuery:
				JSONArray jsonArr = JSON.parseArray(responseString);
				adapter.clear();
				for(int i = 0; i < jsonArr.size();++i){
					JSONObject jobj = (JSONObject)jsonArr.get(i);
					RoomInfoBean testBean = JSON.toJavaObject(jobj, RoomInfoBean.class);
					adapter.add(testBean);
				}
				lv.setAdapter(adapter);
				return;
			case QueryMore:
				return;
			}
			
		}
		
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_act_infolist_sec, null);
		lv = (XListView)view.findViewById(R.id.ListView_fragment);
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(false);
		lv.setOnItemClickListener(this);
		list = new ArrayList<RoomInfoBean>();
		adapter = new RoomListAdapter<RoomInfoBean>(getActivity(),lv);
		adapter.setList(list);
		
		RequestParams params = new RequestParams();
		params.put(QueryParam.PARAM_TRADE_TYPE,QueryParam.TRADE_TYPE_SECONDHAND_HOUSE);
		params.put(QueryParam.PARAM_USER_ID, UserInfo.UserId);
		netRequest(RequestDataType.ReQuery,params);
		
		return view;
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
	public void netRequest(RequestDataType type, RequestParams params) {
		// TODO Auto-generated method stub
		mRequestDataType = type;
		QueryNet.getRoomList(httpHandler, params);
	}


	@Override
	public void toShow() {
		// TODO Auto-generated method stub
		if(list.size()==0){
			RequestParams params = new RequestParams();
			params.put(QueryParam.PARAM_TRADE_TYPE,QueryParam.TRADE_TYPE_SECONDHAND_HOUSE);
			params.put(QueryParam.PARAM_USER_ID, UserInfo.UserId);
			netRequest(RequestDataType.ReQuery,params);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> viewParent, View view, int position, long id) {
		// TODO Auto-generated method stub
		DataManager.getInstance().setRoomInfo(list.get(position-1)); 
		Intent intent = new Intent(getActivity(),InfoActivity.class);
		startActivity(intent);
	}


	

}
