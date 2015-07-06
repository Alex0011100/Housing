package com.wqcf.kanfang.interfaze;

public interface IListViewNotify {
	/*
	 * listview的通知接口。
	 * 网络模块收到response后，回调此接口来刷新其他class中的listview。
	 * 网络模块和其他class须持有同一个list对象。
	 */
	/**
	 * refresh or lodemore the listview
	 */
	public abstract void onSuccess(ListViewResetType type);
	/**
	 * notify the listview this module is fail
	 */
	public  abstract void onFail();
	
	public enum ListViewResetType{Refresh,Loadmore}
}
