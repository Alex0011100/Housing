package com.wqcf.kanfang;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener{
	
	private ImageView img_zufang,img_ershou,img_xinfang,img_qifang;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }
    
    
    private void initView(){
    	img_zufang = (ImageView)findViewById(R.id.Img_main_zufang);
    	img_ershou = (ImageView)findViewById(R.id.Img_main_ershou);
    	img_xinfang = (ImageView)findViewById(R.id.Img_main_xinfang);
    	img_qifang = (ImageView)findViewById(R.id.Img_main_qifang);
    }
    private void initListener(){
    	img_zufang.setOnClickListener(this);
    	img_ershou.setOnClickListener(this);
    	img_xinfang.setOnClickListener(this);
    	img_qifang.setOnClickListener(this);
    }


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.Img_main_zufang:
			toInfoList("租房");
			break;
		case R.id.Img_main_ershou:
			toInfoList("二手");
			break;
		case R.id.Img_main_xinfang:
			toInfoList("新房");
			break;
		case R.id.Img_main_qifang:
			toInfoList("期房");
			break;
		}
	}
	
	private void toInfoList(String title){
		Intent intent = new Intent(MainActivity.this,InfoListActivity.class);
		intent.putExtra("title",title);
		startActivity(intent);
	}

    
}
