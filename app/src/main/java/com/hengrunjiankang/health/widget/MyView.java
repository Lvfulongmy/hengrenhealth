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
import android.view.View;

import com.hengrunjiankang.health.entity.LineData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    private int textColorId = -1;
    private int lineColorId = -1;
    private int axisColorId = -1;

    public static int getMode() {
        return mode;
    }

    public ArrayList<LineData> getLineData() {
        return lineDataList;
    }

    public void setLineData(ArrayList<LineData> lineData) {
        this.lineDataList = lineData;
    }

    public void setMode(int mode, long starttime) {
        this.mode = mode;
        this.starttime = starttime;
        yStep = 100;
        yTextStep = 10;
        yTextList = new ArrayList<>();
        for (int i = 100; i > 0; i -= 10) {
            yTextList.add(i + "%");
        }
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(starttime);
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        xTextList = new ArrayList<>();
        switch (mode) {
            case DAY:
                xStep = 24;
                xTextStep = 6;
                for (int i = 0; i < xTextStep; i++) {
                    xTextList.add(((i + 1) * 4) + "h");
                }
                break;
            case WEEK:
                xStep = 7;
                xTextStep = 7;
                for (int i = 0; i < 7; i++) {
                    long time = cl.getTimeInMillis() + 24 * 60 * 60 * 1000;
                    cl.clear();
                    cl.setTimeInMillis(time);
                    xTextList.add(format.format(cl.getTime()));
                }
                break;
            case MONTH:
                xStep = 30;
                xTextStep = 6;
                xTextList = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    long time = cl.getTimeInMillis() + 24 * 60 * 60 * 1000 * 6;
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
        long startTime = cl.getTimeInMillis();
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
    }

    float xleftoffset = 100;
    float xrightoffset = 100;
    float ytopoffset = 100;
    float ybottomoffset = 100;

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
        paintText.setTextSize(20);
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
        }
        canvas.drawLine(xleftoffset, 0, xleftoffset, height - ybottomoffset, paintAxis);
        canvas.drawLine(xleftoffset, height - ybottomoffset, width, height - ybottomoffset, paintAxis);
        float spaceX = (width - xleftoffset - xrightoffset) / xTextStep;
        for (int i = 0; i < xTextStep; i++) {
            canvas.drawText(xTextList.get(i), xleftoffset + ((i + 1) * spaceX), height, paintText);
        }
        if (lineDataList != null) {
            drawData(canvas);
        }

    }

    private void drawData(Canvas canvas) {
        float xspace = (width - xleftoffset - xrightoffset) / xStep;
        float yspace = (height - ytopoffset - ybottomoffset) / yStep;
        float r = 5;
        Paint paint = new Paint();
        paint.setStrokeWidth(2);

        for (int i = 0; i < lineDataList.size(); i++) {
            paint.setColor(ContextCompat.getColor(mContext, lineDataList.get(i).getColor()));
            for (int j = 0; j < lineDataList.get(i).getDate().size(); j++) {
                int xLocation = timeToXLocation(lineDataList.get(i).getDate().get(j).getDate());
                float x = xleftoffset + xLocation * xspace + xStep;
                int yLocation = 100 - (lineDataList.get(i).getDate().get(j).getIndex());
                float y = ytopoffset + yspace * yLocation;

                RectF rectf = new RectF();
                rectf.top = y - r;
                rectf.bottom = y + r;
                rectf.left = x - r;
                rectf.right = x + r;
                canvas.drawOval(rectf, paint);
                if(i!=lineDataList.size()-1){
                    int nxl=timeToXLocation(lineDataList.get(i).getDate().get(j+1).getDate());
                    float nx=xleftoffset + nxl* xspace + xStep;
                    int nyl=100 - (lineDataList.get(i).getDate().get(j+1).getIndex());
                    float ny=ytopoffset + yspace * nyl;
                    canvas.drawLine(x,y,nx,ny,paint);
                }
            }
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
