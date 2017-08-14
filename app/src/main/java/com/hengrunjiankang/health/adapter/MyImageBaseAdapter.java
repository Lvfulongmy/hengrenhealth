package com.hengrunjiankang.health.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hengrunjiankang.health.util.CommonUtils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class MyImageBaseAdapter<T> extends BaseAdapter {

	protected Context context;
	protected LayoutInflater inflater;
	protected List<T> data;
	protected RequestManager glide;

	public MyImageBaseAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		glide = Glide.with(context);
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (data == null)
			return 0;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (data == null)
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (data == null)
			return 0;
		return position;
	}

	protected void setImage(String url, ImageView view) {
//		if (CommonUtils.isGifUrl(url)) {
//			glide.asGif().load(url)
//					.into(view);
//		} else {
			glide.load(url).into(view);
//		}
	}

//	protected void setImageNoCache(String url, ImageView view) {
//		String headurl = url;
//
//		if (view.getTag(R.id.headurl) == null
//				|| !view.getTag(R.id.headurl).equals(url)) {
//				glide.load(headurl).skipMemoryCache(true)
//						.diskCacheStrategy(DiskCacheStrategy.NONE).into(view);
//			}
//			view.setTag(R.id.headurl, url);
//		}
//	}
//	protected void setImageBitmap(String url, ImageView view) {
//	// TODO Auto-generated method stub
//	if (CommonUtil.isGifUrl(url)) {
//		glide.load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//				.into(view);
//	} else{
//		glide.load(url).into(view);
//	}
//}
}
