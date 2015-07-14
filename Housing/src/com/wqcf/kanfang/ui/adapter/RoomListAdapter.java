package com.wqcf.kanfang.ui.adapter;

import com.wqcf.kanfang.R;
import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.bean.Video;
import com.wqcf.kanfang.ui.support.AsyncImageLoader;
import com.wqcf.kanfang.ui.support.AsyncImageLoader.ImageCallback;

import com.wqcf.kanfang.util.Logger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
		RoomListAdapter.ViewHolder vh = null;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.listviewitem_roominfo_new,null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		RoomInfoBean roomInfoBean = (RoomInfoBean) getItem(position);
		Video vedio = roomInfoBean.video;
		vh.title.setText(roomInfoBean.title);
		vh.price.setText(roomInfoBean.price+"元");
		vh.desc.setText(roomInfoBean.room_type+"-"+
				roomInfoBean.area+
				roomInfoBean.district+"-"+
				roomInfoBean.housingname+
				roomInfoBean.address);
		
		
		//vh.time.setText();
		String url = roomInfoBean.image_url;
		vh.picture.setTag(url);
		Drawable cachedImage = asyncImageLoader.loadDrawable(url, new ImageCallback() {  
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {  
				ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl); 
				//            	 Logger.log(imageViewByTag,"imageViewByTag");
				//            	 Logger.log(imageDrawable,"imageDrawable");
				if(imageViewByTag!=null)
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


	public static class ViewHolder implements android.view.View.OnClickListener{

		public Context context;
		public TextView title,price,time,desc;
		public ImageView picture,watch;

		public ViewHolder(View v){
			title = (TextView)v.findViewById(R.id.Txt_listitem_infolist_Title);
			price = (TextView)v.findViewById(R.id.Txt_listitem_infolist_Price);
			time = (TextView)v.findViewById(R.id.Txt_listitem_infolist_Time);
			desc = (TextView)v.findViewById(R.id.Txt_listitem_infolist_Desc);
			picture = (ImageView)v.findViewById(R.id.Img_listitem_infolist_Pic);
			watch = (ImageView)v.findViewById(R.id.Img_listitem_infolist_Watch);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}


}
