package com.wqcf.kanfang;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private ImageView img_zufang,img_ershou,img_xinfang,img_qifang,mImgUser;
	private TextView mTxtCity,mClkTxtLogin,mClkTxtReg;
	private DrawerLayout mDrawerLayout;
	private long mPressedTime;
	
	
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
    	mDrawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout_act_main);
    	mTxtCity = (TextView)findViewById(R.id.Txt_act_main_City);
    	mImgUser = (ImageView)findViewById(R.id.Img_act_main_User);
    	mClkTxtLogin = (TextView)findViewById(R.id.Txt_drawer_userinfo_Login);
    	mClkTxtReg = (TextView)findViewById(R.id.Txt_drawer_userinfo_Reg);
    }
    private void initListener(){
    	img_zufang.setOnClickListener(this);
    	img_ershou.setOnClickListener(this);
    	img_xinfang.setOnClickListener(this);
    	img_qifang.setOnClickListener(this);
    	mImgUser.setOnClickListener(this);
    	mClkTxtLogin.setOnClickListener(this);
    	mClkTxtReg.setOnClickListener(this);
    }


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.Img_main_zufang:
			toInfoList("租房");
			return;
		case R.id.Img_main_ershou:
			toInfoList("二手");
			return;
		case R.id.Img_main_xinfang:
			toInfoList("新房");
			return;
		case R.id.Img_main_qifang:
			toInfoList("期房");
			return;
		case R.id.Img_act_main_User:
			mDrawerLayout.openDrawer(Gravity.RIGHT);
			return;
		case R.id.Txt_drawer_userinfo_Login:
			startActivity(new Intent(this,LoginActivity.class));
			return;
		case R.id.Txt_drawer_userinfo_Reg:
			startActivity(new Intent(this,RegisterActivity.class));
			return;
		}
	}
	
	private void toInfoList(String title){
		Intent intent = new Intent(MainActivity.this,InfoListActivity.class);
		intent.putExtra("title",title);
		startActivity(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mPressedTime == 0
					|| System.currentTimeMillis() - mPressedTime > 2000) {
				mPressedTime = System.currentTimeMillis();
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				return false;
			} else if (mPressedTime > 0
					&& System.currentTimeMillis() - mPressedTime < 2000) {
				System.exit(0);
				return false;
			}
		}
		return false;
	}

    
}
