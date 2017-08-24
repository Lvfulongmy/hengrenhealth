package com.hengrunjiankang.health.photo;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.BaseActivity;
import com.hengrunjiankang.health.activity.BaseBegPermissionActivity;
import com.hengrunjiankang.health.util.PhotoUtil;


public class SelectPhotoActivity extends BaseBegPermissionActivity {
    private GridView gvPhotos;
    private PhotoGridAdapter photoGridAdapter;
    private List<ImageBucket> photoList;
    private TextView tvFinish;
    private final int TOFOLDER = 1;
    private TextView tvFolder;
    public static String foldernum = "all";

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.title_back:
			setResult(PhotoUtil.REPICKCANCEL);
                this.finish();
                break;
            case R.id.title_ac_folder:
                Intent intent = new Intent(SelectPhotoActivity.this,
                        SelectPhotoFolderActivity.class);
                startActivityForResult(intent, TOFOLDER);
                break;
            case R.id.title_ac_finish:
                v.setClickable(false);
			    setResult(PhotoUtil.REPICKFINISH);
                this.finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected int setLayout() {
        // TODO Auto-generated method stub
        return R.layout.activity_select_photo;
    }

    @Override
    protected void findView() {
        // TODO Auto-generated method stub
        gvPhotos = (GridView) findViewById(R.id.gv_photo_list);
        tvFinish = (TextView) findViewById(R.id.title_ac_finish);
        tvFolder = (TextView) findViewById(R.id.title_ac_folder);

    }

    @Override
    protected void createObject() {
        // TODO Auto-generated method stub
        if (checkPermissions(begRWPermissons, BEGRWCODE)) {
            photoGridAdapter = new PhotoGridAdapter(this);
//		ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
            AlbumHelper helper = AlbumHelper.getHelper();
            helper.init(getApplicationContext());
            photoList = helper.getImagesBucketList(false);
//		for (int i = 0; i < photoList.size(); i++) {
//			imageItems.addAll(photoList.get(i).imageList);
//		}
            ArrayList<ImageItem> imageItems = helper.getAllImageList();
            photoGridAdapter.setData(imageItems);
            gvPhotos.setAdapter(photoGridAdapter);
        }
        setBackDo(new TodoBackFromBeg() {
            @Override
            public void backTodo(int requestCode) {
               if(requestCode== BEGRWCODE) {
                   photoGridAdapter = new PhotoGridAdapter(SelectPhotoActivity.this);
//		ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
                   AlbumHelper helper = AlbumHelper.getHelper();
                   helper.init(getApplicationContext());
                   photoList = helper.getImagesBucketList(false);
//		for (int i = 0; i < photoList.size(); i++) {
//			imageItems.addAll(photoList.get(i).imageList);
//		}
                   ArrayList<ImageItem> imageItems = helper.getAllImageList();
                   photoGridAdapter.setData(imageItems);
                   gvPhotos.setAdapter(photoGridAdapter);
               }

            }

            @Override
            public void cancelTodo(int requestCode) {

            }
        });
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        tvFinish.setOnClickListener(this);
        tvFolder.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        // TODO Auto-generated method stub
        initTitle("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TOFOLDER) {
            if (resultCode != -1) {
                foldernum = resultCode + "";
                photoGridAdapter.setData(photoList.get(resultCode).imageList);
                photoGridAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(PhotoUtil.REPICKCANCEL);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        AlbumHelper.destroyInstance();
    }
}
