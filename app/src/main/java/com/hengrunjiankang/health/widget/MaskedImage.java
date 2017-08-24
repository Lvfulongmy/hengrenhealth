package com.hengrunjiankang.health.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public abstract class MaskedImage extends AppCompatImageView {
	private static final Xfermode MASK_XFERMODE;
	private Bitmap mask;
	private Paint paint;

	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public MaskedImage(Context paramContext) {
		super(paramContext);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public abstract Bitmap createMask();

	protected void onDraw(Canvas paramCanvas) {
		if (isInEditMode()) {
			return;
		}
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			if (this.paint == null) {
				this.paint = new Paint();
				this.paint.setFilterBitmap(false);
				this.paint.setXfermode(MASK_XFERMODE);
			}
			float f1 = getWidth();
			float f2 = getHeight();
			int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2,null,-1);
			int j = getWidth();
			int k = getHeight();
			localDrawable.setBounds(0, 0, j, k);
			localDrawable.draw(paramCanvas);
			if ((this.mask == null) || (this.mask.isRecycled())) {
				this.mask = createMask();
			}
			RectF localRectF = new RectF(0,0, f1, f1);
			paramCanvas.drawBitmap(this.mask, 0.0F, 0.0F, this.paint);
			paramCanvas.restoreToCount(i);

			return;
		} catch (Exception localException) {
			StringBuilder localStringBuilder = new StringBuilder()
					.append("Attempting to draw with recycled bitmap. View ID = ");
			System.out.println("localStringBuilder==" + localStringBuilder);
		}
	}
	
}
