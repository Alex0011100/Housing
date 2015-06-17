package com.wqcf.kanfang.ui.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class XAdapter<T> extends BaseAdapter{

	protected ArrayList<T> list;
	protected LayoutInflater layoutInflater;
	protected Context context;
	
	public XAdapter(Context context){
		
		this.context = context;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setList(ArrayList<T> list){
//		if(list.size()%2!=0)
//			this.list = null;
		this.list = list;
	}
	
	public ArrayList<T> getDataList(){
		return list;
	}
	
	public void clear() {
		list.clear();
	}
	
	public void appendToList(T t, boolean isClearOld) {
		if (t == null) {
			return;
		}
		if (isClearOld) {
			list.clear();
		}
		list.add(t);
	}

	public void appendToList(List<T> l, boolean isClearOld) {
		if (list == null) {
			return;
		}
		if (isClearOld) {
			list.clear();
		}
		list.addAll(l);
	}

	public void appendToListTop(List<T> l, boolean isClearOld) {
		if (list == null) {
			return;
		}
		if (isClearOld) {
			list.clear();
		}
		list.addAll(0, l);
	}

	public void appendToTopList(T t, boolean isClearOld) {
		if (t == null) {
			return;
		}
		if (isClearOld) {
			list.clear();
		}
		list.add(0, t);
	}
	
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (list == null) {
			return null;
		}
		if (position > list.size() - 1) {
			return null;
		}
		//Log.d("getItem","getItem");
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		//Log.d("getItemId","getItemId");
		return position;
	}

}
