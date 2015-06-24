package com.wqcf.kanfang;

import java.util.ArrayList;

import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.util.Logger;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.VideoView;

public class LiveActivity extends Activity implements OnClickListener{
	
	private ListView lv;
	private ArrayAdapter<String> adapter;
	private EditText edt;
	private LinearLayout linear_sendMsg;
	private Button btn;
	private ArrayList<String> ary;
	private VideoView vv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live);
		initView();
		initClickListener();
		
	}
	private void initView(){
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
			break;
		}
	}
	
	private void playVideo(){
		String strURL = DataManager.getInstance().getRoomInfo().vedio.vedio_url;
		Uri uri = Uri.parse(strURL);
		vv.setVideoURI(uri);
		vv.start();
	}
	
	
	
}
