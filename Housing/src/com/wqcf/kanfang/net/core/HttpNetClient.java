package com.wqcf.kanfang.net.core;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.wqcf.kanfang.opensrc.asynchttp.AsyncHttpClient;
import com.wqcf.kanfang.opensrc.asynchttp.JsonRequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.RequestParams;
import com.wqcf.kanfang.opensrc.asynchttp.ResponseHandlerInterface;
import com.wqcf.kanfang.util.BeanUtils;
import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class HttpNetClient extends AsyncHttpClient{
	
	private static HttpNetClient mClient;
	private static final String CONTENT_TYPE = "application/json;charset=utf-8";
	
	public HttpNetClient(){
		addHeader("Accept", "application/json");
		addHeader("charset", "UTF-8");
		this.setTimeout(5000);
	}
	
	public static HttpNetClient getInstance(){
		if(mClient == null){
			mClient = new HttpNetClient();
			//mClient..setCookieStore(new PersistentCookieStore(AfastApplication.getApplication()));
		}
		return mClient;
	}
	public void get(Context context,String url,Object header,Object criteria,ResponseHandlerInterface responseHandler) {
		get(context, url, BeanUtils.object2map(header), criteria, responseHandler);
	}
	
	public void get(Context context,String url, Map<String, String> headerMap, Object criteria,ResponseHandlerInterface responseHandler) {
		get(context, url, headerMap, BeanUtils.object2map(criteria), responseHandler);
	}
	
	public void get(Context context,String url, Map<String, String> headerMap, Map<String, String> body,ResponseHandlerInterface responseHandler) {
		get(context,url,map2headerArray(headerMap),new JsonRequestParams(body),responseHandler);		
		//debug(url,headerMap, body);
	}

	private void post(Context context, String url, Object header, Object criteria,
			ResponseHandlerInterface responseHandler) {
		post(context, url, BeanUtils.object2map(header), criteria, responseHandler);
	}
	
	private void post(Context context, String url, Map<String, String> headerMap, Object criteria,
			ResponseHandlerInterface responseHandler) {
 		post(context, url, headerMap, BeanUtils.object2map(criteria), responseHandler);
	}

	private void post(Context context, String url, Map<String, String> headerMap,
			Map<String, String> body, ResponseHandlerInterface responseHandler) {
		post(context, url, map2headerArray(headerMap), new JsonRequestParams(body), CONTENT_TYPE,responseHandler);
		//debug(url, headerMap, body);
	}
	
	public void post(Context context, String url,Map<String, String> headerMap, RequestParams params, ResponseHandlerInterface responseHandler){
		if(headerMap ==null){
			post(context, url, params, responseHandler);
		}else{
			post(context, url, headerMap, (Object)params, responseHandler);
		}
		
		//debug(url, headerMap, params.toString());
	}
	
	private void post(Context context, String url, Map<String, String> headerMap,
			StringEntity body, String contentType, ResponseHandlerInterface responseHandler) {
		post(context, url, map2headerArray(headerMap), body, CONTENT_TYPE,responseHandler);
		//debug(url, headerMap, BeanUtils.object2map(body));
	}
	
	// 处理串行的合并接口 ：{“code0”:{“”:””,””:””,””:””},”code1”:{“”:””,””:””,””:””}}
	private void post(Context context, String url, Object header,
			ResponseHandlerInterface responseHandler, Object... criteriaArray) {
		post(context, url, BeanUtils.object2map(header), responseHandler, criteriaArray);
	}

	// 处理串行的合并接口   类似于Body格式：{“code0”:{“”:””,””:””,””:””},”code1”:{“”:””,””:””,””:””}}
	private void post(Context context, String url, Map<String, String> headerMap,
			ResponseHandlerInterface responseHandler, Object... criterias) {
		BocopBodyEntity entity = criteriaArr2HttpEntity(criterias);
		post(context, url, map2headerArray(headerMap), entity, CONTENT_TYPE, responseHandler);
		//debug(url, headerMap, entity.getBody());
	}

	public void put(Context context, String url, Object header, Object criteria,ResponseHandlerInterface responseHandler) {
		put(context, url, BeanUtils.object2map(header), criteria,responseHandler);
	}
	
	public void put(Context context, String url, Map<String, String> header, Object criteria,ResponseHandlerInterface responseHandler) {
		BocopBodyEntity entity = criteria2HttpEntity(criteria);
		put(context, url, map2headerArray(header), entity, CONTENT_TYPE, responseHandler);
		//debug(url, header, entity.getBody());
	}

	// 处理串行的合并接口 ：{“code0”:{“”:””,””:””,””:””},”code1”:{“”:””,””:””,””:””}}
	public void put(Context context, String url, Object header,
			ResponseHandlerInterface responseHandler, Object... criteriaArray) {
		put(context, url, BeanUtils.object2map(header), responseHandler, criteriaArray);
	}

	// 处理串行的合并接口   类似于Body格式：{“code0”:{“”:””,””:””,””:””},”code1”:{“”:””,””:””,””:””}}
	public void put(Context context, String url, Map<String, String> headerMap,
			ResponseHandlerInterface responseHandler, Object... criterias) {
		BocopBodyEntity entity = criteriaArr2HttpEntity(criterias);
		put(context, url, map2headerArray(headerMap), entity, CONTENT_TYPE, responseHandler);
		//debug(url, headerMap, entity.getBody());
	}

	private Header[] map2headerArray(Map<String, String> headerMap) {
		Header[] headers = null;
		if (headerMap != null) {
			headers = new Header[headerMap.size()];
			int i = 0;
			for (Map.Entry<String, String> headerKeyValue : headerMap.entrySet()) {
				headers[i++] = new BasicHeader(headerKeyValue.getKey(), headerKeyValue.getValue());
			}
		}
		return headers;
	}

	//用于串行接口合并
	private BocopBodyEntity criteriaArr2HttpEntity(Object... criterias) {
		BocopBodyEntity entity = null;
		if (criterias != null) {
			JsonObject jsonObject = new JsonObject();
			for (int i = 0; i < criterias.length; i++) {
				Object criteria = criterias[i];
				jsonObject.addProperty("code" + i, new Gson().toJson(criteria));
			}
			// 绑定到请求 Entry
			try {
				entity = new BocopBodyEntity(jsonObject.toString(), HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	//用于串行接口合并
	private BocopBodyEntity criteria2HttpEntity(Object criteria) {
		if (criteria == null)
			return null;
		BocopBodyEntity entity = null;
		// 绑定到请求 Entry
		try {
			entity = new BocopBodyEntity(new Gson().toJson(criteria), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}

	// 打印日志
//	private void debug(String url, String header, String body) {
//		Logger.d(TAG, "request url---->" + url);
//		Logger.d(TAG, "header--->" + header);
//		Logger.d(TAG, "body--->" + body);
//	}

	// 打印日志
//	private void debug(String url, Map header, Map body) {
//		String headerStr = "";
//		String bodyStr = "";
//		if (header != null) {
//			headerStr = header.toString();
//		}
//		if (body != null) {
//			bodyStr = body.toString();
//		}
//		debug(url, headerStr, bodyStr);
//	}

	// 打印日志
//	private void debug(String url, Map header, String body) {
//		String headerStr = "";
//		if (header != null) {
//			headerStr = header.toString();
//		}
//		debug(url, headerStr, body);
//	}
//	// 打印日志
//	private void debug(String url, String header, Map body) {
//		String bodyStr = "";
//		if (body != null) {
//			bodyStr = body.toString();
//		}
//		debug(url, header, bodyStr);
//	}


	private class BocopBodyEntity extends StringEntity {
		private String body;

		public BocopBodyEntity(String s, String charset)
				throws UnsupportedEncodingException {
			super(s, charset);
			body = s;
		}

		public BocopBodyEntity(String s) throws UnsupportedEncodingException {
			super(s);
			body = s;
		}

		public String getBody() {
			return body;
		}
	}
	
	@Override
	public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
		super.cancelRequests(context, mayInterruptIfRunning);
	}
	
	
	

}
