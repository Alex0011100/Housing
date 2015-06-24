package com.wqcf.kanfang.ui.adapter;

import java.util.ArrayList;

import com.wqcf.kanfang.R;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.ui.support.AsyncImageLoader;
import com.wqcf.kanfang.ui.support.AsyncImageLoader.ImageCallback;


import com.wqcf.kanfang.ui.support.ViewHolder;
import com.wqcf.kanfang.util.Logger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class RoomListAdapter<T> extends XAdapter<T>{
	
	private AsyncImageLoader asyncImageLoader;  
	private ListView listView;
	
	public RoomListAdapter(Context context,ListView listView) {
		super(context);
		this.listView = listView;
		asyncImageLoader = new AsyncImageLoader(); 
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.listviewitem_info,null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		RoomInfoBean roomInfoBean = (RoomInfoBean) getItem(position);
		vh.title.setText(roomInfoBean.title);
		vh.price.setText(roomInfoBean.price+"元");
		vh.desc.setText(roomInfoBean.room_type+"室-"+roomInfoBean.street+"街-"+roomInfoBean.address+"门");
		String url = roomInfoBean.image_url;
		vh.picture.setTag(url);
		Drawable cachedImage = asyncImageLoader.loadDrawable(url, new ImageCallback() {  
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {  
            	 ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl); 
            	 imageViewByTag.setImageDrawable(imageDrawable);
            }  
        });  
		if (cachedImage == null) {  
            vh.picture.setImageResource(R.drawable.logo);  
        }else{  
            vh.picture.setImageDrawable(cachedImage);  
        }  


		return convertView;
	}



}
