package com.wqcf.kanfang.util;

import android.util.Log;

public class Logger {
	
	public static void log(String s,String str){
		if(str!=null){
			if(!str.equals(""))
				Log.d(s,str);
			else
				Log.d(s,"\"\"");
		}else{
			Log.d(s,"null");
		}
	}
	
}
