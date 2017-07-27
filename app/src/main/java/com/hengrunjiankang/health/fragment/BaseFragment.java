package com.hengrunjiankang.health.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.hengrunjiankang.health.util.CommonUtils;

/**
 * Fragment基类
 * 
 * @author 1989.12.16
 * 
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected Activity mActivity;
	protected View view;
	protected AlertDialog progressView;
	protected  int progressCount=0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity = getActivity();
		view = inflater.inflate(setLayout(), container, false);
		findView(view);
		createObject();
		setListener();
		getData();
		return view;
	}

	abstract protected int setLayout();

	protected abstract void findView(View view);

	abstract protected void createObject();

	abstract protected void setListener();

	abstract protected void getData();

	protected void showPDialog(){
		progressCount++;
		if(progressView==null) {
			progressView = CommonUtils.getProgressDialog(mActivity);
		}
		else{
			progressView.show();
		}

	}
	protected void dismissPDialog(){
		progressCount--;
		if(progressCount<=0) {
			if (progressView != null) {
				progressView.dismiss();
				progressCount=0;
			}
		}
	}

}
