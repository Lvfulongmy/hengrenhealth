package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewNoScroll extends ListView {

	public ListViewNoScroll(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ListViewNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ListViewNoScroll(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		 int temp = expandSpec + getDividerHeight() * getChildCount();
		 super.onMeasure(widthMeasureSpec, temp);
//		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
