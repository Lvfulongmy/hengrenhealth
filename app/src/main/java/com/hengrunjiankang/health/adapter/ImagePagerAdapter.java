package com.hengrunjiankang.health.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.hengrunjiankang.health.R;

/**
 * 图片轮播适配器
 * 
 * @author 1989.12.16
 * 
 */
public class ImagePagerAdapter extends PagerAdapter {
	private Context context;
	private List<ImageView> views;
	private int pos;
	private PagerAdapter copy = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		public int getItemPosition(Object object) {
			return pos;
		};
	};

	public PagerAdapter getCopyAdapter() {
		return copy;
	}

	@SuppressWarnings("deprecation")
	public ImagePagerAdapter(Context context, List<Integer> ids) {
		this.context = context;

		// TODO Auto-generated constructor stub
		views = new ArrayList<ImageView>();
		for (int i=0;i<ids.size();i++) {
			ImageView view = new ImageView(context);
			view.setScaleType(ScaleType.FIT_XY);
			view.setTag(R.id.vp_home_banner,i);
			view.setImageResource(ids.get(i));
			views.add(view);
			
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (views.size() > 2)
			return Integer.MAX_VALUE;
		return views.size();
	}

	public List<ImageView> getViews() {
		return views;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		pos = position % views.size();
		 container.removeView(views.get(pos));
		try {
			container.addView(views.get(pos));
		} catch (Exception e) {

		}
		return views.get(pos);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(container, position, object);
//		container.removeView(views.get(position % views.size()));
	}

}
