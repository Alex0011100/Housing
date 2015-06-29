package com.wqcf.kanfang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	
	private TextView mClkTxtLogin,mClkTxtRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initListener();
	}
	private void initView(){
		mClkTxtLogin = (TextView)findViewById(R.id.Txt_act_login_Login);
		mClkTxtRegister = (TextView)findViewById(R.id.Txt_act_login_Register);
	}
	private void initListener(){
		mClkTxtLogin.setOnClickListener(this);
		mClkTxtRegister.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Txt_act_login_Login:
			startActivity(new Intent(LoginActivity.this,MainActivity.class));
			LoginActivity.this.finish();
			break;
		case R.id.Txt_act_login_Register:
			startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
			break;
		}
	}
	
}
