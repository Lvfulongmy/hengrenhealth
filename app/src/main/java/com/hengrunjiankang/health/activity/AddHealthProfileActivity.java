package com.hengrunjiankang.health.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.renderscript.Float3;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.okhttp.CommonHttp;
import com.hengrunjiankang.health.okhttp.CommonHttpCallback;
import com.hengrunjiankang.health.okhttp.UrlObject;
import com.hengrunjiankang.health.util.CommonUtils;
import com.hengrunjiankang.health.util.PhotoUtil;
import com.hengrunjiankang.health.widget.zoom.Compat;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;

/**
 * Created by Administrator on 2017/8/14.
 */

public class AddHealthProfileActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private LinearLayout llCheckGroup;
    private PhotoUtil mPhotoUtil;
    private BGABadgeImageView ivYYJC1, ivYYJC2, ivYYJC3;
    private BGABadgeImageView ivBL1, ivBL2, ivBL3;
    private BGABadgeImageView ivYYFA1, ivYYFA2, ivYYFA3;
    private TextView tvYYJC, tvYYFA, tvBL;
    private ArrayList<File> selectFileYYJC;
    private ArrayList<File> selectFileBL;
    private ArrayList<File> selectFileYYFA;
    private ImageView ivYYJCADD, ivBLADD, ivYYFAADD;
    private int selectPhotoType;
    private TextView tvTxtNums;
    private EditText etOther;
    private TextView tvTitleRight;
    private String strYYJCUrls = "", strBLUrls = "", strYYFAUrls = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_add_profile;
    }

    @Override
    protected void findView() {
        llCheckGroup = (LinearLayout) findViewById(R.id.ll_checkgroup);
        tvYYJC = (TextView) findViewById(R.id.tv_profile_yyjc);
        tvYYJC.setOnClickListener(this);
        ivYYJCADD = (ImageView) findViewById(R.id.iv_profile_yyjc_add);
        ivYYJCADD.setVisibility(View.GONE);
        ivYYJCADD.setOnClickListener(this);
        ivYYJC1 = (BGABadgeImageView) findViewById(R.id.iv_profile_yyjc1);
        ivYYJC2 = (BGABadgeImageView) findViewById(R.id.iv_profile_yyjc2);
        ivYYJC3 = (BGABadgeImageView) findViewById(R.id.iv_profile_yyjc3);
        tvYYFA = (TextView) findViewById(R.id.tv_profile_yyfa);
        tvYYFA.setOnClickListener(this);
        ivYYFAADD = (ImageView) findViewById(R.id.iv_profile_yyfa_add);
        ivYYFAADD.setVisibility(View.GONE);
        ivYYFAADD.setOnClickListener(this);
        ivYYFA1 = (BGABadgeImageView) findViewById(R.id.iv_profile_yyfa1);
        ivYYFA2 = (BGABadgeImageView) findViewById(R.id.iv_profile_yyfa2);
        ivYYFA3 = (BGABadgeImageView) findViewById(R.id.iv_profile_yyfa3);

        tvBL = (TextView) findViewById(R.id.tv_profile_bl);
        tvBL.setOnClickListener(this);
        ivBLADD = (ImageView) findViewById(R.id.iv_profile_bl_add);
        ivBLADD.setVisibility(View.GONE);
        ivBLADD.setOnClickListener(this);
        ivBL1 = (BGABadgeImageView) findViewById(R.id.iv_profile_bl1);
        ivBL2 = (BGABadgeImageView) findViewById(R.id.iv_profile_bl2);
        ivBL3 = (BGABadgeImageView) findViewById(R.id.iv_profile_bl3);

        initImageView(ivYYJC1, R.id.iv_profile_yyjc1);
        initImageView(ivYYJC2, R.id.iv_profile_yyjc2);
        initImageView(ivYYJC3, R.id.iv_profile_yyjc3);
        initImageView(ivBL1, R.id.iv_profile_bl1);
        initImageView(ivBL2, R.id.iv_profile_bl2);
        initImageView(ivBL3, R.id.iv_profile_bl3);
        initImageView(ivYYFA1, R.id.iv_profile_yyfa1);
        initImageView(ivYYFA2, R.id.iv_profile_yyfa2);
        initImageView(ivYYFA3, R.id.iv_profile_yyfa3);
        etOther = (EditText) findViewById(R.id.et_profile_other);
        tvTxtNums = (TextView) findViewById(R.id.tv_profile_txtnums);
        tvTitleRight = (TextView) findViewById(R.id.title_right);
        tvTitleRight.setOnClickListener(this);

    }

    private void initImageView(BGABadgeImageView iv, int id) {
        iv.showDrawableBadge(BitmapFactory.decodeResource(getResources(), R.mipmap.add_photo_cancel));
        iv.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        iv.setOnClickListener(this);
        iv.setVisibility(View.GONE);
    }

    SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void save() {
        if (selectFileYYJC.size() > 0 || selectFileBL.size() > 0 || selectFileYYFA.size() > 0) {
            upLoadPic();
        } else {
            upLoadInfo();
        }
        showPDialog();
    }

    private void upLoadInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("RecordTime", formatTime.format(System.currentTimeMillis()));
        if (etOther.length() > 0) {
            params.put("Remark", etOther.getText().toString());
        }
        for (Map.Entry<String, Boolean> entry : mapCheckState.entrySet()
                ) {
            params.put(entry.getKey(), entry.getValue().toString());
        }
        if (!TextUtils.isEmpty(strYYJCUrls)) {
            params.put("Dalx_Yyjc", strYYJCUrls);
        }
        if (!TextUtils.isEmpty(strBLUrls)) {
            params.put("Dalx_Bl", strBLUrls);
        }
        if (!TextUtils.isEmpty(strYYFAUrls)) {
            params.put("Dalx_Yyfa", strYYFAUrls);
        }


        new CommonHttp(AddHealthProfileActivity.this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
                dismissPDialog();
                Toast.makeText(AddHealthProfileActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                strYYJCUrls = "";
                strBLUrls = "";
                strYYFAUrls = "";
                String id= JSON.parseArray(json,String.class).get(0);
                Intent intent=new Intent(AddHealthProfileActivity.this,ProfileActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                finish();
            }

            @Override
            public void requestFail(String msg) {
                dismissPDialog();
                Toast.makeText(AddHealthProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(AddHealthProfileActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }

        }).doRequest(UrlObject.SAVAHEALTHRECORD, params);
    }

    private void upLoadPic() {
        LinkedHashMap<String, File> filemap = new LinkedHashMap<>();
        if (selectFileYYJC.size() > 0) {
            for (int i = 0; i < selectFileYYJC.size(); i++) {
                filemap.put(selectFileYYJC.get(i).getName(), selectFileYYJC.get(i));
            }
        }
        if (selectFileBL.size() > 0) {
            for (int i = 0; i < selectFileBL.size(); i++) {
                filemap.put(selectFileBL.get(i).getName(), selectFileBL.get(i));
            }
        }
        if (selectFileYYFA.size() > 0) {
            for (int i = 0; i < selectFileYYFA.size(); i++) {
                filemap.put(selectFileYYFA.get(i).getName(), selectFileYYFA.get(i));
            }
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("RestrictType", "个人健康资料");

        new CommonHttp(AddHealthProfileActivity.this, new CommonHttpCallback() {
            @Override
            public void requestSeccess(String json) {
//                dismissPDialog();
                ArrayList<String> urls = (ArrayList<String>) JSON.parseArray(json, String.class);
                Toast.makeText(AddHealthProfileActivity.this, json, Toast.LENGTH_SHORT).show();
                for (int j = 0; j < urls.size(); j++) {
                    if (j < selectFileYYJC.size()) {
                        strYYJCUrls += urls.get(j);
                        strYYJCUrls += ";";
                    } else if (j < selectFileYYJC.size() + selectFileBL.size()) {
                        strBLUrls += urls.get(j);
                        strBLUrls += ";";
                    } else {
                        strYYFAUrls += urls.get(j);
                        strYYFAUrls += ";";
                    }

                }
//                strYYJCUrls=strYYJCUrls.substring(0,strYYJCUrls.length()-1);
//                strYYFAUrls=strYYJCUrls.substring(0,strYYFAUrls.length()-1);
//                strBLUrls=strBLUrls.substring(0,strBLUrls.length()-1);
                upLoadInfo();
            }

            @Override
            public void requestFail(String msg) {
                dismissPDialog();
                Toast.makeText(AddHealthProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestAbnormal(int code) {
                Toast.makeText(AddHealthProfileActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                dismissPDialog();
            }

        }).doRequest(UrlObject.UPLOADFILE, params, filemap);
    }

    @Override
    protected void createObject() {
        mPhotoUtil = new PhotoUtil(this);
        selectFileYYJC = new ArrayList<>();
        selectFileBL = new ArrayList<>();
        selectFileYYFA = new ArrayList<>();
        mPhotoUtil.setDoBack(new PhotoUtil.DoBack() {
            @Override
            public void doPickBack(List<File> file) {
                if (selectPhotoType == 0) {
                    selectFileYYJC = (ArrayList<File>) file;
                    splashPhoto(selectFileYYJC, ivYYJC1, ivYYJC2, ivYYJC3, ivYYJCADD, tvYYJC);
                }
                if (selectPhotoType == 1) {
                    selectFileBL = (ArrayList<File>) file;
                    splashPhoto(selectFileBL, ivBL1, ivBL2, ivBL3, ivBLADD, tvBL);
                }
                if (selectPhotoType == 2) {
                    selectFileYYFA = (ArrayList<File>) file;
                    splashPhoto(selectFileYYFA, ivYYFA1, ivYYFA2, ivYYFA3, ivYYFAADD, tvYYFA);
                }
            }

            @Override
            public void doTakeBack(File file) {
                if (selectPhotoType == 0) {
                    selectFileYYJC.add(file);
                    splashPhoto(selectFileYYJC, ivYYJC1, ivYYJC2, ivYYJC3, ivYYJCADD, tvYYJC);
                }
                if (selectPhotoType == 1) {
                    selectFileBL.add(file);
                    splashPhoto(selectFileBL, ivBL1, ivBL2, ivBL3, ivBLADD, tvBL);
                }
                if (selectPhotoType == 2) {
                    selectFileYYFA.add(file);
                    splashPhoto(selectFileYYFA, ivYYFA1, ivYYFA2, ivYYFA3, ivYYFAADD, tvYYFA);
                }
            }
        });
    }

    private void splashPhoto(ArrayList<File> list, View... views) {
        if (list.size() > 0) {
            ((ImageView) views[0]).setImageBitmap(BitmapFactory.decodeFile(list.get(0).getPath()));
            ((ImageView) views[0]).setVisibility(View.VISIBLE);
            ((TextView) views[4]).setEnabled(false);
            ((ImageView) views[3]).setVisibility(View.VISIBLE);
        }
        if (list.size() > 1) {
            ((ImageView) views[1]).setImageBitmap(BitmapFactory.decodeFile(list.get(1).getPath()));
            ((ImageView) views[1]).setVisibility(View.VISIBLE);
        }
        if (list.size() > 2) {
            ((ImageView) views[2]).setImageBitmap(BitmapFactory.decodeFile(list.get(2).getPath()));
            ((ImageView) views[2]).setVisibility(View.VISIBLE);
            ((ImageView) views[3]).setVisibility(View.GONE);
            ((TextView) views[4]).setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPhotoUtil.onResult(requestCode, resultCode, RESULT_OK);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void setListener() {
        etOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTxtNums.setText(charSequence.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void getData() {
        tvTitleRight.setText("保存");
        initTitle("添加档案");
        initCheckMap();
        initCheckGroup();
    }


    private void addCheckBox(String tag, boolean haveline) {
        CheckBox box = (CheckBox) LayoutInflater.from(this).inflate(R.layout.layout_item_checkbox, null);
        box.setText(mapString.get(tag));
        box.setTag(tag);
        box.setOnCheckedChangeListener(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtils.dip2px(this, 5), 0, CommonUtils.dip2px(this, 5), 0);
        box.setLayoutParams(layoutParams);
        llCheckGroup.addView(box);
        if (haveline) {
            TextView view = new TextView(this);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(this, 1));
            view.setLayoutParams(layoutParams2);
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_line));
            llCheckGroup.addView(view);
        }
    }

    private void addSubTitle(String tag) {
        TextView view = new TextView(this);
        view.setPadding(CommonUtils.dip2px(this, 5), CommonUtils.dip2px(this, 2), 0, CommonUtils.dip2px(this, 2));
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams2);
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.bgcolor));
        view.setText(tag);
        llCheckGroup.addView(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
            case R.id.tv_profile_yyjc:
            case R.id.iv_profile_yyjc_add:
                selectPhotoType = 0;
                PhotoUtil.selectFile = new LinkedHashMap<>();
                for (int i = 0; i < selectFileYYJC.size(); i++) {
                    PhotoUtil.selectFile.put("yyjc" + i, selectFileYYJC.get(i));
                }
                mPhotoUtil.selectPhoto(view);
                break;
            case R.id.tv_profile_bl:
            case R.id.iv_profile_bl_add:
                selectPhotoType = 1;
                PhotoUtil.selectFile = new LinkedHashMap<>();
                for (int i = 0; i < selectFileBL.size(); i++) {
                    PhotoUtil.selectFile.put("bl" + i, selectFileBL.get(i));
                }
                mPhotoUtil.selectPhoto(view);
                break;
            case R.id.tv_profile_yyfa:
            case R.id.iv_profile_yyfa_add:
                selectPhotoType = 2;
                PhotoUtil.selectFile = new LinkedHashMap<>();
                for (int i = 0; i < selectFileYYFA.size(); i++) {
                    PhotoUtil.selectFile.put("yyfa" + i, selectFileYYFA.get(i));
                }
                mPhotoUtil.selectPhoto(view);
                break;
            case R.id.iv_profile_yyjc1:
                deleteImage(selectFileYYJC, 0, ivYYJCADD, tvYYJC);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_yyjc2:
                deleteImage(selectFileYYJC, 1, ivYYJCADD, tvYYJC);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_yyjc3:
                deleteImage(selectFileYYJC, 2, ivYYJCADD, tvYYJC);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_yyfa1:
                deleteImage(selectFileYYFA, 0, ivYYFAADD, tvYYFA);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_yyfa2:
                deleteImage(selectFileYYFA, 1, ivYYFAADD, tvYYFA);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_yyfa3:
                deleteImage(selectFileYYFA, 2, ivYYFAADD, tvYYFA);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_bl1:
                deleteImage(selectFileBL, 0, ivBLADD, tvBL);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_bl2:
                deleteImage(selectFileBL, 1, ivBLADD, tvBL);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_profile_bl3:
                deleteImage(selectFileBL, 2, ivBLADD, tvBL);
                view.setVisibility(View.GONE);
                break;
            case R.id.title_right:
                if (check()) {
                    save();
                } else {
                    Toast.makeText(this, "请至少填写一项内容", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void deleteImage(ArrayList<File> list, int pos, View... views) {
        if (pos==3&&list.size()<3)
            pos = list.size() - 1;
        if(pos==2&&list.size()<3){
            pos=0;
        }
        list.remove(pos);
//        int i=0;
//        for (Map.Entry<String,File> entry:
//             map.entrySet()) {
//            if(i==pos) {
//                map.remove(entry.getKey());
//                break;
//            }
//            i++;
//        }
        if (list.size() == 0) {
            views[0].setVisibility(View.GONE);
            views[1].setEnabled(true);
        } else {
            views[0].setVisibility(View.VISIBLE);
            views[1].setEnabled(false);
        }

    }

    private void initCheckGroup() {
        Set<Map.Entry<String, Boolean>> set = mapCheckState.entrySet();
        int i = 0;
        for (Map.Entry<String, Boolean> entry : set) {
            boolean haveline = true;
            if (i == 0) {
                addSubTitle("基础症状");
            }
            if (i == 5) {
                addSubTitle("微循环");
            }
            if (i == 9) {
                addSubTitle("神经病变");
            }
            if (i == 4 || i == 8) {
                haveline = false;
            }
            addCheckBox(entry.getKey(), haveline);
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
        Toast.makeText(this, ((String) compoundButton.getTag() + b), Toast.LENGTH_SHORT).show();
        mapCheckState.put((String) compoundButton.getTag(), b);
    }

    protected void showQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否退出本次编辑？");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private boolean check() {
        for (Map.Entry<String, Boolean> entry : mapCheckState.entrySet()) {
            if (entry.getValue() == true) {
                return true;
            }
        }
        if (selectFileYYFA.size() > 0)
            return true;
        if (selectFileYYJC.size() > 0)
            return true;
        if (selectFileBL.size() > 0)
            return true;
        if (etOther.length() > 0) {
            return true;
        }
        return false;
    }

}

