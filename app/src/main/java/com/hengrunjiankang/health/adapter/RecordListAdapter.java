package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.RecordActivity;
import com.hengrunjiankang.health.entity.RecordEntity;

/**
 * 主页顶部标题栏菜单适配器
 * @author YuXiu 2016-7-14
 *
 */
public class RecordListAdapter extends MyBaseAdapter<RecordEntity> implements View.OnClickListener{

	public RecordListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		TextView tv;
		TextView tvToSee;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_record_list, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_item_date);
			holder.tvToSee=(TextView)convertView.findViewById(R.id.tv_item_tosee);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(data.get(position).getCollectdate().substring(0,10)+" "+data.get(position).getCollectdate().substring(11,19));
		holder.tvToSee.setTag(R.id.tv_item_tosee,position);
		holder.tvToSee.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag(v.getId());
		((RecordActivity)context).goToDetails(position);
	}
}
