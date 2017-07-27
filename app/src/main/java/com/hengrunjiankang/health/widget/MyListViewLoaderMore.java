package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hengrunjiankang.health.R;

public class MyListViewLoaderMore extends ListView {
    private OnLoader loader;
    private Context mContext;

    public void setLoader(OnLoader loader) {
        this.loader = loader;
    }

    public MyListViewLoaderMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init(context);
    }

    public MyListViewLoaderMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init(context);
    }

    public MyListViewLoaderMore(Context context) {
        super(context);
        mContext=context;
        init(context);
    }

    ViewGroup footerView;

    protected void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        footerView = (ViewGroup) inflater.inflate(R.layout.layout_progress_dialog, null);
         handler= new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                removeFooterView(footerView);
                return false;
            }
        });
    }

    private boolean isLoadFinish = true;
    private int temptop = -1;
    private int tempt;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }
    private void setOnload(){
        isLoadFinish=false;
        loader.onLoader();
    }

    private void addLoadView() {
        addFooterView(footerView);
    }

    public OnLoader getLoader() {
        return loader;
    }

    public boolean isLoadFinish() {
        return isLoadFinish;
    }

    public void setLoadFinish() {
//        isLoadFinish = true;
//        removeFooterView(footerView);
    }

    Handler handler;

    public static interface OnLoader {
        void onLoader();
    }
//    private boolean isOnLoader() {
//        boolean result = false;
//        if (getLastVisiblePosition() == (getCount() - 1)) {
//            View bottomChildView = getChildAt(getLastVisiblePosition()
//                    - getFirstVisiblePosition());
//            if(bottomChildView==null)
//                return false;
//            result = (getHeight() >=bottomChildView.getBottom());
//            Log.e("bottom", bottomChildView.getBottom() + "," + getHeight());
//
//        }
//        if(getAdapter()==null)
//            return false;
//        return result;
//    }
    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (getAdapter() != null) {
            return getLastVisiblePosition() == (getAdapter().getCount() - 1);
        }
        return false;
    }
    private int downy;
    private int movey;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                downy = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                movey = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (isPullUp()&&isBottom()&&isLoadFinish) {
                    isLoadFinish=false;
                    addLoadView();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            setOnload();
                        }
                    });

                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (downy - movey) >= 50;
    }

}
