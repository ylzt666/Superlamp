package com.linkus.superlamp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 54757 on 11/3/2017.
 */

public class RadiusImageView extends ImageView {
    private Paint mRadiusPaint ;

    public RadiusImageView(Context context) {
        this(context, null,0);
    }

    public RadiusImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadiusImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mRadiusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadiusPaint.setStyle(Paint.Style.STROKE);
        mRadiusPaint.setStrokeWidth(2);
        mRadiusPaint.setColor(Color.RED);
        setDrawingCacheEnabled(true);
        setWillNotCacheDrawing(false);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        Bitmap drawingCache = getDrawingCache();
        canvas.drawBitmap(drawingCache,0,0,mRadiusPaint);
        float[] radii = { 20f, 20f, 20f, 20f,0f, 0f, 0f, 0f,};
        Path path = new Path();
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.drawPath(path,mRadiusPaint);
//        drawTopRadius(canvas);
        setDrawingCacheEnabled(false);
    }

    private void drawTopRadius(Canvas canvas) {
//        mRadiusPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));





    }

    @Override
    protected void dispatchDraw(Canvas canvas) {




        super.dispatchDraw(canvas);
    }
}
