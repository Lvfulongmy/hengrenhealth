package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hengrunjiankang.health.entity.LineData;
import com.hengrunjiankang.health.entity.PointData;
import com.hengrunjiankang.health.entity.YTextData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by 56574 on 2017/6/1.
 */

public class MyView extends View {
    private int width;
    private int height;
    private Context mContext;
    private int xStep;
    private int yStep;
    private int xTextStep;
    private int yTextStep;
    public static final int WEEK = 1;
    public static final int DAY = 0;
    public static final int MONTH = 2;
    private static int mode;
    private long starttime;
    private ArrayList<LineData> lineDataList;
    private ArrayList<String> xTextList;
    private ArrayList<String> yTextList;
    private int textColorId = -1;//文字颜色
    private int lineColorId = -1;//辅助线颜色
    private int axisColorId = -1;//坐标轴颜色

    public static int getMode() {
        return mode;
    }

    public ArrayList<LineData> getLineData() {
        return lineDataList;
    }

    public void setLineData(ArrayList<LineData> lineData) {
        this.lineDataList = lineData;
    }

    public float getXleftoffset() {
        return xleftoffset;
    }

    public void setXleftoffset(float xleftoffset) {
        this.xleftoffset = xleftoffset;
    }

    public float getXrightoffset() {
        return xrightoffset;
    }

    public void setXrightoffset(float xrightoffset) {
        this.xrightoffset = xrightoffset;
    }

    public float getYtopoffset() {
        return ytopoffset;
    }

    public void setYtopoffset(float ytopoffset) {
        this.ytopoffset = ytopoffset;
    }

    public float getYbottomoffset() {
        return ybottomoffset;
    }

    public void setYbottomoffset(float ybottomoffset) {
        this.ybottomoffset = ybottomoffset;
    }

    public void setMode(int mode, long starttime, YTextData yTextData) {
        this.mode = mode;
        this.starttime = starttime;
        yStep = 100;
        yTextStep = yTextData.getYstep();
        yTextList = yTextData.getYtextlist();
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(starttime);
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");

        xTextList = new ArrayList<>();
        switch (mode) {
            case DAY:
                xStep = 24;
                xTextStep = 7;
                for (int i = 0; i <=xTextStep; i++) {
                    xTextList.add((i * 4) + "h");
                }
                break;
            case WEEK:
                xStep = 7;
                xTextStep = 7;
                xTextList.add(format.format(cl.getTime()));
                for (int i = 0; i <xTextStep-1; i++) {
                    long time = cl.getTimeInMillis() + (24 * 60 * 60 * 1000);
                    cl.clear();
                    cl.setTimeInMillis(time);
                    xTextList.add(format.format(cl.getTime()));
                }
                break;
            case MONTH:
                xStep = 30;
                xTextStep = 6;
                xTextList = new ArrayList<>();
                xTextList.add(format.format(cl.getTime()));
                for (int i = 0; i <xTextStep-1; i++) {
                    long time = cl.getTimeInMillis() +(24 * 60 * 60 * 1000 * 6);
                    cl.clear();
                    cl.setTimeInMillis(time);
                    xTextList.add(format.format(cl.getTime()));
                }
                break;
        }
    }

