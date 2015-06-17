package com.wqcf.kanfang.net.engine;

import android.content.Context;


import com.wqcf.kanfang.net.core.HttpNetClient;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.ResponseHandlerInterface;

public class QueryNet {
	
	
	public static void getRoomList(ResponseHandlerInterface responseHandler,RequestParams params){
		
		HttpNetClient.getInstance().get(NetInfo.URL_ROOMLIST,params, responseHandler);
	}
	
	public static void getRoomInfo(ResponseHandlerInterface responseHandler){
		
		HttpNetClient.getInstance().get(NetInfo.URL_ROOMINFO, responseHandler);
		
	}
	
	public static void postRoomList(ResponseHandlerInterface responseHandler){
		HttpNetClient.getInstance().post(NetInfo.URL_ROOMLIST, null, responseHandler);
	}
	
	public static void postRoomList(Context context , ResponseHandlerInterface responseHandler,String postContent){
		HttpNetClient.getInstance().post(context, NetInfo.URL_ROOMLIST, null, postContent, responseHandler);
	}
	
	public static void test(ResponseHandlerInterface responseHandler){
		HttpNetClient.getInstance().get("www.baidu.com", responseHandler);
	}
	
	
	
	
}
