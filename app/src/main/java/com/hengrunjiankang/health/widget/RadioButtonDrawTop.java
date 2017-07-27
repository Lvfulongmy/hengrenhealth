package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class RadioButtonDrawTop extends RadioButton {

	public RadioButtonDrawTop(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		setIcon();
	}

	public RadioButtonDrawTop(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setIcon();
	}

	public RadioButtonDrawTop
			(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setIcon();
	}

	private void setIcon() {
		int i = (int) getTextSize();
//		int i=(int)getHeight();
		getCompoundDrawables()[1].setBounds(0, 0, i*2, i*2);
		setCompoundDrawables(null,getCompoundDrawables()[1], null, null);
	}

}