    private int timeToXLocation(Long time) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(starttime);
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        switch (mode) {
            case DAY:
                return (int) ((time - cl.getTimeInMillis()) / (60 * 60 * 1000));
            case WEEK:
                return (int) ((time - cl.getTimeInMillis()) / (24 * 60 * 60 * 1000));
            case MONTH:
                return (int) ((time - cl.getTimeInMillis()) / (24 * 60 * 60 * 1000));

        }
        return -1;
    }

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context context) {
        mContext = context;
        xleftoffset = dip2px(context, 20);
        xrightoffset = dip2px(context, 20);
        ytopoffset = dip2px(context, 20);
        ybottomoffset = dip2px(context, 20);
        textSize = dip2px(context, 6);
        pointR = dip2px(context, 3);
        lineWidth = dip2px(context, 2);
    }

    private float xleftoffset;
    private float xrightoffset;
    private float ytopoffset;
    private float ybottomoffset;
    private float textSize;

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        Paint paintLine = new Paint();
        if (lineColorId != -1) {
            paintLine.setColor(ContextCompat.getColor(mContext, lineColorId));
        } else {
            paintLine.setColor(Color.BLACK);
        }
        paintLine.setStrokeWidth(1);
        Paint paintAxis = new Paint();
        paintAxis.setStrokeWidth(3);

        if (axisColorId != -1) {
            paintAxis.setColor(ContextCompat.getColor(mContext, axisColorId));
        } else {
            paintAxis.setColor(Color.BLACK);
        }
        Paint paintText = new Paint();
        paintText.setTextSize(textSize);
        if (textColorId != -1) {
            paintText.setColor(ContextCompat.getColor(mContext, textColorId));
        } else {
            paintText.setColor(Color.BLACK);
        }
        paintLine.setAntiAlias(true);
        paintAxis.setAntiAlias(true);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        float spaceY = (height - ytopoffset - ybottomoffset) / yTextStep;
        for (int i = 0; i < yTextStep; i++) {
            canvas.drawLine(xleftoffset, ytopoffset + i * spaceY, (float) width, ytopoffset + i * spaceY, paintLine);
            canvas.drawText(yTextList.get(i), 0f, ytopoffset + i * spaceY, paintText);
        }//绘制辅助线和y轴文字
//        canvas.drawLine(xleftoffset, 0, xleftoffset, height - ybottomoffset, paintAxis);//画纵轴
        canvas.drawLine(xleftoffset, height - ybottomoffset, width, height - ybottomoffset, paintAxis);//画横轴
        float spaceX = (width - xleftoffset - xrightoffset) / (xTextStep-1);
        for (int i = 0; i < xTextStep; i++) {
            canvas.drawText(xTextList.get(i), xleftoffset + (i * spaceX), height, paintText);
        }//画x轴文字
        if (lineDataList != null) {
            drawData(canvas);
        }

    }

    private float pointR;
    private float lineWidth;

    private void drawData(Canvas canvas) {

        float r = pointR;
        Paint paint = new Paint();
        paint.setStrokeWidth(lineWidth);

        for (int i = 0; i < lineDataList.size(); i++) {
            paint.setColor(ContextCompat.getColor(mContext, lineDataList.get(i).getColor()));
            Log.e("lanDateListsize:"+mode,lineDataList.get(i).getDate().size()+"");
            for (int j = 0; j < lineDataList.get(i).getDate().size(); j++) {
                float[] xy = toPoint(lineDataList.get(i).getDate().get(j));

                Log.e("lanDateListsize:"+mode,format2.format(lineDataList.get(i).getDate().get(j).getDate())+"");
                float x = xy[0];
                float y = xy[1];
                RectF rectf = new RectF();
                rectf.top = y - r;
                rectf.bottom = y + r;
                rectf.left = x - r;
                rectf.right = x + r;
                canvas.drawOval(rectf, paint);
                if (j != lineDataList.get(i).getDate().size() - 1) {
                    float[] nxy = toPoint(lineDataList.get(i).getDate().get(j + 1));
                    float nx = nxy[0];
                    float ny = nxy[1];
                    canvas.drawLine(x, y, nx, ny, paint);
                }
            }
        }
    }

    private float[] toPoint(PointData data) {
        float xspace = (width - xleftoffset - xrightoffset) / (xStep-1);
        float yspace = (height - ytopoffset - ybottomoffset) / yStep;
        int xLocation = timeToXLocation(data.getDate());
        float x = xleftoffset + xLocation * xspace;
        int yLocation = yStep - (data.getIndex());
        float y = ytopoffset + yspace * yLocation;
        float[] arr = {x, y};
        return arr;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("tag", event.getX() + "," + event.getY());
            for(int i=0;i<lineDataList.size();i++){
                for(int j=0;j<lineDataList.get(i).getDate().size();j++){
                    float[] xy=toPoint(lineDataList.get(i).getDate().get(j));
                    if(Math.abs(event.getX()-xy[0])<100&&Math.abs(event.getY()-xy[1])<100){
                        Toast.makeText(mContext,format2.format(lineDataList.get(i).getDate().get(j).getDate()) , Toast.LENGTH_SHORT).show();
                        i=lineDataList.size();
                        break;
                    }
                }
            }
        }

        return true;
    }
}
