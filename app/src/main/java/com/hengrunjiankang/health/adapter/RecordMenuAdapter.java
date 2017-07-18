package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
/**
 * 主页顶部标题栏菜单适配器
 * @author YuXiu 2016-7-14
 *
 */
public class RecordMenuAdapter extends MyBaseAdapter<String> {

	public RecordMenuAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		TextView tv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_record_top_menu, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_item_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(data.get(position));
		return convertView;
	}

}
