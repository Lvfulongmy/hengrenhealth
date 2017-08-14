package com.hengrunjiankang.health.photo;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.adapter.MyBaseAdapter;

/**
 * 
 * 
 * @author YuXiu 2016-7-25
 * 
 */
public class PhotoGridAdapter extends MyBaseAdapter<ImageItem> implements
		OnClickListener {
	private RequestManager glide;

	public PhotoGridAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// imageLoader = ImageLoader.getInstance();
		// options = new DisplayImageOptions.Builder()
		// // .showImageOnLoading(R.drawable.load_image) // 设置图片在下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.gray_stroke)// 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.gray_stroke) // 设置图片加载/解码过程中错误时候显示的图片
		// .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
		// .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//
		// .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
		// // .decodingOptions(android.graphics.BitmapFactory.Options
		// // decodingOptions)//设置图片的解码配置
		// // .delayBeforeLoading(int delayInMillis)//int
		// // delayInMillis为你设置的下载前的延迟时间
		// // 设置图片加入缓存前，对bitmap进行设置
		// // .preProcessor()
		// // .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
		// // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
		// .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
		// .build();// 构建完成
		glide = Glide.with(context);

	}

	private static class ViewHolder {
		ImageView ivPhoto;
		// CheckBox cbCheck;
		ToggleButton tbCheck;
	}

	private boolean isVisible = true;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_photo_list, null);
			holder = new ViewHolder();
			holder.ivPhoto = (ImageView) convertView
					.findViewById(R.id.iv_item_photo);
			holder.tbCheck = (ToggleButton) convertView
					.findViewById(R.id.tb_item_check);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		String path = null;
		String pathstr = data.get(position).getThumbnailPath();
		if (pathstr == null || pathstr.length() == 0)
			pathstr = data.get(position).getImagePath();
		if (pathstr != null && pathstr.length() > 0)
			path = "file://" + pathstr;
		glide.load(path).into(holder.ivPhoto);
		boolean select = data.get(position).isSelected;
		holder.tbCheck.setChecked(select);
		// holder.cbCheck.setChecked(select);
		holder.tbCheck.setTag(position);
		// holder.ivPhoto.setTag(path);
		holder.tbCheck.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View view) {
		if (view instanceof ToggleButton) {
			ToggleButton toggleButton = (ToggleButton) view;
			int position = (Integer) toggleButton.getTag();
			// Toast.makeText(context,data.get(position).getImagePath(),
			// Toast.LENGTH_LONG).show();

			if (toggleButton.isChecked()) {
				if (Bimp.tempSelectFile.size() < 10) {
					File file = new File(data.get(position).getImagePath());
					Bimp.tempSelectFile.put(SelectPhotoActivity.foldernum+position,file);
				} else {
					toggleButton.setChecked(false);
					Toast.makeText(context, "最多选择十张", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Bimp.tempSelectFile
						.remove(SelectPhotoActivity.foldernum + position);
			}
			data.get(position).isSelected = toggleButton.isChecked();

		}
	}

}
