package com.wqcf.kanfang.core;

import android.view.KeyEvent;
import android.widget.Toast;

public class BaseActivity extends android.app.Activity{
	
	private long mPressedTime;
	
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
