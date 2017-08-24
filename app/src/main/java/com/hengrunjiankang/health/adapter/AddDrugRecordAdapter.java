package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.RecordActivity;
import com.hengrunjiankang.health.entity.AddDrugRecordEntity;
import com.hengrunjiankang.health.entity.RecordEntity;

/**
 * 主页顶部标题栏菜单适配器
 * @author YuXiu 2016-7-14
 *
 */
public class AddDrugRecordAdapter extends MyBaseAdapter<AddDrugRecordEntity> implements View.OnClickListener{

	public AddDrugRecordAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		EditText tv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			convertView = inflater.inflate(R.layout.item_add_drug_record_list, null);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag(v.getId());
		((RecordActivity)context).goToDetails(position);
	}
}
