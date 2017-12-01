package com.linkus.superlamp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.linkus.superlamp.R;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;

/**
 * Created by 54757 on 10/12/2017.
 */

public class BorderRadioButton extends AppCompatRadioButton {
    private int mWidth;
    private int mHeight;
    private Paint borderPaint;
    private Context mContext;
    private int mCheckedColor = Color.RED;
    private int mDefaultColor = Color.WHITE;
    private int borderWidth;

    public int mType;



    public BorderRadioButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BorderRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BorderRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContext = context;
        borderWidth = ScreenUtil.dip2px(mContext, 2);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked()) {
            borderPaint.setColor(getResources().getColor(R.color.yellow_choose));
            setTextColor(getResources().getColor(R.color.yellow_choose));
        } else {
            setTextColor(getResources().getColor(R.color.white));
            borderPaint.setColor(mDefaultColor);
        }
        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        RectF rect = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
//        float rx = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
//        float ry = (getHeight() - getPaddingBottom() - getPaddingBottom()) / 2;//切图吧
        canvas.drawRoundRect(rect, ScreenUtil.dip2px(mContext, 10), ScreenUtil.dip2px(mContext, 10), borderPaint);
    }

    public void setCheckedColor(int color) {
        mCheckedColor = color;
    }

    public void setBorderWith(int dp) {
        if (dp < 0) {
            return;
        }
        borderWidth = ScreenUtil.dip2px(mContext, dp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(),
                widthMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }
}
