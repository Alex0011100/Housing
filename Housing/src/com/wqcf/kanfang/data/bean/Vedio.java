package com.wqcf.kanfang.data.bean;

import com.alibaba.fastjson.JSONObject;

public class Vedio {
	public String id;
	public String vedio_Status;
	public String vedio_url;
	public String vedio_time;
	public String vedio_mid;
	public String vedio_history;
	public String address;
	public String price;
	public String resource_type;
	public String vedio;
	public String Image_url;
	
	
	public static Vedio toVedio(JSONObject jo){
		Vedio v = new Vedio();
		v.id = jo.getString("id");
		v.vedio_Status = jo.getString("vedio_Status");
		v.vedio_url= jo.getString("vedio_url");
		v.vedio_time= jo.getString("vedio_time");
		v.vedio_mid= jo.getString("vedio_mid");
		v.vedio_history= jo.getString("vedio_history");
		v.address= jo.getString("address");
		v.price= jo.getString("price");
		v.resource_type= jo.getString("resource_type");
		v.vedio= jo.getString("vedio");
		v.Image_url= jo.getString("Image_url");
		return v;
	}
	
}

