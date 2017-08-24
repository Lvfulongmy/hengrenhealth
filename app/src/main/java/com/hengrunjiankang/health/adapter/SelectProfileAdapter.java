package com.hengrunjiankang.health.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.BespockDoctorActivity;
import com.hengrunjiankang.health.activity.ProfileListActivity;
import com.hengrunjiankang.health.entity.ProfileListSelectEntity;

/**
 * @author YuXiu 2016-7-25
 */
public class SelectProfileAdapter extends MyBaseAdapter<ProfileListSelectEntity> implements View.OnClickListener {


    public SelectProfileAdapter(Context context) {
        super(context);

    }

    private static class ViewHolder {
        ImageView ivSelect;
        TextView tvNum;
        TextView tvToSee;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_profile_list, null);
            holder = new ViewHolder();
            holder.ivSelect = (ImageView) convertView
                    .findViewById(R.id.iv_item_select);
            holder.tvNum = (TextView) convertView
                    .findViewById(R.id.tv_item_num);
            holder.tvToSee = (TextView) convertView.findViewById(R.id.tv_item_tosee);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        boolean select = data.get(position).isSelect();

        if (select)
            holder.ivSelect.setImageResource(R.mipmap.icon_rb_checked);
        else {
            holder.ivSelect.setImageResource(R.mipmap.icon_rb_unchecked);
        }
        // holder.cbCheck.setChecked(select);
        holder.tvNum.setText(data.get(position).getFriendlyId());
        holder.tvToSee.setTag(R.id.tv_item_tosee, position);
        holder.tvToSee.setOnClickListener(this);
        holder.ivSelect.setTag(R.id.iv_item_select, position);
        holder.ivSelect.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag(v.getId());
        switch (v.getId()) {
            case R.id.tv_item_tosee:
                ((BespockDoctorActivity) context).goToDetails(position);
                break;
            case R.id.iv_item_select:
                data.get(position).setSelect(!data.get(position).isSelect());
                notifyDataSetChanged();
                break;
        }
    }

}
