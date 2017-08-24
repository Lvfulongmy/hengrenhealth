package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.FindpwdActivity;
import com.hengrunjiankang.health.entity.DoctorEntity;
import com.hengrunjiankang.health.entity.ReservationExpertEntity;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.RatingBar;

import java.util.Random;
import java.util.zip.Inflater;

/**
 * 主页顶部标题栏菜单适配器
 * @author YuXiu 2016-7-14
 *
 */
public class BegpockJudgeAdapter extends MyImageBaseAdapter<ReservationExpertEntity> implements View.OnClickListener{

	public BegpockJudgeAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static class ViewHolder {
		TextView tvName;
		TextView tvTime;
		TextView tvJudge;
		RatingBar mRatingBar;
		LinearLayout llDialog;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_doctor_judge, null);
			holder = new ViewHolder();
			holder.llDialog=(LinearLayout) convertView.findViewById(R.id.ll_item_dialog);
			holder.tvName=(TextView)convertView.findViewById(R.id.tv_item_name);
			holder.tvTime=(TextView)convertView.findViewById(R.id.tv_item_time);
			holder.tvJudge=(TextView)convertView.findViewById(R.id.tv_item_judge);
			holder.mRatingBar=(RatingBar) convertView.findViewById(R.id.ratingbar_item_judge);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(data.get(position).getPatientUser().getFullName());
		holder.tvJudge.setText(data.get(position).getEvaluateContent());
		holder.tvTime.setText(data.get(position).getReservationTime().substring(0,10)+" "+data.get(position).getReservationTime().substring(11,19));
		if(data.get(position).getEvaluateValue()!=null&&data.get(position).getEvaluateValue().length()>0) {
			holder.mRatingBar.setStar(Float.parseFloat(data.get(position).getEvaluateValue()));
		}
		else{
			holder.mRatingBar.setStar(0);
		}
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		int margin= CommonUtils.dip2px(context,10);
		layoutParams.setMargins(margin,margin,margin,margin);
		holder.llDialog.removeAllViews();
		for(int i=0;i<data.get(position).getHealthRecord().size();i++){
			if(data.get(position).getHealthRecord().get(i).getIllStr().length()>0) {
				TextView tv = (TextView) inflater.inflate(R.layout.item_judge_dialog, null);
				tv.setLayoutParams(layoutParams);
				tv.setText(data.get(position).getHealthRecord().get(i).getIllStr());
				holder.llDialog.addView(tv);
			}
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag(v.getId());
	}
}
