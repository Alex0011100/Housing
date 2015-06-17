package com.wqcf.kanfang.ui.support;

import com.wqcf.kanfang.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder implements android.view.View.OnClickListener{
	
	public Context context;
	public TextView title,price,time,desc;
	public ImageView picture,watch;
	
	public ViewHolder(View v){
		title = (TextView)v.findViewById(R.id.Txt_title_listitem_infolist);
		price = (TextView)v.findViewById(R.id.Txt_price_listitem_infolist);
		time = (TextView)v.findViewById(R.id.Txt_time_listitem_infolist);
		desc = (TextView)v.findViewById(R.id.Txt_desc_listitem_infolist);
		picture = (ImageView)v.findViewById(R.id.Img_picture_listitem_infolist);
		watch = (ImageView)v.findViewById(R.id.Img_watch_listitem_infolist);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
}
