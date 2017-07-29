package com.hengrunjiankang.health.adapter;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class WelPagerAdapter extends PagerAdapter {

	private ArrayList<View> list;

	public WelPagerAdapter(ArrayList<View> list) {
		this.list = list;
	}

	// viewpager中的组件数量
	@Override
	public int getCount() {
		return list.size();
	}

	// 滑动切换的时候销毁当前的组件
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(list.get(position));
	}

	// 每次滑动的时候生成的组件
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(list.get(position));
		return list.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
