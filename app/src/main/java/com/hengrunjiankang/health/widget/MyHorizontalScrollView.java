package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2017/8/17.
 */
public class MyHorizontalScrollView extends HorizontalScrollView implements
        View.OnTouchListener {
    private int changed;
    public static MyHorizontalScrollView nowOpen;

    public MyHorizontalScrollView(Context context) {

        super(context);
        // TODO Auto-generated constructor stu
        changed = dip2px(context, 50f);
        setOnTouchListener(this);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs,
                                  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        changed = dip2px(context, 50f);
        // TODO Auto-generated constructor stub
        setOnTouchListener(this);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        changed = dip2px(context, 50f);
        setOnTouchListener(this);
    }

    int left;
    int oldleft;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);
        left = l;
        oldleft = oldl;

    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     */


    /**
     * dp转px方法
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int touchEventId = -9983761;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View scroller = (View) msg.obj;
            if (msg.what == touchEventId) {
                onStop();
            }
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_POINTER_UP
                || event.getAction() == MotionEvent.ACTION_UP) {
            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v),
                    5);
        }
        return false;
    }

    private void onStop() {
        if (oldleft < changed) {
            smoothScrollTo(0, 0);
        }
        if (oldleft > changed) {
            if(nowOpen!=null&&nowOpen!=this) {
                nowOpen.scrollTo(0, 0);
            }
            nowOpen=this;
            smoothScrollTo(changed * 2, 0);

        }
    }
}
