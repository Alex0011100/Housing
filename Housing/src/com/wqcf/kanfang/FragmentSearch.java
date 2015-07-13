package com.wqcf.kanfang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wqcf.kanfang.core.BaseFragment;

public class FragmentSearch extends BaseFragment{
	
	private TextView mClkTxtClear;
	private ImageView mImgSearch;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_act_infolist_search, null);
		
		return view;
	}
	
	private void initView(View view){
		mClkTxtClear = (TextView)view.findViewById(R.id.Txt_frag_search_ClearHistory);
		mImgSearch = (ImageView)view.findViewById(R.id.Img_frag_search_Search);
	}
	
	@Override
	public void call() {
		// TODO Auto-generated method stub
		
	}

}
