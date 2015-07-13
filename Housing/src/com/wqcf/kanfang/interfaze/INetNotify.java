package com.wqcf.kanfang.interfaze;

import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.TextHttpResponseHandler;

public interface INetNotify {
	
	/*
	 * 网络模块的请求接口。
	 * 若一个class企图使用网络请求，回调此接口使用网络模块的网络通讯
	 * 网络模块和此class须持有同一个list对象。
	 */
	
	/**
	 *  notify the net module to send a http request
	 */
	public abstract void netRequest(TextHttpResponseHandler httpHandler,RequestParams params);
	
	
}
