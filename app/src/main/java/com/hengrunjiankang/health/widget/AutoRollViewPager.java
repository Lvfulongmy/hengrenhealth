package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.hengrunjiankang.health.adapter.ImagePagerAdapter;

/**
 * 图片轮播组件，
 * 继承自ViewPager增加了自动轮播功能
 * @author 1989.12.16
 *
 */
public class AutoRollViewPager extends ViewPager {
	public AutoRollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
	}

	@Override
	public PagerAdapter getAdapter() {
		// TODO Auto-generated method stub
		return ((ImagePagerAdapter) super.getAdapter()).getCopyAdapter();
	}

	public AutoRollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private final int AUTO_MSG = 1;
	private final int HANDLE_MSG = AUTO_MSG + 1;
	private static final int PHOTO_CHANGE_TIME = 3000;//定时变量
	private Handler mHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AUTO_MSG:
				setCurrentItem(getCurrentItem()+1);//收到消息后设置当前要显示的图片
				mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
				break;
			case HANDLE_MSG:
				mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
				break;
			default:
				break;
			}
		};
	};

}
