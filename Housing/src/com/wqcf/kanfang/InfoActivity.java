package com.wqcf.kanfang;

import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.ui.view.SlideShowView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class InfoActivity extends Activity implements OnClickListener{

	private ImageView img;
	private TextView txt;
	private Button live_btn,info_btn;
	private SlideShowView mViewRoomPic;
	private VideoView mVV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		initView();
		//playVideo();
	}
	private void initView(){
		mViewRoomPic = (SlideShowView) findViewById(R.id.Slide_acc_info_RoomPic);
		mVV = (VideoView) findViewById(R.id.VideoView_acc_info_live);
	}

	private void initClickListener(){

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


	}
	
	private void playVideo(){
		String strURL = DataManager.getInstance().getRoomInfo().vedio.vedio_url;
		Uri uri = Uri.parse(strURL);
		mVV.setVideoURI(uri);
		mVV.start();
	}

}
