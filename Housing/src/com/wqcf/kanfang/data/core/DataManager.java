package com.wqcf.kanfang.data.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.bean.Vedio;

public class DataManager {
	
	private static DataManager instance;
	
	private RoomInfoBean curRoomInfo;
	private Vedio vedio;
	
	private DataManager(){
		
	}
	
	public static DataManager getInstance(){
		if(instance == null)
			instance = new DataManager();
		return instance;
	}
	
	public void setRoomInfo(RoomInfoBean roomInfoBean){
		curRoomInfo = roomInfoBean;
		JSONObject jo = (JSONObject) JSON.parse(roomInfoBean.vedio);
	}
	public RoomInfoBean getRoomInfo(){
		return curRoomInfo;
	}
}