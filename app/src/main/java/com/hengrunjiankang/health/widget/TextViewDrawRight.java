package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.hengrunjiankang.health.R;
import com.hengrunjiankang.health.util.CommonUtils;

public class TextViewDrawRight extends AppCompatTextView{
	private int drawthanmore;

	public TextViewDrawRight(Context context, AttributeSet attrs,
                             int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		TypedArray arr = context.obtainStyledAttributes(attrs,
				R.styleable.TextViewDrawRight);
		drawthanmore = CommonUtils.dip2px(context, arr.getDimensionPixelSize(
				R.styleable.TextViewDrawRight_drawthan, 0));
		arr.recycle();
		setIcon();

	}

	public TextViewDrawRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if (isInEditMode()) { return; }

		TypedArray arr = context.obtainStyledAttributes(attrs,
				R.styleable.TextViewDrawRight);
		drawthanmore = CommonUtils.dip2px(context, arr.getDimensionPixelSize(
				R.styleable.TextViewDrawRight_drawthan, 0));
		arr.recycle();
		setIcon();
	}

	public TextViewDrawRight(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setIcon();
	}

	private void setIcon() {
		int i = (int) getTextSize();
		getCompoundDrawables()[2].setBounds(0, 0, i + drawthanmore, i
				+ drawthanmore);
		setCompoundDrawables(null, null,getCompoundDrawables()[2], null);
	}

}
