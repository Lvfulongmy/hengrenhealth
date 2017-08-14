package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.entity.DoctorEntity;
import com.hengrunjiankang.health.okhttp.UrlObject;

/**
 * 主页顶部标题栏菜单适配器
 * @author YuXiu 2016-7-14
 *
 */
public class HomeListAdapter extends MyImageBaseAdapter<DoctorEntity> implements View.OnClickListener{

	public HomeListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		TextView tvName;
		TextView tvJob;
		TextView tvUnit;
		TextView tvDes;
		TextView tvBespock;
		ImageView ivIcon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_home_list, null);
			holder = new ViewHolder();
			holder.tvName= (TextView) convertView.findViewById(R.id.tv_item_name);
			holder.tvJob= (TextView) convertView.findViewById(R.id.tv_item_job);
			holder.tvUnit= (TextView) convertView.findViewById(R.id.tv_item_unit);
			holder.tvDes= (TextView) convertView.findViewById(R.id.tv_item_des);
			holder.tvBespock= (TextView) convertView.findViewById(R.id.tv_item_bespock);
			holder.ivIcon= (ImageView) convertView.findViewById(R.id.iv_item_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvBespock.setTag(R.id.tv_item_bespock,position);
		holder.tvBespock.setOnClickListener(this);
		holder.tvName.setText(data.get(position).getName());
		holder.tvJob.setText(data.get(position).getDuties());
		holder.tvUnit.setText(data.get(position).getHospital());
		holder.tvDes.setText(data.get(position).getExpertise());
		setImage(UrlObject.BASEURL+data.get(position).getSmallImageUrl(),holder.ivIcon);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag(v.getId());
	}
}
