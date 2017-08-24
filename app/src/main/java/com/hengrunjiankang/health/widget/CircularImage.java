package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
/**
 * 圆形头像
 * @author 1989.12.16
 *
 */
public class CircularImage extends MaskedImage {
	public CircularImage(Context paramContext) {
		super(paramContext);
	}

	public CircularImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CircularImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public Bitmap createMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint(1);
		localPaint.setColor(Color.BLUE);
		float f1 = getWidth();
		float f2 = getHeight();
		RectF localRectF = new RectF(1F, 1F, f1-1, f2-1);
//		RectF localRectF = new RectF(0,0, f1, f1);
		localCanvas.drawOval(localRectF, localPaint);
		return localBitmap;
	}
}
