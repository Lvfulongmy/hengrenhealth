package com.hengrunjiankang.health.activity;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.entity.BloodFatEntity;
import com.hengrunjiankang.health.entity.BloodFatIndexEntity;
import com.hengrunjiankang.health.entity.UREntity;
import com.hengrunjiankang.health.entity.URIndexEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RecordDetailsActivity extends BaseActivity {
    private LinearLayout llContent;
    private LinearLayout llUrHeader;
    private LinearLayout llBfHeader;
    private TextView tvTime;
    @Override
    protected int setLayout() {
        return R.layout.activity_bloodfat_index;
    }

    @Override
    protected void findView() {
        llContent = (LinearLayout) findViewById(R.id.ll_index_content);
        llBfHeader=(LinearLayout)findViewById(R.id.ll_header_bf);
        llUrHeader=(LinearLayout)findViewById(R.id.ll_header_ur);
        tvTime=(TextView)findViewById(R.id.tv_index_time);
    }

    @Override
    protected void createObject() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void getData() {
        initTitle(getIntent().getStringExtra("title"));
        initData();
    }

    private void initData() {
        LayoutInflater inflater = LayoutInflater.from(this);
        int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                llUrHeader.setVisibility(View.GONE);
                BloodFatEntity bfEntity =(BloodFatEntity) getIntent().getSerializableExtra("data");
                tvTime.setText("测量时间   "+bfEntity.getCollectdate().substring(0,10)+" "+bfEntity.getCollectdate().substring(11,19));
                ArrayList<BloodFatIndexEntity> list = new ArrayList<BloodFatIndexEntity>();
                list.add(new BloodFatIndexEntity("胆固醇含量\n（CHOL）", bfEntity.getCHOL()+"mmol/L", "合适水平：≤5.17mmol/L", "临界：5.20—5.66mmol/L", "升高：≥5.69mmol/L"));
                list.add(new BloodFatIndexEntity("高密度脂蛋\n白胆固醇含\n（HDLCHOL）", bfEntity.getHDLCHOL()+"mmol/L", "合适水平：＜1.69mmol/L", "临界：1.69—2.25mmol/L", "升高：2.26—5.63mmol/L\n极高：≥5.64mmol/L"));
                list.add(new BloodFatIndexEntity("甘油三酯含量\n（TRIG）", bfEntity.getTRIG()+"mmol/L", "合适水平：≥1.04mmol/L", "降低：≤0.91mmol/L", ""));
                list.add(new BloodFatIndexEntity("低密度脂蛋白\n胆固醇含量\n（CALCLDL）", bfEntity.getCALCLDL()+"mmol/L", "合适水平：≤3.10mmol/L", "临界：3.13—3.59mmol/L", "升高：≥3.62mmol/L"));
                list.add(new BloodFatIndexEntity("总胆固醇和\n高密度胆\n固醇的比值\n（TC_HDL）", bfEntity.getTC_HDL()+"mmol/L", "男性合适水平：≤4.5mmol/L\n女性合适水平：≤3.5mmol/L", "", "升高：≥5.0mmol/L"));
                ArrayList<Integer> colorList=new ArrayList<>();
                if(bfEntity.getCHOL()<5.20){
                    colorList.add(ContextCompat.getColor(this,R.color.green_index));
                }else if(bfEntity.getCHOL()>=5.20&&bfEntity.getCHOL()<=5.66){
                    colorList.add(ContextCompat.getColor(this,R.color.orange_index));
                }else if(bfEntity.getCHOL()>5.66){
                    colorList.add(ContextCompat.getColor(this,R.color.red_index));
                }
                if(bfEntity.getHDLCHOL()<1.69){
                    colorList.add(ContextCompat.getColor(this,R.color.green_index));
                }else if(bfEntity.getHDLCHOL()>=1.69&&bfEntity.getHDLCHOL()<=2.25){
                    colorList.add(ContextCompat.getColor(this,R.color.orange_index));
                }else if(bfEntity.getHDLCHOL()>2.25){
                    colorList.add(ContextCompat.getColor(this,R.color.red_index));
                }
                if(bfEntity.getTRIG()>0.91){
                    colorList.add(ContextCompat.getColor(this,R.color.green_index));
                }else {
                    colorList.add(ContextCompat.getColor(this,R.color.orange_index));
                }
                if(bfEntity.getCALCLDL()<3.13){
                    colorList.add(ContextCompat.getColor(this,R.color.green_index));
                }else if(bfEntity.getCALCLDL()>=3.13&&bfEntity.getCALCLDL()<=3.59){
                    colorList.add(ContextCompat.getColor(this,R.color.orange_index));
                }else if(bfEntity.getCALCLDL()>3.59){
                    colorList.add(ContextCompat.getColor(this,R.color.red_index));
                }
                if(bfEntity.getTC_HDL()<5.0){
                    colorList.add(ContextCompat.getColor(this,R.color.green_index));
                }else {
                    colorList.add(ContextCompat.getColor(this,R.color.red_index));
                }
                for (int i = 0; i < list.size(); i++) {
                    LinearLayout sub = (LinearLayout) inflater.inflate(R.layout.item_bloodfat_list, null);
                    TextView tvName = sub.findViewById(R.id.tv_item_name);
                    tvName.setText(list.get(i).getName());
                    TextView tvIndex = sub.findViewById(R.id.tv_item_index);
                    tvIndex.setText(list.get(i).getIndex());
                    TextView tvIndexG = sub.findViewById(R.id.tv_item_index_green);
                    tvIndexG.setText(list.get(i).getIndex_green());
                    TextView tvIndexO = sub.findViewById(R.id.tv_item_index_orange);
                    if(list.get(i).getIndex_orange().equals(""))
                        tvIndexO.setVisibility(View.GONE);
                    tvIndexO.setText(list.get(i).getIndex_orange());
                    TextView tvIndexR = sub.findViewById(R.id.tv_item_index_red);
                    if(list.get(i).getIndex_red().equals(""))
                        tvIndexR.setVisibility(View.GONE);
                    tvIndexR.setText(list.get(i).getIndex_red());
                    llContent.addView(sub);
                    tvIndex.setTextColor(colorList.get(i));
                }
                break;
            case 1:
                llBfHeader.setVisibility(View.GONE);
                UREntity urEntity=(UREntity) getIntent().getSerializableExtra("data");
                tvTime.setText("测量时间   "+urEntity.getCollectdate().substring(0,10)+" "+urEntity.getCollectdate().substring(11,19));
                ArrayList<URIndexEntity> listUr=new ArrayList<>();
                listUr.add(new URIndexEntity("白细胞（LEU）",urEntity.getLEU(),"【阳性（+）说明尿路感染】"));
                listUr.add(new URIndexEntity("亚硝酸盐（NIT）",urEntity.getNIT(),"【阳性（+）说明尿路感染】"));
                listUr.add(new URIndexEntity("尿胆原（UBG）",urEntity.getUBG(),"【超过16，说明有黄疸】"));
                listUr.add(new URIndexEntity("蛋白质（PRO）",urEntity.getPRO(),"【阳性（+），说明可能有急性肾小球肾炎，糖尿病肾性疾病】"));
                listUr.add(new URIndexEntity("酸碱度（PH）",urEntity.getPH(),"【平均值6.0，增高常见于频繁呕吐糖尿病肾性疾病；降低常见于慢性肾小球炎，糖尿病等】"));
                listUr.add(new URIndexEntity("红细胞（BLD）",urEntity.getBLD(),"【阳性（+），同时有蛋白者要考虑肾脏病或出血】"));
                listUr.add(new URIndexEntity("比重（SG）",urEntity.getSG(),"【正常范围：1.015-1.025 增高多见于高热，肾功能不全，糖尿病；降低多见于慢性肾小球炎和肾盂肾炎等】"));
                listUr.add(new URIndexEntity("酮体（KET）",urEntity.getKET(),"【阳性（+），可能酸中毒,糖尿病，呕吐，腹泻】"));
                listUr.add(new URIndexEntity("胆红素（BIL）",urEntity.getBIL(),"【阳性（+），可能肝细胞性或阻塞性黄疸】"));
                listUr.add(new URIndexEntity("葡萄糖（GLU）",urEntity.getGLU(),"【阳性（+），可能有糖尿病,甲亢，肢端肥大症等】"));
                listUr.add(new URIndexEntity("维生素C（VC）",urEntity.getVC(),"【阳性（+），可能尿液红细胞，胆红素，亚硝酸盐，葡萄糖可能出现假阳性】"));
                for (int i = 0; i < listUr.size(); i++) {
                    LinearLayout sub = (LinearLayout) inflater.inflate(R.layout.item_ur_list, null);
                    TextView tvName = sub.findViewById(R.id.tv_item_name);
                    tvName.setText(listUr.get(i).getName());
                    TextView tvIndex = sub.findViewById(R.id.tv_item_index);
                    tvIndex.setText(listUr.get(i).getIndex());
                    TextView tvScope = sub.findViewById(R.id.tv_item_scope);
                    tvScope.setText(listUr.get(i).getScope());
                    llContent.addView(sub);
                }
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                this.finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
                break;
        }
    }
}
