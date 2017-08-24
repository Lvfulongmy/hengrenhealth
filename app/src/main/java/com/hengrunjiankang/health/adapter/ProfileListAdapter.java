package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.ProfileListActivity;
import com.hengrunjiankang.health.activity.RecordActivity;
import com.hengrunjiankang.health.applaction.MyApplication;
import com.hengrunjiankang.health.entity.ProfileListEntity;
import com.hengrunjiankang.health.entity.RecordEntity;

/**
 * 主页顶部标题栏菜单适配器
 * @author YuXiu 2016-7-14
 *
 */
public class ProfileListAdapter extends MyBaseAdapter<ProfileListEntity> implements View.OnClickListener{

	public ProfileListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		TextView tv;
		TextView tvToSee;
		TextView tvDel;
		LinearLayout ll;
		HorizontalScrollView scroll;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_profile_list, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_item_num);
			holder.tvToSee=(TextView)convertView.findViewById(R.id.tv_item_tosee);
			holder.tvDel=(TextView)convertView.findViewById(R.id.tv_item_delete);
			holder.ll=(LinearLayout)convertView.findViewById(R.id.ll_1);
			ViewGroup.LayoutParams params =holder.ll.getLayoutParams();
			params.width= MyApplication.ScreenWidth;
			holder.ll.setLayoutParams(params);
			holder.scroll=(HorizontalScrollView) convertView.findViewById(R.id.scroll);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(data.get(position).getFriendlyId());
		holder.scroll.scrollTo(0,0);
		holder.tvToSee.setTag(R.id.tv_item_tosee,position);
		holder.tvToSee.setOnClickListener(this);
		holder.tvDel.setTag(R.id.tv_item_delete,position);
		holder.tvDel.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag(v.getId());
		switch (v.getId()) {
			case R.id.tv_item_tosee:
				((ProfileListActivity) context).goToDetails(position);
				break;
			case R.id.tv_item_delete:
				((ProfileListActivity) context).deleteRecord(position);
				break;
		}
	}
}
