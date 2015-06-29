package com.wqcf.kanfang;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wqcf.kanfang.R.id;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.bean.Vedio;
import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.data.core.GlobalInfo;
import com.wqcf.kanfang.data.core.UserInfo;
import com.wqcf.kanfang.net.core.HttpNetClient;
import com.wqcf.kanfang.net.engine.QueryNet;
import com.wqcf.kanfang.net.param.QueryParam;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.TextHttpResponseHandler;
import com.wqcf.kanfang.opensrc.xlistview.XListView;
import com.wqcf.kanfang.opensrc.xlistview.XListView.IXListViewListener;
import com.wqcf.kanfang.ui.adapter.MyspinnerDropdownAdapter;
import com.wqcf.kanfang.ui.adapter.RoomListAdapter;
import com.wqcf.kanfang.ui.manager.ProgressDialogManager;
import com.wqcf.kanfang.util.BeanUtils;
import com.wqcf.kanfang.util.CodeUtil;
import com.wqcf.kanfang.util.Logger;
import com.wqcf.kanfang.ui.view.ParamTextView;
import com.wqcf.kanfang.ui.view.SlideShowView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData.Item;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoListActivity extends Activity implements OnClickListener,OnItemClickListener,IXListViewListener{

	private ImageView mBackBtn;
	private ParamTextView mTxtLocation,mTxtRoomType,mTxtPrice,mTxtVedio;
	private TextView mTxtCity;
	
	private XListView lv;
	private RoomListAdapter<RoomInfoBean> adapter;
	private ProgressDialog mProgressDailog;
	private Handler mHandler;
	private ArrayList<RoomInfoBean> list;
	private SlideShowView mViewAD;
	private ArrayList<String> mListLocation = new ArrayList<String>();
	private ArrayList<String> mListRoomType = new ArrayList<String>();
	private ArrayList<String> mListPrice = new ArrayList<String>();
	private LinearLayout mLinearLayout;
	private PopupWindow mPopupWindow;
	private MyspinnerDropdownAdapter mDropAdapter;
	private RelativeLayout mRL_PopupBG;
	private ListView myspinnerDropdownListView;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infolist);
		// Intent intent = getIntent();
		initTestData();
		getDataAndInitView();
		initClickListener();
		mProgressDailog = ProgressDialogManager.showPD(this, "Loading", "Please wait...");
		queryRoomList();
	}

	private void initTestData() {
		String[] didians = {"全城", "朝阳", "海淀", "东城", "西城", "崇文", "宣武", "丰台", "通州", "石景山", "房山", "昌平", "大兴", "顺义", "密云", "怀柔", "延庆", "平谷", "门头沟", "燕郊", "北京周边"};
		String[] tingshis = {"不限", "一室", "两室", "三室", "四室", "四室以上"};
		String[] zujins = {"不限", "600元以下", "600-1000元", "1000-1500元", "1000-1500元", "1500-2000元", "2000-3000元", "3000-5000元", "5000-8000元", "8000元以上"};
		Collections.addAll(mListLocation, didians);
		Collections.addAll(mListRoomType, tingshis);
		Collections.addAll(mListPrice, zujins);
	}
	
	private void getDataAndInitView() {
		String title = getIntent().getStringExtra("title");
		((TextView)findViewById(R.id.Txt_title_infolist)).setText(title);
		mTxtCity = (TextView)findViewById(R.id.Txt_acc_infolist_City);
		mTxtCity.setText("北京");
		mHandler = new Handler();
		list = new ArrayList<RoomInfoBean>();
		mBackBtn = (ImageView) findViewById(R.id.Img_back_infolist);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.ll_container);
		mTxtLocation = (ParamTextView) findViewById(R.id.tv_didian);
		mTxtRoomType = (ParamTextView) findViewById(R.id.tv_tingshi);
		mTxtPrice = (ParamTextView) findViewById(R.id.tv_zujin);
		mTxtVedio = (ParamTextView) findViewById(R.id.tv_zhibo);
		lv = (XListView) findViewById(R.id.listVeiw);
		mViewAD = (SlideShowView)findViewById(R.id.ad);
		adapter = new RoomListAdapter<RoomInfoBean>(this,lv);
		adapter.setList(list);
		mDropAdapter = new MyspinnerDropdownAdapter(this, null);
		//lv.setAdapter(adapter);
		lv.setXListViewListener(this);
		lv.setPullLoadEnable(true);
	}



	private void initClickListener() {
		mBackBtn.setOnClickListener(this);
		lv.setOnItemClickListener(this);
		mTxtLocation.setOnClickListener(this);
		mTxtRoomType.setOnClickListener(this);
		mTxtPrice.setOnClickListener(this);
		mTxtVedio.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Img_search_infolist:
			break;
		case R.id.Img_back_infolist:
			finish();
			break;
		case R.id.tv_didian:
			Log.i("info", "tv_didian");
			if(mDropAdapter == null) {
				mDropAdapter = new MyspinnerDropdownAdapter(this, mListLocation);

			}
			mDropAdapter.changeData(mListLocation);
			mDropAdapter.setCallBack(new MyspinnerDropdownAdapter.OnClickCallBack() {

				@Override
				public void callBack(String selectedStr) {
					// TODO Auto-generated method stub
					mTxtLocation.param = selectedStr;
				}
			});
			showWindow(mLinearLayout, mTxtLocation);
			break;
		case R.id.tv_tingshi:
			if(mDropAdapter == null) {
				mDropAdapter = new MyspinnerDropdownAdapter(this, mListRoomType);
			}
			mDropAdapter.changeData(mListRoomType);
			mDropAdapter.setCallBack(new MyspinnerDropdownAdapter.OnClickCallBack() {

				@Override
				public void callBack(String selectedStr) {
					// TODO Auto-generated method stub
					mTxtRoomType.param = selectedStr;
				}
			});
			showWindow(mLinearLayout, mTxtRoomType);
			break;
		case R.id.tv_zujin:
			if(mDropAdapter == null) {
				mDropAdapter = new MyspinnerDropdownAdapter(this, mListPrice);
			}
			mDropAdapter.changeData(mListPrice);
			mDropAdapter.setCallBack(new MyspinnerDropdownAdapter.OnClickCallBack() {

				@Override
				public void callBack(String selectedStr) {
					// TODO Auto-generated method stub
					mTxtPrice.param = selectedStr;
				}
			});
			showWindow(mLinearLayout, mTxtPrice);
			break;
		case R.id.tv_zhibo:
			break;
		}
	}

	public void showWindow(View position, final TextView txt) {  


		mRL_PopupBG = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.myspinner_dropdown, null);  
		myspinnerDropdownListView = (ListView) mRL_PopupBG.findViewById(R.id.myspinner_dropdown_listView);  
		myspinnerDropdownListView.setAdapter(mDropAdapter); 
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;     // 屏幕宽度（像素）
		int height = metric.heightPixels;   // 屏幕高度（像素）
		mPopupWindow = new PopupWindow(position, mLinearLayout.getWidth(), 1000, true);  
		// 设置弹框的宽度为布局文件的宽  
		//        mPopupWindow.setWidth(mLinearLayout.getWidth());  
		//        mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);  
		//        mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 设置一个透明的背景，不然无法实现点击弹框外，弹框消失  
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());  
		// 设置点击弹框外部，弹框消失  
		mPopupWindow.setOutsideTouchable(true);  
		mPopupWindow.setFocusable(true);  
		mPopupWindow.setContentView(mRL_PopupBG);  
		// 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐纵轴不偏移  
		mPopupWindow.showAsDropDown(position, 0, 0);  
		mPopupWindow.setOnDismissListener(new OnDismissListener(){  
			@Override  
			public void onDismiss() {  
				// TODO Auto-generated method stub  
				txt.setTextColor(getResources().getColor(R.color.white));
			}  

		});  
		// listView的item点击事件  
		myspinnerDropdownListView.setOnItemClickListener(new OnItemClickListener() {  

			@Override  
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {  
				// TODO Auto-generated method stub  
				String select = (String)mDropAdapter.getItem(arg2);
				txt.setText(select);// 设置所选的item作为下拉框的标题  
				// 弹框消失  
				mDropAdapter.callBack(select);
				mPopupWindow.dismiss();  
				mPopupWindow = null;  
			}  
		});  

	}  


	private void queryRoomList() {
		RequestParams params = new RequestParams();
		params.put(QueryParam.PARAM_TRADE_TYPE,QueryParam.TRADE_TYPE_RENT_HOUSE);
		params.put(QueryParam.PARAM_USER_ID, UserInfo.UserId);
		if(mTxtLocation.param!=null)
			params.put(QueryParam.PARAM_AREA, mTxtLocation.param);
		if(mTxtPrice.param!=null)
			params.put(QueryParam.PARAM_PRICE_TOP, mTxtPrice.param);
		if(mTxtRoomType.param!=null)
			params.put(QueryParam.PARAM_ROOM_TYPE, mTxtRoomType.param);
		//params.put(key, file)
		QueryNet.getRoomList(httpHandler, params);

	}

	private TextHttpResponseHandler httpHandler = new TextHttpResponseHandler() {

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			// TODO Auto-generated method stub
			if (mProgressDailog != null)
				mProgressDailog.dismiss();
			Toast.makeText(InfoListActivity.this,"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
			onFinishLoad();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			// TODO Auto-generated method stub
			if (mProgressDailog != null)
				mProgressDailog.dismiss();

			Logger.log("response", responseString);

			//ArrayList<RoomInfoBean> list = BeanUtils.jsonToList(responseString, RoomInfoBean.class);

			JSONArray jsonArr = JSON.parseArray(responseString);
			adapter.clear();
			for(int i = 0; i < jsonArr.size();++i){
				JSONObject jobj = (JSONObject)jsonArr.get(i);
				RoomInfoBean testBean = JSON.toJavaObject(jobj, RoomInfoBean.class);
				adapter.add(testBean);
			}
			lv.setAdapter(adapter);
			onFinishLoad();
			//adapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> viewParent, View view, int position, long id) {
		// TODO Auto-generated method stub
		DataManager.getInstance().setRoomInfo(list.get(position-1)); 
		//Intent intent = new Intent(InfoListActivity.this,LiveActivity.class);
		Intent intent = new Intent(InfoListActivity.this,InfoActivity.class);
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		queryRoomList();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				onFinishLoad();
			}

		}, 1000);
	}

	private void onFinishLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime("刚刚");
	}





}
