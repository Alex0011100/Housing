package com.wqcf.kanfang.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BeanUtils {
	/**
	 * 把json字符串，转为指定的对象，转换失败则抛出com.google.gson.JsonSyntaxException异常
	 * @param response json字符串
	 * @param clazz 转换的类型
	 * @return
	 */
//	public final static <T> T jsonToObject(String response,Class<T> clazz) {
//		//      Gson gson = new Gson();
//		//      return (T)gson.fromJson(response, clazz);
//		//Logger.i("解析前的Json数据: ", response);
//		JSONObject json;
//		String res = null;
//		try {
//			json = new JSONObject(response);
//			res = json.getString("res");
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		return JSON.parseObject(res, clazz);
//	}

	/**
	 * 把封装了上传参数的obj，转化为HashMap
	 * key为obj字段的名字，value为obj字段的值，
	 * obj中应该全部使用String类型转化中会忽略掉所有非String类型的字段
	 * @param obj  封装了上传参数
	 * @return 转化后的HashMap
	 */
	public static HashMap<String, String> object2map(Object obj) {
		if (obj == null)
			return new HashMap<String, String>();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		try {
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getType() == String.class && field.get(obj) != null)  {
					dataMap.put(field.getName(), (String)field.get(obj));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dataMap;
	}	

	public static <T> ArrayList<T> jsonToList(String response,Class<T> clazz){

		//JSONObject json = JSON.parseObject(response);
		//String res = json.getString("res");

		JSONArray jsonArr = JSON.parseArray(response);
		ArrayList<T> list = new ArrayList<T>();
		for(int i = 0; i < jsonArr.size();i++){
			JSONObject jobj = (JSONObject)jsonArr.get(i);
			T testBean = JSON.toJavaObject(jobj, clazz);
			list.add(testBean);
		}
		return list;
	}

}
