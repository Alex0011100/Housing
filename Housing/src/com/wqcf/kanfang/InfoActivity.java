package com.wqcf.kanfang;

import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.ui.view.SlideShowView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class InfoActivity extends Activity implements OnClickListener{

	private ImageView img;
	private TextView mTxtTitle;
	private Button live_btn,info_btn;
	private SlideShowView mViewRoomPic;
	private RelativeLayout mRlayoutOrder,mRlayoutWatch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		initView();
		setView();
		initClickListener();
		//playVideo();
	}
	private void initView(){
		mTxtTitle = (TextView)findViewById(R.id.Txt_act_info_Title);
		mRlayoutOrder = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_Zhongjie);
		mViewRoomPic = (SlideShowView)findViewById(R.id.SlideView_act_info_Pic);
		mRlayoutWatch = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_watch);
	}
	private void setView(){
		RoomInfoBean room = DataManager.getInstance().getRoomInfo();
		mTxtTitle.setText(room.title);
	}	

	private void initClickListener(){
		mRlayoutOrder.setOnClickListener(this);
		mRlayoutWatch.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.RLayout_act_info_Bottom_Zhongjie:
			startActivity(new Intent(this,OrderActivity.class));
			return;
		case R.id.RLayout_act_info_Bottom_watch:
			startActivity(new Intent(this,LiveActivity.class));
			return;
		}

	}
	
//	private void playVideo(){
//		String strURL = DataManager.getInstance().getRoomInfo().vedio.vedio_url;
//		Uri uri = Uri.parse(strURL);
//		mVV.setVideoURI(uri);
//		mVV.start();
//	}

}
