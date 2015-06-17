package com.wqcf.kanfang.ui.manager;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogManager {
	
	public static ProgressDialog showPD(Context context,String title,String message){
		return ProgressDialog.show(context, title, message, true, false);
	}
	
}
