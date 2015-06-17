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
import com.wqcf.kanfang.view.SlideShowView;

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

	private ImageView back, search;
	private TextView tv1,tv2,tv3,tv4;
	private XListView lv;
	private RoomListAdapter<RoomInfoBean> adapter;
	private ProgressDialog pd;
	private Handler mHandler;
	private ArrayList<RoomInfoBean> list;
	private SlideShowView advert;
	private ArrayList<String> listData1 = new ArrayList<String>();
	private ArrayList<String> listData2 = new ArrayList<String>();
	private ArrayList<String> listData3 = new ArrayList<String>();
	private LinearLayout llContainer;
	private PopupWindow popupWindow;
	private MyspinnerDropdownAdapter adpter;
	private RelativeLayout layout;
	private ListView myspinnerDropdownListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infolist);
		// Intent intent = getIntent();
		initTestData();
		initView();
		initClickListener();
		pd = ProgressDialogManager.showPD(this, "Loading", "Please wait...");
		queryRoomList();
	}

	private void initTestData() {
		String[] didians = {"全城", "朝阳", "海淀", "东城", "西城", "崇文", "宣武", "丰台", "通州", "石景山", "房山", "昌平", "大兴", "顺义", "密云", "怀柔", "延庆", "平谷", "门头沟", "燕郊", "北京周边"};
		String[] tingshis = {"不限", "一室", "两室", "三室", "四室", "四室以上"};
		String[] zujins = {"不限", "600元以下", "600-1000元", "1000-1500元", "1000-1500元", "1500-2000元", "2000-3000元", "3000-5000元", "5000-8000元", "8000元以上"};
		Collections.addAll(listData1, didians);
		Collections.addAll(listData2, tingshis);
		Collections.addAll(listData3, zujins);
	}

	private void initView() {
		mHandler = new Handler();
		list = new ArrayList<RoomInfoBean>();
		back = (ImageView) findViewById(R.id.Img_back_infolist);
		search = (ImageView) findViewById(R.id.Img_search_infolist);
		llContainer = (LinearLayout) findViewById(R.id.ll_container);
		tv1 = (TextView) findViewById(R.id.tv_didian);
		tv2 = (TextView) findViewById(R.id.tv_tingshi);
		tv3 = (TextView) findViewById(R.id.tv_zujin);
		tv4 = (TextView) findViewById(R.id.tv_zhibo);
		lv = (XListView) findViewById(R.id.listVeiw);
		advert = (SlideShowView)findViewById(R.id.ad);
		adapter = new RoomListAdapter<RoomInfoBean>(this,lv);
		adpter = new MyspinnerDropdownAdapter(this, null);
		//lv.setAdapter(adapter);
		lv.setXListViewListener(this);
		lv.setPullLoadEnable(true);
	}



	private void initClickListener() {
		back.setOnClickListener(this);
		search.setOnClickListener(this);
		lv.setOnItemClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
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
			if(adpter == null) {
				adpter = new MyspinnerDropdownAdapter(this, listData1);
			}
			adpter.changeData(listData1);
			showWindow(llContainer, tv1);
			break;
		case R.id.tv_tingshi:
			if(adpter == null) {
				adpter = new MyspinnerDropdownAdapter(this, listData2);
			}
			adpter.changeData(listData2);
			showWindow(llContainer, tv2);
			break;
		case R.id.tv_zujin:
			if(adpter == null) {
				adpter = new MyspinnerDropdownAdapter(this, listData3);
			}
			adpter.changeData(listData3);
			showWindow(llContainer, tv3);
			break;
		case R.id.tv_zhibo:
			break;
		}
	}
	
	public void showWindow(View position, final TextView txt) {  
		
		
        layout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.myspinner_dropdown, null);  
        myspinnerDropdownListView = (ListView) layout.findViewById(R.id.myspinner_dropdown_listView);  
        myspinnerDropdownListView.setAdapter(adpter); 
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        popupWindow = new PopupWindow(position, llContainer.getWidth(), 1000, true);  
        // 设置弹框的宽度为布局文件的宽  
//        popupWindow.setWidth(llContainer.getWidth());  
//        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);  
//        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失  
        popupWindow.setBackgroundDrawable(new BitmapDrawable());  
        // 设置点击弹框外部，弹框消失  
        popupWindow.setOutsideTouchable(true);  
        popupWindow.setFocusable(true);  
        popupWindow.setContentView(layout);  
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐纵轴不偏移  
        popupWindow.showAsDropDown(position, 0, 0);  
        popupWindow.setOnDismissListener(new OnDismissListener(){  
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
                txt.setText((String)adpter.getItem(arg2));// 设置所选的item作为下拉框的标题  
                // 弹框消失  
                popupWindow.dismiss();  
                popupWindow = null;  
            }  
        });  
  
    }  
  

	private void queryRoomList() {
		RequestParams params = new RequestParams();
		params.put(QueryParam.PARAM_TRADE_TYPE,QueryParam.TRADE_TYPE_RENT_HOUSE);
		params.put(QueryParam.PARAM_USER_ID, UserInfo.UserId);
		QueryNet.getRoomList(httpHandler, params);

	}

	private TextHttpResponseHandler httpHandler = new TextHttpResponseHandler() {

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			// TODO Auto-generated method stub
			if (pd != null)
				pd.dismiss();
			Toast.makeText(InfoListActivity.this,"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
			onFinishLoad();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			// TODO Auto-generated method stub
			if (pd != null)
				pd.dismiss();

			Logger.log("response", responseString);
			
			//ArrayList<RoomInfoBean> list = BeanUtils.jsonToList(responseString, RoomInfoBean.class);
			
			JSONArray jsonArr = JSON.parseArray(responseString);
			list.clear();
			for(int i = 0; i < jsonArr.size();++i){
				JSONObject jobj = (JSONObject)jsonArr.get(i);
				RoomInfoBean testBean = JSON.toJavaObject(jobj, RoomInfoBean.class);
				list.add(testBean);
			}
			adapter.setList(list);
			lv.setAdapter(adapter);
			onFinishLoad();
			//adapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> viewParent, View view, int position, long id) {
		// TODO Auto-generated method stub
		DataManager.getInstance().setRoomInfo(list.get(position));
		Intent intent = new Intent(InfoListActivity.this,LiveActivity.class);
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
