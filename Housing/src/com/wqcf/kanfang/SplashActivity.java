package com.wqcf.kanfang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkUserInfo();
			}
			
		}, 3000);
	}
	
	private void checkUserInfo(){
		startActivity(new Intent(SplashActivity.this,MainActivity.class));
		finish();
	}


}
