package com.wqcf.kanfang;

import java.util.ArrayList;

import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.util.Logger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class LiveActivity extends Activity implements OnClickListener{
	
	private ListView lv;
	private ArrayAdapter<String> adapter;
	private EditText edt;
	private LinearLayout linear_sendMsg;
	private Button btn;
	private ArrayList<String> ary;
	private VideoView vv;
	
	private RelativeLayout mRlayoutFav,mRlayoutOrder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live);
		initView();
		initClickListener();
		
	}
	private void initView(){
		mRlayoutFav = (RelativeLayout)findViewById(R.id.Rlayout_act_live_Favourite);
		mRlayoutOrder = (RelativeLayout)findViewById(R.id.Rlayout_act_live_Order);
		lv = (ListView) findViewById(R.id.ListView_act_live_message);
		edt = (EditText) findViewById(R.id.Edt_act_live_userMessage);
		btn = (Button)findViewById(R.id.Btn_act_live_send);
		vv = (VideoView)findViewById(R.id.VideoView);
		ary = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,R.layout.listviewitem_chat,ary);
		lv.setAdapter(adapter);
	}
	private void initClickListener(){
		btn.setOnClickListener(this);
		mRlayoutOrder.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.Btn_act_live_send:
			Logger.log("sendMsg","sendMsg");
			ary.add("我："+edt.getText().toString());
			edt.clearFocus();
			edt.setText("");
			adapter.notifyDataSetChanged();
			return;
		case R.id.Rlayout_act_live_Order:
			startActivity(new Intent(this,OrderActivity.class));
			return;
		}
	}
	
	private void playVideo(){
		String strURL = DataManager.getInstance().getRoomInfo().video.video_url;
		Uri uri = Uri.parse(strURL);
		vv.setVideoURI(uri);
		vv.start();
	}
	
	
	
}
