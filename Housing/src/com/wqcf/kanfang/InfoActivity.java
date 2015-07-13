package com.wqcf.kanfang;

import java.util.ArrayList;

import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.ui.adapter.OtherRoomAdapter;
import com.wqcf.kanfang.ui.view.SlideShowView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class InfoActivity extends Activity implements OnClickListener{

	private ImageView mImgVideo1,
	mImgVideo2,
	mImgVideo3;
	
	
	private TextView mTxtTitle,
	mTxtVideo1,
	mTxtVideo2,
	mTxtVideo3,
	
	mTxtPrice,
	
	mTxtRoomtype,
	mTxtZhuangxiu,
	mTxtFloor,
	mTxtSize,
	mTxtGaikuang,
	mTxtChaoxiang,
	
	mTxtXiaoqu,
	mTxtPosition,
	
	mTxtDesc;
	
	private ListView mLvOtherHouse;
	private SlideShowView mViewRoomPic;
	private RelativeLayout mRlayoutOrder,mRlayoutLive,mRlayoutFav;
	
	private OtherRoomAdapter<RoomInfoBean> adapter;
	private ArrayList<RoomInfoBean> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		initView();
		setView();
		initTestData();
		initClickListener();
		//playVideo();
	}
	private void initView(){
		mTxtTitle = (TextView)findViewById(R.id.Txt_act_info_Title);
		mTxtRoomtype = (TextView)findViewById(R.id.Txt_act_info_Roomtype);
		mTxtPrice = (TextView)findViewById(R.id.Txt_act_info_Price);
		mTxtPosition = (TextView)findViewById(R.id.Txt_act_info_Position);
		mRlayoutOrder = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_Zhongjie);
		mRlayoutFav = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_Favourite);
		mViewRoomPic = (SlideShowView)findViewById(R.id.SlideView_act_info_Pic);
		mRlayoutLive = (RelativeLayout)findViewById(R.id.Llayout_act_info_Bottom);
		mTxtVideo1 = (TextView)findViewById(R.id.Txt_act_info_VideoHistory_1);
		mTxtVideo2 = (TextView)findViewById(R.id.Txt_act_info_VideoHistory_2);
		mTxtVideo3 = (TextView)findViewById(R.id.Txt_act_info_VideoHistory_3);
		mTxtZhuangxiu = (TextView)findViewById(R.id.Txt_act_info_ZhuangXiu);
		mTxtFloor = (TextView)findViewById(R.id.Txt_act_info_Floor);
		mTxtSize = (TextView)findViewById(R.id.Txt_act_info_Size);
		mTxtGaikuang = (TextView)findViewById(R.id.Txt_act_info_GaiKuang);
		mTxtChaoxiang = (TextView)findViewById(R.id.Txt_act_info_ChaoXiang);
		mTxtXiaoqu = (TextView)findViewById(R.id.Txt_act_info_XiaoQu);
		mTxtDesc = (TextView)findViewById(R.id.Txt_act_info_Desc);
		mLvOtherHouse = (ListView)findViewById(R.id.ListView_act_info_OtherHouse);
	}
	private void setView(){
		RoomInfoBean room = DataManager.getInstance().getRoomInfo();
		mTxtTitle.setText(room.title);
		mTxtRoomtype.setText(room.room_type+"室");
		mTxtPosition.setText(room.area+
				room.district+
				room.address+
				room.address);
		mTxtPrice.setText(room.price);
	}	

	private void initClickListener(){
		mRlayoutOrder.setOnClickListener(this);
		mRlayoutLive.setOnClickListener(this);
		mRlayoutFav.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.RLayout_act_info_Bottom_Zhongjie:
			startActivity(new Intent(this,OrderActivity.class));
			return;
		case R.id.Llayout_act_info_Bottom:
			startActivity(new Intent(this,LiveActivity.class));
			return;
		case R.id.RLayout_act_info_Bottom_Favourite:
			
			return;
		}

	}
	
	private void initTestData(){
		adapter = new OtherRoomAdapter<RoomInfoBean>(this, mLvOtherHouse);
		list = new ArrayList<RoomInfoBean>();
		list.add(new RoomInfoBean());
		list.add(new RoomInfoBean());
		adapter.setList(list);
		mLvOtherHouse.setAdapter(adapter);
	}
	
//	private void playVideo(){
//		String strURL = DataManager.getInstance().getRoomInfo().vedio.vedio_url;
//		Uri uri = Uri.parse(strURL);
//		mVV.setVideoURI(uri);
//		mVV.start();
//	}

}
