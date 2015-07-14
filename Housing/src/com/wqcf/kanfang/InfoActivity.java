package com.wqcf.kanfang;

import java.util.ArrayList;

import com.wqcf.kanfang.data.bean.RoomInfoBean;
import com.wqcf.kanfang.data.core.DataManager;
import com.wqcf.kanfang.ui.adapter.OtherRoomAdapter;
import com.wqcf.kanfang.ui.view.SlideShowView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class InfoActivity extends Activity implements OnClickListener{

	private ImageView mImgVideo1,
	mImgVideo2,
	mImgVideo3;


	private TextView mTxtTitle,
	mTxtVideo1,
	mTxtVideo2,
	mTxtVideo3,

	mTxtPrice,

	mTxtRoomtype,
	mTxtZhuangxiu,
	mTxtFloor,
	mTxtSize,
	mTxtGaikuang,
	mTxtChaoxiang,

	mTxtXiaoqu,
	mTxtPosition,

	mTxtDesc;

	private ListView mLvOtherHouse;
	private SlideShowView mViewRoomPic;
	private RelativeLayout mRlayoutOrder,mRlayoutLive,mRlayoutFav;

	private OtherRoomAdapter<RoomInfoBean> adapter;
	private ArrayList<RoomInfoBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		initView();
		setView();
		initTestData();
		initClickListener();
		//playVideo();
	}
	private void initView(){
		mTxtTitle = (TextView)findViewById(R.id.Txt_act_info_Title);
		mTxtRoomtype = (TextView)findViewById(R.id.Txt_act_info_Roomtype);
		mTxtPrice = (TextView)findViewById(R.id.Txt_act_info_Price);
		mTxtPosition = (TextView)findViewById(R.id.Txt_act_info_Position);
		mRlayoutOrder = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_Zhongjie);
		mRlayoutFav = (RelativeLayout)findViewById(R.id.RLayout_act_info_Bottom_Favourite);
		mViewRoomPic = (SlideShowView)findViewById(R.id.SlideView_act_info_Pic);
		mRlayoutLive = (RelativeLayout)findViewById(R.id.Llayout_act_info_Bottom);
		mTxtVideo1 = (TextView)findViewById(R.id.Txt_act_info_VideoHistory_1);
		mTxtVideo2 = (TextView)findViewById(R.id.Txt_act_info_VideoHistory_2);
		mTxtVideo3 = (TextView)findViewById(R.id.Txt_act_info_VideoHistory_3);
		mTxtZhuangxiu = (TextView)findViewById(R.id.Txt_act_info_ZhuangXiu);
		mTxtFloor = (TextView)findViewById(R.id.Txt_act_info_Floor);
		mTxtSize = (TextView)findViewById(R.id.Txt_act_info_Size);
		mTxtGaikuang = (TextView)findViewById(R.id.Txt_act_info_GaiKuang);
		mTxtChaoxiang = (TextView)findViewById(R.id.Txt_act_info_ChaoXiang);
		mTxtXiaoqu = (TextView)findViewById(R.id.Txt_act_info_XiaoQu);
		mTxtDesc = (TextView)findViewById(R.id.Txt_act_info_Desc);
		mLvOtherHouse = (ListView)findViewById(R.id.ListView_act_info_OtherHouse);
	}
	private void setView(){
		RoomInfoBean room = DataManager.getInstance().getRoomInfo();
		mTxtTitle.setText(room.title);
		mTxtRoomtype.setText(room.room_type);
		mTxtPosition.setText(room.area+
				room.district+
				room.address);
		mTxtPrice.setText(room.price);
		mTxtSize.setText("44m");
		mTxtZhuangxiu.setText("精装修");
		mTxtFloor.setText("7/27层");
		mTxtGaikuang.setText("普通住宅");
		mTxtChaoxiang.setText("北向");
		mTxtXiaoqu.setText(room.housingname);
		mTxtDesc.setText("        麦田认证房假一罚百房源编号MTXXXXXX举报电话：400-7061188“华源冠军城”西临京昌高速路，东望奥运会主赛场，北靠奥运村东西主干路——清华东路，距奥运主赛场800米，步行10分钟，项目占地3.56公顷，总建筑面积15万平米，是集精装修住宅、写字楼、商业为一体的综合高档项目。秉承“华源关注生命”的企业宗旨，“华源冠军城”将建设为高品质、健康、环保的人文社区。项目2004年动工建设，2005年开盘销售，2007年全面入住，2008年现场体会奥运盛大体育赛事带给您的生命活力和世间友谊。天时、地利、人和造就了项目，“华源冠军城”无疑将是2005年奥运核心区内最值得期待的楼盘，亦将成为奥运村的冠军楼盘。交通状况良好，四环、城市轻轨、地铁五号线、奥运支线和机场轻轨的联络，12条道路纵横交错，共同构成了未来“棋盘状”交通网，形成高效、便捷、通畅的立体交通网络。幼儿园：小牛津双语幼儿园、亚运村幼儿园中小学：朝阳外语学校、陈经纶中学大学：中国科学院、对外经贸大学、中国音乐学院、北京联合大学、信息工程学院综合商场：北辰购物中心、飘亮购物中心、旺市百利、第五大道、爱家家居医院：亚运村医院、西藏医院、中日医院邮局：亚运村邮局银行：交通银行、北京银行、光大银行、兴业银行、广发银行、工商银行其他：鸿华高尔夫球场、馨叶高尔夫球场、姜庄湖高尔夫球场、清河湾高尔夫球场小区内部配套：健身房、洗衣店、停车位、美容美发\n——因为专注，所以专业——");
	}	

	private void initClickListener(){
		mRlayoutOrder.setOnClickListener(this);
		mRlayoutLive.setOnClickListener(this);
		mRlayoutFav.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.RLayout_act_info_Bottom_Zhongjie:
			startActivity(new Intent(this,OrderActivity.class));
			return;
		case R.id.Llayout_act_info_Bottom:
			startActivity(new Intent(this,LiveActivity.class));
			return;
		case R.id.RLayout_act_info_Bottom_Favourite:

			return;
		}

	}

	private void initTestData(){
		adapter = new OtherRoomAdapter<RoomInfoBean>(this, mLvOtherHouse);
		list = new ArrayList<RoomInfoBean>();
		list.add(new RoomInfoBean());
		list.add(new RoomInfoBean());
		adapter.setList(list);
		mLvOtherHouse.setAdapter(adapter);
	}

	//	private void playVideo(){
	//		String strURL = DataManager.getInstance().getRoomInfo().vedio.vedio_url;
	//		Uri uri = Uri.parse(strURL);
	//		mVV.setVideoURI(uri);
	//		mVV.start();
	//	}

}
