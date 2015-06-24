package com.wqcf.kanfang.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



import com.wqcf.kanfang.R;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
 * ViewPagerʵ�ֵ�ͼƬ�ֲ��Զ�����ͼ�ؼ����ȿ���ʵ���Զ����ţ�Ҳ����֧�ֻ����л�ͼƬ��
 * @author wuping
 *
 */

public class SlideShowView extends FrameLayout {

	// �ֲ�ͼͼƬ����
	private final static int IMAGE_COUNT = 6;
	
	// �Զ��ֲ���ʱ����
	private final static int ITME_INTERVAL = 3;
	
	// �Զ��ֲ����ÿ���
	private final static boolean isAutoPlay = true;
	
	//�Զ����ֲ�ͼ����Դ
	private int[] imageId = {R.drawable.room,R.drawable.room,R.drawable.room,R.drawable.room,R.drawable.room};
	private List<ImageEntity> imageEntities;
	
	//���ֲ�ͼ��ImageView��list
	private List<ImageView> imageViewList;
	
	// ��Բ���View ��list
	private List<View> dotViewList;
	
	private ViewPager viewPager;
	
	private TextView tv_description1;

	private TextView tv_description2;
	
	//��ǰ�ֲ�ҳ
	private int currentItem;
	
	//��ʱ����
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
     * �첽����,��ȡ���� 
     *  
     */  
    class GetListTask extends AsyncTask<String, Integer, Boolean> {  
  
        public GetListTask(){}
        
        public GetListTask(List<Drawable> drawList){
        	
        }

		@Override  
        protected Boolean doInBackground(String... params) {  
            try {  
                // ����һ����÷���˽ӿڻ�ȡһ���ֲ�ͼƬ��������ģ������
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
	 * 	��ʼ��View��UI
	 * 
	 */
	private void initUI(Context context) {
		if(imageEntities == null || imageEntities.size() == 0) {
			return;
		}
		
		LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
		LinearLayout dotLayout = (LinearLayout)findViewById(R.id.ll_dotcontainer);
		dotLayout.removeAllViews();
		
		//�ȵ������ͼƬ�������
		for (int i = 0; i < imageEntities.size(); i++) {
			ImageView view = new ImageView(context);
			view.setTag(imageEntities.get(i));
			if(i == 0) {//��һ��Ĭ��ͼ
				view.setBackgroundResource(R.drawable.logo);
			}
			view.setScaleType(ScaleType.CENTER);
			imageViewList.add(view);
			
			//������
			ImageView dotView = new ImageView(context);
			dotView.setBackgroundResource(R.drawable.circle_white);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			dotLayout.addView(dotView,params);
			dotViewList.add(dotView);
		}
		
		// ��ȡTextView���
//		tv_description1 = (TextView) findViewById(R.id.tv1);
//		tv_description2 = (TextView) findViewById(R.id.tv2);
		
		//����ViewPager���
		viewPager = (ViewPager) findViewById(R.id.vp_slideshow);
		viewPager.setFocusable(true);
		
		viewPager.setAdapter(new MypagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListerner());
		
		// ����viewpager�ĵ�ǰ��Ϊһ���Ƚϴ�������Ա�һ��ʼ�Ϳ�������ѭ������
		int n = Integer.MAX_VALUE / 2 % imageViewList.size();  
        currentItem = Integer.MAX_VALUE / 2 - n;  
        
        dotViewList.get(0).setBackgroundResource(R.drawable.circle_origin);
        viewPager.setCurrentItem(currentItem);
	}
	
	/**
	 * ViewPager��������
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
	 * ViewPager�ļ�����
	 * ��ViewPager�е�ҳ�淢���ı��ʱ�����
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
	 * ִ���ֲ�ͼ�л�����
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
	 * ��ʼ�ֲ�ͼ�л�
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new slideShowTask(), 5, 5, TimeUnit.SECONDS);
	}
	
	/**
	 * ֹͣ�ֲ�ͼ�л�
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdownNow();
	}

	/**
	 * ��ʼ���������
	 */
	private void initData() {
		imageViewList = new ArrayList<ImageView>();  
        dotViewList = new ArrayList<View>();
        imageEntities = new ArrayList<ImageEntity>();
        // �첽�����ȡͼƬ  
        new GetListTask().execute("");
	}
	
	private class ImageEntity {
		private String description1;
		private String description2;
		private int resourceId;
		private Drawable drawable;
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
		public void setDrawable(Drawable drawable){
			this.drawable = drawable;
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		stopPlay();//���л�����Ľ���ʱ��ֹͣ�ֲ�ͼ
	}
	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}
	
}
