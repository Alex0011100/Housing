package com.wqcf.kanfang.ui.adapter;

import java.util.List;

import com.wqcf.kanfang.R;
import com.wqcf.kanfang.ui.adapter.RoomListAdapter.ViewHolder;
import com.wqcf.kanfang.ui.support.AsyncImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OtherRoomAdapter<T> extends XAdapter<T>{

	private AsyncImageLoader asyncImageLoader;  
	private ListView listView;


	public OtherRoomAdapter(Context context,ListView listView) {
		super(context);
		this.listView = listView;
		asyncImageLoader = new AsyncImageLoader(); 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		OtherRoomAdapter.ViewHolder vh = null;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.listviewitem_otherroom,null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		}
		else{
			vh = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	public static class ViewHolder implements android.view.View.OnClickListener{

		public Context context;
		public TextView title,price,desc;
		public ImageView picture;

		public ViewHolder(View v){
			title = (TextView)v.findViewById(R.id.Txt_title_listitem_otherroom);
			price = (TextView)v.findViewById(R.id.Txt_price_listitem_otherroom);
			desc = (TextView)v.findViewById(R.id.Txt_desc_listitem_otherroom);
			picture = (ImageView)v.findViewById(R.id.Img_picture_listitem_otherroom);
			
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

}
