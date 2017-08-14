package com.hengrunjiankang.health.photo;

import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.activity.BaseActivity;

public class SelectPhotoFolderActivity extends BaseActivity {
	private ListView lvFolder;
	private PhotoFolderListAdapter folderAapter;
	private List<ImageBucket> photoList;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back:
			setResult(-1);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.activity_select_photo_folder;
	}

	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		lvFolder = (ListView) findViewById(R.id.lv_photofolder_list);
	}

	@Override
	protected void createObject() {
		// TODO Auto-generated method stub
		folderAapter = new PhotoFolderListAdapter(this);
		AlbumHelper helper = AlbumHelper.getHelper();
		photoList = helper.getImagesBucketList(false);
		folderAapter.setData(photoList);
		lvFolder.setAdapter(folderAapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(-1);
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		lvFolder.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setResult(position);
				finish();
			}
		});
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		initTitle("选择相册");
	}

}
