package com.wqcf.kanfang.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



import com.wqcf.kanfang.R;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ViewPager实现的图片轮播自定义视图控件，既可以实现自动播放，也可以支持滑动切换图片。
 * @author wuping
 *
 */

public class SlideShowView extends FrameLayout {

	// 轮播图图片数量
	private final static int IMAGE_COUNT = 6;
	
	// 自动轮播的时间间隔
	private final static int ITME_INTERVAL = 3;
	
	// 自动轮播启用开关
	private final static boolean isAutoPlay = true;
	
	//自定义轮播图的资源
	private int[] imageId = {R.drawable.room,R.drawable.room,R.drawable.room,R.drawable.room,R.drawable.room};
	private List<ImageEntity> imageEntities;
	
	//放轮播图的ImageView的list
	private List<ImageView> imageViewList;
	
	// 放圆点的View 的list
	private List<View> dotViewList;
	
	private ViewPager viewPager;
	
	private TextView tv_description1;

	private TextView tv_description2;
	
	//当前轮播页
	private int currentItem;
	
	//定时任务
	private ScheduledExecutorService scheduledExecutorService;
	
	private Context context;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem((currentItem + 1));
		}
	};

	public SlideShowView(Context context) {
		this(context,null);
	}
	
	public SlideShowView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
		initData();
		if(isAutoPlay) {
			startPlay();
		}
	}
	
	 /** 
     * 异步任务,获取数据 
     *  
     */  
    class GetListTask extends AsyncTask<String, Integer, Boolean> {  
  
        

		@Override  
        protected Boolean doInBackground(String... params) {  
            try {  
                // 这里一般调用服务端接口获取一组轮播图片，下面是模拟数据
                for (int i = 0; i < imageId.length; i++) {
					ImageEntity entity = new ImageEntity();
					entity.setResourceId(imageId[i]);
					imageEntities.add(entity);
				}
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  
  
        @Override  
        protected void onPostExecute(Boolean result) {  
            super.onPostExecute(result);  
            if (result) {  
                initUI(context);  
            }  
        }  
    }  

	/**
	 * 	初始化View等UI
	 * 
	 */
	private void initUI(Context context) {
		if(imageEntities == null || imageEntities.size() == 0) {
			return;
		}
		
		LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
		LinearLayout dotLayout = (LinearLayout)findViewById(R.id.ll_dotcontainer);
		dotLayout.removeAllViews();
		
		//热点个数与图片特殊相等
		for (int i = 0; i < imageEntities.size(); i++) {
			ImageView view = new ImageView(context);
			view.setTag(imageEntities.get(i));
			if(i == 0) {//给一个默认图
				view.setBackgroundResource(R.drawable.logo);
			}
			view.setScaleType(ScaleType.CENTER);
			imageViewList.add(view);
			
			//构建点
			ImageView dotView = new ImageView(context);
			dotView.setBackgroundResource(R.drawable.circle_white);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			dotLayout.addView(dotView,params);
			dotViewList.add(dotView);
		}
		
		// 获取TextView组件
//		tv_description1 = (TextView) findViewById(R.id.tv1);
//		tv_description2 = (TextView) findViewById(R.id.tv2);
		
		//设置ViewPager组件
		viewPager = (ViewPager) findViewById(R.id.vp_slideshow);
		viewPager.setFocusable(true);
		
		viewPager.setAdapter(new MypagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListerner());
		
		// 设置viewpager的当前项为一个比较大的数，以便一开始就可以左右循环滑动
		int n = Integer.MAX_VALUE / 2 % imageViewList.size();  
        currentItem = Integer.MAX_VALUE / 2 - n;  
        
        dotViewList.get(0).setBackgroundResource(R.drawable.circle_origin);
        viewPager.setCurrentItem(currentItem);
	}
	
	/**
	 * ViewPager的适配器
	 *
	 */
	private class MypagerAdapter extends PagerAdapter{
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(imageViewList.get(position % imageViewList.size()));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ImageView imageView = imageViewList.get(position % imageViewList.size());
			ImageEntity entity = (ImageEntity) imageView.getTag();
			imageView.setBackgroundResource(entity.getResourceId());
//			if(position % imageViewList.size() == 0) {
//				tv_description1.setText(imageEntities.get(position % imageViewList.size()).getDescription1());
//				tv_description2.setText(imageEntities.get(position % imageViewList.size()).getDescription2());
//			}
			((ViewPager)container).addView(imageView);
			return imageView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void startUpdate(View container) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void finishUpdate(View container) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * ViewPager的监听器
	 * 当ViewPager中的页面发生改变的时候调用
	 *
	 */
	private class MyPageChangeListerner implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			Log.i("info", "onPageSelected=====" + position);
			((View)dotViewList.get(position % dotViewList.size())).setBackgroundResource(R.drawable.circle_origin);
			if(position == currentItem) {
				((View)dotViewList.get(currentItem % dotViewList.size())).setBackgroundResource(R.drawable.circle_origin);
			}else {
				((View)dotViewList.get(currentItem % dotViewList.size())).setBackgroundResource(R.drawable.circle_white);
			}
			currentItem = position;
//			tv_description1.setText(imageEntities.get(position % imageEntities.size()).getDescription1());
//			tv_description2.setText(imageEntities.get(position % imageEntities.size()).getDescription2());
		}
		
	}
	
	/**
	 * 执行轮播图切换任务
	 *
	 */
	private class slideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				handler.sendEmptyMessage(0);
			}
		}
		
	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new slideShowTask(), 5, 5, TimeUnit.SECONDS);
	}
	
	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdownNow();
	}

	/**
	 * 初始化相关数据
	 */
	private void initData() {
		imageViewList = new ArrayList<ImageView>();  
        dotViewList = new ArrayList<View>();
        imageEntities = new ArrayList<ImageEntity>();
        // 异步任务获取图片  
        new GetListTask().execute("");
	}
	
	private class ImageEntity {
		private String description1;
		private String description2;
		private int resourceId;
		public String getDescription1() {
			return description1;
		}
		public void setDescription1(String description1) {
			this.description1 = description1;
		}
		public String getDescription2() {
			return description2;
		}
		public void setDescription2(String description2) {
			this.description2 = description2;
		}
		public int getResourceId() {
			return resourceId;
		}
		public void setResourceId(int resourceId) {
			this.resourceId = resourceId;
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		stopPlay();//当切换到别的界面时，停止轮播图
	}
	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}
	
}
