package com.wqcf.kanfang.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.wqcf.kanfang.R;
import com.wqcf.kanfang.opensrc.xlistview.XListView;
import com.wqcf.kanfang.opensrc.xlistview.XListViewFooter;
import com.wqcf.kanfang.opensrc.xlistview.XListViewHeader;

public class ListViewBlankHeader extends XListView{
	
	
	
	public ListViewBlankHeader(Context context) {
		super(context);
		initWithContext(context);
	}

	public ListViewBlankHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public ListViewBlankHeader(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}
	
	protected void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);

		// init header view
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView
				.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView
				.findViewById(R.id.xlistview_header_time);
		
		addHeaderView(View.inflate(context, R.layout.blank_header, null));
		
		// init footer view
		mFooterView = new XListViewFooter(context);

	}

}
