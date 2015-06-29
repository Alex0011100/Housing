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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class InfoActivity extends Activity implements OnClickListener{

	private ImageView img;
	private TextView mTxtTitle;
	private Button live_btn,info_btn;
	private SlideShowView mViewRoomPic;
	private VideoView mVV;
	private RelativeLayout mRlayoutOrder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		initView();
		setView();
		initClickListener();
		playVideo();
	}
	private void initView(){
		mVV = (VideoView) findViewById(R.id.VideoView_acc_info_live);
		mTxtTitle = (TextView)findViewById(R.id.Txt_act_info_Title);
		mRlayoutOrder = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_Zhongjie);
	}
	private void setView(){
		RoomInfoBean room = DataManager.getInstance().getRoomInfo();
		mTxtTitle.setText(room.title);
	}	

	private void initClickListener(){
		mRlayoutOrder.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.RLayout_act_info_Bottom_Zhongjie:
			startActivity(new Intent(this,OrderActivity.class));
			break;
		}

	}
	
	private void playVideo(){
		String strURL = DataManager.getInstance().getRoomInfo().vedio.vedio_url;
		Uri uri = Uri.parse(strURL);
		mVV.setVideoURI(uri);
		mVV.start();
	}

}
