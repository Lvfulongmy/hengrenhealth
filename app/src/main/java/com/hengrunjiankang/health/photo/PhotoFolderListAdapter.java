package com.hengrunjiankang.health.photo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
public class PhotoFolderListAdapter extends MyBaseAdapter<ImageBucket> {
	private RequestManager glide;
	public PhotoFolderListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		glide=Glide.with(context);
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//		// .showImageOnLoading(R.drawable.load_image) // 设置图片在下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.gray_stroke)// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.gray_stroke) // 设置图片加载/解码过程中错误时候显示的图片
//				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//
//				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//				// .decodingOptions(android.graphics.BitmapFactory.Options
//				// decodingOptions)//设置图片的解码配置
//				// .delayBeforeLoading(int delayInMillis)//int
//				// delayInMillis为你设置的下载前的延迟时间
//				// 设置图片加入缓存前，对bitmap进行设置
//				// .preProcessor()
//				// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
//				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
//				.build();// 构建完成

	}

	private static class ViewHolder {
		ImageView ivPhoto;
		// CheckBox cbCheck;
		TextView tvTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_photo_folder_list,
					null);
			holder = new ViewHolder();
			holder.ivPhoto = (ImageView) convertView
					.findViewById(R.id.iv_item_photo);
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		String path = "";

		String pathstr = data.get(position).imageList.get(0).thumbnailPath;
		if (pathstr == null || pathstr.length() == 0){
			pathstr = data.get(position).imageList.get(0).getImagePath();
		}
		if (pathstr != null && pathstr.length() > 0){
			path = "file://" + pathstr;
		}
//		if (holder.ivPhoto.getTag() == null
//				|| !holder.ivPhoto.getTag().equals(path)) {
////			holder.ivPhoto.setImageBitmap(null);
//			// holder.ivPhoto.setImageBitmap(Bimp.revitionImageSize(path,
//			// 256));
//			imageLoader.displayImage(path, holder.ivPhoto, options);
//		}
		glide.load(path).into(holder.ivPhoto);
		holder.tvTitle.setText(data.get(position).bucketName);
//		holder.ivPhoto.setTag(path);
		return convertView;
	}

}
