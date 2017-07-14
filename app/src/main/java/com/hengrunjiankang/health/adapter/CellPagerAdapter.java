package com.hengrunjiankang.health.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.widget.MyView;
/**
 * 图片轮播适配器
 * 
 * @author 1989.12.16
 * 
 */
public class CellPagerAdapter extends PagerAdapter {
	private Context context;
	private List<MyView> views;
	private int pos;
	public CellPagerAdapter(Context context) {
		this.context = context;

		// TODO Auto-generated constructor stub
		views = new ArrayList<MyView>();
		for (int i=0;i<3;i++) {
			MyView view= new MyView(context);
			views.add(view);
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stu
			return Integer.MAX_VALUE;
	}
	public MyView getView(int pos){
		return views.get(pos%views.size());
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		pos =position % views.size() ;
		// container.removeView(views.get(pos));
		try {
			container.addView(views.get(pos));
		} catch (Exception e) {

		}
		return position;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(container, position, object);
		container.removeView(views.get(position % views.size()));
	}

}
