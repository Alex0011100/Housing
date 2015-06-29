package com.wqcf.kanfang;

import java.util.ArrayList;

import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.ui.adapter.OtherRoomAdapter;
import com.wqcf.kanfang.ui.adapter.RoomListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderActivity extends Activity implements View.OnClickListener{
	
	private ImageView mImgPhoto,
					  mImgAlibabaPayChoose,
					  mImgAlibabaPayUnchoose,
					  mImgWechatPayChoose,
					  mImgWechatPayUnchoose,
					  mImgUnionPayChoose,
					  mImgUnionPayUnchoose;
	private TextView mTxtName,
	                 mTxtCompany,
	                 mTxtShop,
	                 mTxtPhone;
	private RelativeLayout mRlayoutAlibabaPay,
	                       mRlayoutWechatPay,
	                       mRlayoutUnionPay;
	private Switcher mSwitcherAlibabaPay,
					 mSwitcherWechatPay,
					 mSwitcherUnionPay;
	private ListView mListViewRooms;
	private OtherRoomAdapter<RoomInfoBean> adapter;
	private ArrayList<RoomInfoBean> list;
	
	private Payment mPayment;
	public enum Payment{AlibabaPay,WechatPay,UnionPay}
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initPayment();
        initTestData();
        initListener();
    }
	
	private void initTestData(){
		mImgPhoto.setImageResource(R.drawable.beauty);
		mTxtName.setText("梦琳");
		mTxtCompany.setText("时代地产");
		mTxtShop.setText("国贸店");
		mTxtPhone.setText("18610249072");
		
		adapter = new OtherRoomAdapter<RoomInfoBean>(this, mListViewRooms);
		list = new ArrayList<RoomInfoBean>();
		list.add(new RoomInfoBean());
		list.add(new RoomInfoBean());
		adapter.setList(list);
		mListViewRooms.setAdapter(adapter);
	}
	
	private void initView(){
		mImgPhoto = (ImageView)findViewById(R.id.Img_act_order_Photo);
		mImgAlibabaPayChoose = (ImageView)findViewById(R.id.Img_act_order_AlibabaPay_choose);
		mImgAlibabaPayUnchoose = (ImageView)findViewById(R.id.Img_act_order_AlibabaPay_unchoose);
		mImgWechatPayChoose = (ImageView)findViewById(R.id.Img_act_order_WechatPay_choose);
		mImgWechatPayUnchoose = (ImageView)findViewById(R.id.Img_act_order_WechatPay_unchoose);
		mImgUnionPayChoose = (ImageView)findViewById(R.id.Img_act_order_UnionPay_choose);
		mImgUnionPayUnchoose = (ImageView)findViewById(R.id.Img_act_order_UnionPay_unchoose);
		
		mTxtName = (TextView)findViewById(R.id.Txt_act_order_Name);
		mTxtCompany = (TextView)findViewById(R.id.Txt_act_order_Company);
		mTxtShop = (TextView)findViewById(R.id.Txt_act_order_Shop);
		mTxtPhone = (TextView)findViewById(R.id.Txt_act_order_Phone);
		
		mRlayoutAlibabaPay = (RelativeLayout)findViewById(R.id.Rlayout_act_order_AlibabaPay);
		mRlayoutWechatPay = (RelativeLayout)findViewById(R.id.Rlayout_act_order_WeChatPay);
		mRlayoutUnionPay = (RelativeLayout)findViewById(R.id.Rlayout_act_order_UnionPay);
		
		mListViewRooms = (ListView)findViewById(R.id.ListView_act_order_rooms);
		
		mSwitcherAlibabaPay = new Switcher(mImgAlibabaPayChoose,mImgAlibabaPayUnchoose);
		mSwitcherWechatPay = new Switcher(mImgWechatPayChoose,mImgWechatPayUnchoose);
		mSwitcherUnionPay = new Switcher(mImgUnionPayChoose,mImgUnionPayUnchoose);
		
		
	}
	private void initListener(){
		mRlayoutAlibabaPay.setOnClickListener(this);
		mRlayoutWechatPay.setOnClickListener(this);
		mRlayoutUnionPay.setOnClickListener(this);
	}
	private void initPayment(){
		mSwitcherAlibabaPay.setChoose();
		mSwitcherWechatPay.setUnChoose();
		mSwitcherUnionPay.setUnChoose();
		mPayment = Payment.AlibabaPay;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Rlayout_act_order_AlibabaPay:
			mSwitcherAlibabaPay.setChoose();
			mSwitcherWechatPay.setUnChoose();
			mSwitcherUnionPay.setUnChoose();
			mPayment = Payment.AlibabaPay;
			break;
		case R.id.Rlayout_act_order_WeChatPay:
			mSwitcherAlibabaPay.setUnChoose();
			mSwitcherWechatPay.setChoose();
			mSwitcherUnionPay.setUnChoose();
			mPayment = Payment.WechatPay;
			break;
		case R.id.Rlayout_act_order_UnionPay:
			mSwitcherAlibabaPay.setUnChoose();
			mSwitcherWechatPay.setUnChoose();
			mSwitcherUnionPay.setChoose();
			mPayment = Payment.UnionPay;
			break;
		}
	}
	
	public static class Switcher{
		View chooseView,unchooseView;
		
		public Switcher(View chooseView,View unchooseView){
			this.chooseView = chooseView;
			this.unchooseView = unchooseView;
		}
		public void setChoose(){
			chooseView.setVisibility(View.VISIBLE);
			unchooseView.setVisibility(View.INVISIBLE);
		}
		public void setUnChoose(){
			chooseView.setVisibility(View.INVISIBLE);
			unchooseView.setVisibility(View.VISIBLE);
		}
		
	}
	
}
