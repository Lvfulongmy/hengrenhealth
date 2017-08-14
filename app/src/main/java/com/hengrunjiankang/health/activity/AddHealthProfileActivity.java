package com.hengrunjiankang.health.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.widget.zoom.Compat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/14.
 */

public class AddHealthProfileActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private LinearLayout llCheckGroup;

    @Override
    protected int setLayout() {
        return R.layout.activity_add_profile;
    }

    @Override
    protected void findView() {
        llCheckGroup = (LinearLayout) findViewById(R.id.ll_checkgroup);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void getData() {
        initTitle("添加档案");
        initCheckMap();
        initCheckGroup();
    }


    private void addCheckBox(String tag,boolean haveline) {
        CheckBox box = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_item_checkbox, null);
        box.setText(mapString.get(tag));
        box.setTag(tag);
        box.setOnCheckedChangeListener(this);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtils.dip2px(this,5),0,CommonUtils.dip2px(this,5),0);
        box.setLayoutParams(layoutParams);
        llCheckGroup.addView(box);
        if(haveline) {
            TextView view = new TextView(this);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(this, 1));
            view.setLayoutParams(layoutParams2);
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_line));
            llCheckGroup.addView(view);
        }
    }
    private void addSubTitle(String tag) {
        TextView view=new TextView(this);
        view.setPadding(CommonUtils.dip2px(this,5),CommonUtils.dip2px(this,2),0,CommonUtils.dip2px(this,2));
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        LinearLayout.LayoutParams layoutParams2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams2);
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.bgcolor));
        view.setText(tag);
        llCheckGroup.addView(view);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                break;
        }
    }
    private void initCheckGroup(){
       Set< Map.Entry<String,Boolean>> set=mapCheckState.entrySet();
        int i=0;
        for(Map.Entry<String,Boolean> entry:set){
            boolean haveline=true;
            if(i==0){
                addSubTitle("基础症状");
            }
            if(i==5){
                addSubTitle("微循环");
            }
            if(i==9){
                addSubTitle("神经病变");
            }
            if(i==4||i==8){
                haveline=false;
            }
            addCheckBox(entry.getKey(),haveline);
            i++;
        }
    }

    private void initCheckMap() {
        mapString.put("Jczz_SfFlkg", "乏力、口干（苦、异味）");
        mapString.put("Jczz_SfDydssjdn", "多饮、多食、善饥、多尿");
        mapString.put("Jczz_SfXs", "消瘦");
        mapString.put("Jczz_SfFzxh", "烦躁心慌");
        mapString.put("Jczz_SfZhdh", "自汗盗汗");
        mapString.put("Wxh_SfSlmhxj", "视力模糊、下降  （眼底、视网膜、白内障）");
        mapString.put("Wxh_SfSzflr", "手足发凉/热");
        mapString.put("Wxh_SfTnbsb", " 糖尿病肾病");
        mapString.put("Wxh_SfTnbz", "糖尿病足");
        mapString.put("Sjbb_SfSzmm", "手足麻木、疼痛（针刺感）无知觉、踩异物感、手套、袜套感");
        mapString.put("Sjbb_SfPfsr", "皮肤瘙痒、蚁爬感");
        mapString.put("Sjbb_SfBmfx", "便秘、腹泻、便秘腹泻交替");
        mapCheckState.put("Jczz_SfFlkg", false);
        mapCheckState.put("Jczz_SfDydssjdn", false);
        mapCheckState.put("Jczz_SfXs", false);
        mapCheckState.put("Jczz_SfFzxh", false);
        mapCheckState.put("Jczz_SfZhdh", false);
        mapCheckState.put("Wxh_SfSlmhxj", false);
        mapCheckState.put("Wxh_SfSzflr", false);
        mapCheckState.put("Wxh_SfTnbsb", false);
        mapCheckState.put("Wxh_SfTnbz", false);
        mapCheckState.put("Sjbb_SfSzmm", false);
        mapCheckState.put("Sjbb_SfPfsr", false);
        mapCheckState.put("Sjbb_SfBmfx", false);
    }

    private LinkedHashMap<String, Boolean> mapCheckState = new LinkedHashMap<>();
    private HashMap<String, String> mapString = new HashMap<>();


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Toast.makeText(this,((String)compoundButton.getTag()+b),Toast.LENGTH_SHORT).show();
        mapCheckState.put((String) compoundButton.getTag(),b);
    }
}

