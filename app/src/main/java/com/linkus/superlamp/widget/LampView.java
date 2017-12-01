package com.linkus.superlamp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class LampView extends View {
    private String TAG = getClass().getSimpleName();
    private int mWidth;
    private int mHeight;
    private Paint borderPaint;
    private Context mContext;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private float mLampCenter;
    private float mlampOutWith;
    private float mLampCenterX;
    private float mLampCenterY;
    private float mLampCenterRadius;
    private Paint mLinePaint;
    private float mLampCenterCircleRadius;
    private float mLampCircleCenterX;
    private float mLampCircleCenterY;

    //左边圆弧X
    private int mBottomLeftCenterX;
    //左边圆弧Y
    private float mBottomLeftCenterY;
    //右边圆弧X
    private int mBottomRightCenterX;
    //右边圆弧Y
    private float mBottomRightCenterY;


    //圆弧半径
    private float mBottomArcRadius;

    //画下面的画笔
    private Paint mBottomPaint;
    /**
     * 画字的画笔
     */
    private Paint mTextPaint;
    /**
     * 当前是否被禁止
     */
    private boolean mEnabled = true;

    ///////////
    private float ll;
    private float lt;
    private float lr;
    private float lb;
    ///////////
    private float rl;
    private float rt;
    private float rr;
    private float rb;
    /*
    左边的滑动角度
     */
    private double mLeftTouchDegrees;
    ////////////

    private int mCurrentState = STATE_OPEN;

    /**
     * 状态开
     */
    private static final int STATE_OPEN = 1;
    /**
     * 状态关
     */
    private static final int STATE_ClOSE = 2;
    /**
     * 开关区域
     */
    private static final int CURRENT_AFFECT_AREA_SWITCH = 0x2a;

    /**
     * 彩色白色
     */
    private static final int CURRENT_AFFECT_AREA_COLOR = 0x2b;

    /**
     * 刻度尺
     */
    private static final int CURRENT_AFFECT_AREA_SCALE = 0x2c;

    /**
     * 不响应区域
     */
    private static final int CURRENT_AFFECT_AREA_NONE = 0x2d;


    private int mCurrentEffectArea = CURRENT_AFFECT_AREA_NONE;

    /**
     * 色彩白
     */
    private static final int STATE_COLOR_WHITE = 3;
    /**
     * 色彩彩
     */
    private static final int STATE_COLOR_COLOUR = 4;


    private int mCurrentColorState = STATE_COLOR_WHITE;
    /**
     * 右下角滑动角度
     */
    private double mRightTouchDegree;

    String[] colorArray = {"白","彩"};

    private String mCurrentColorText = colorArray[0];

    String[] switchArray = {"开","关"};

    private String mCurrentSwitchStateText = switchArray[0];


    /**
     * 第一次点击响应需要处理的地方
     */
    private int firstAffectArea = CURRENT_AFFECT_AREA_NONE;


    public LampView(Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public LampView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public LampView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public LampView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.parseColor("#FF0000"));
        borderPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 5));
        borderPaint.setStyle(Paint.Style.STROKE);


        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.WHITE);

        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
        mPaddingRight = getPaddingRight();
        mPaddingBottom = getPaddingBottom();
        mLampCenter = 5f / 13;

        mBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        mBottomPaint.setStyle(Paint.Style.STROKE);
        mBottomPaint.setColor(Color.WHITE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawTop(canvas);
        drawBottomRight(canvas);
        drawBottomLeft(canvas);
        super.onDraw(canvas);
    }

    private void drawBottomRight(Canvas canvas) {
        RectF rectL = new RectF(
                mBottomRightCenterX - mBottomArcRadius,
                mBottomRightCenterY - mBottomArcRadius,
                mBottomRightCenterX + mBottomArcRadius,
                mBottomRightCenterY + mBottomArcRadius
        );
        canvas.drawArc(rectL, -180, 90, false, mBottomPaint);
        RectF rectRT = new RectF(
                rl,
                rt,
                rr,
                rb
        );
        canvas.drawOval(rectRT, mBottomPaint);
        mTextPaint.setTextSize(mBottomArcRadius / 4 * 0.6f);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        canvas.drawText(mCurrentColorText, (rl + rr - mTextPaint.measureText(mCurrentColorText)) / 2, (rt + rb + (bottom - top) / 2) / 2, mTextPaint);
        float lw = mBottomRightCenterX - mBottomArcRadius / 4;
        float tw = mBottomRightCenterY - mBottomArcRadius * 5 / 4;
        float rw = mBottomRightCenterX;
        float bw = mBottomLeftCenterY - mBottomArcRadius;
        canvas.drawText(colorArray[0], (lw + rw - mTextPaint.measureText(colorArray[0])) / 2, (tw + bw + (bottom - top) / 2) / 2, mTextPaint);
        float l = mBottomRightCenterX - mBottomArcRadius * 5 / 4;
        float t = mBottomLeftCenterY - mBottomArcRadius / 4;
        float r = l + mBottomArcRadius / 4;
        float b = mBottomLeftCenterY;
        canvas.drawText(colorArray[1],(l + r - mTextPaint.measureText(mCurrentColorText)) / 2,(t + b + (bottom - top) / 2) / 2,mTextPaint);
    }

    private void drawBottomLeft(Canvas canvas) {
        RectF rectL = new RectF(
                mBottomLeftCenterX - mBottomArcRadius,
                mBottomLeftCenterY - mBottomArcRadius,
                mBottomLeftCenterX + mBottomArcRadius,
                mBottomLeftCenterY + mBottomArcRadius
        );
        canvas.drawArc(rectL, -90, 90, false, mBottomPaint);

        RectF rectLT = new RectF(
                ll,
                lt,
                lr,
                lb
        );
        canvas.drawOval(rectLT, mBottomPaint);

        mTextPaint.setTextSize(mBottomArcRadius / 4 * 0.6f);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        canvas.drawText(mCurrentSwitchStateText, (ll + lr - mTextPaint.measureText(mCurrentSwitchStateText)) / 2, (lt + lb + (bottom - top) / 2) / 2, mTextPaint);
        float ol = mBottomLeftCenterX;
        float ot = mBottomLeftCenterY - mBottomArcRadius * 5 / 4;
        float or = mBottomLeftCenterX + mBottomArcRadius / 4;
        float ob = mBottomLeftCenterY - mBottomArcRadius;

        canvas.drawText(switchArray[0], (ol + or - mTextPaint.measureText(switchArray[0])) / 2, (ot + ob + (bottom - top) / 2) / 2, mTextPaint);

        float cl = mBottomLeftCenterX + mBottomArcRadius;
        float ct = mBottomLeftCenterY - mBottomArcRadius * 1 / 4;
        float cr = cl + mBottomArcRadius / 4;
        float cb = mBottomLeftCenterY;

        canvas.drawText(switchArray[1], (cl + cr - mTextPaint.measureText(switchArray[1])) / 2, (ct + cb + (bottom - top) / 2) / 2, mTextPaint);
    }

    private void drawTop(Canvas canvas) {
        mLinePaint.setColor(Color.WHITE);
        RectF rectF = new RectF(mLampCenterX - mLampCenterRadius,
                mLampCenterY - mLampCenterRadius,
                mLampCenterX + mLampCenterRadius,
                mLampCenterY + mLampCenterRadius);
        canvas.drawArc(rectF, 180 + 20, 140, false, mLinePaint);
        canvas.drawPoint(mLampCenterX, mLampCenterY, mLinePaint);
        float intervalDegress = 140 / 17f;
        canvas.rotate(20, mWidth / 2, mLampCenterY);
        Logger.i(TAG, "mLampCenterX - mLampCenterRadius " + (mLampCenterX - mLampCenterRadius));
        canvas.drawLine(mLampCenterX - mLampCenterRadius,
                mLampCenterY, mLampCenterX - mLampCenterRadius + ScreenUtil.dip2px(mContext, -20),
                mLampCenterY,
                mLinePaint);
        for (int i = 0; i < 17; i++) {
            canvas.rotate(intervalDegress, mWidth / 2, mLampCenterY);
            canvas.drawLine(mLampCenterX - mLampCenterRadius,
                    mLampCenterY, mLampCenterX - mLampCenterRadius + ScreenUtil.dip2px(mContext, -20),
                    mLampCenterY,
                    mLinePaint);
        }

        canvas.rotate(-140 - 20, mWidth / 2, mLampCenterY);

        //灯泡下面第一个大方块
        RectF rectB = new RectF(mLampCenterX - ScreenUtil.dip2px(mContext, 40),
                mLampCircleCenterY + mLampCenterCircleRadius - ScreenUtil.dip2px(mContext, 15)
                , mLampCenterX + ScreenUtil.dip2px(mContext, 40)
                , mLampCircleCenterY + mLampCenterCircleRadius + ScreenUtil.dip2px(mContext, 15));
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectB, 20, 20, mLinePaint);
        mLinePaint.setColor(Color.parseColor("#4d5d7b"));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        canvas.drawRoundRect(rectB, 20, 20, mLinePaint);

        //灯泡下面小大方块
        RectF rectFD = new RectF(rectB.left + ScreenUtil.dip2px(mContext, 25)
                , rectB.bottom + 2 * ScreenUtil.dip2px(mContext, 10)
                , rectB.right - ScreenUtil.dip2px(mContext, 25)
                , rectB.bottom + 2 * ScreenUtil.dip2px(mContext, 10) + ScreenUtil.dip2px(mContext, 35));
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectFD, 30, 30, mLinePaint);
        mLinePaint.setColor(Color.parseColor("#4d5d7b"));
        mLinePaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectFD, 30, 30, mLinePaint);
        // 4个小块
        for (int i = 0; i < 4; i++) {
            mLinePaint.setColor(Color.WHITE);
            mLinePaint.setStyle(Paint.Style.FILL);
            RectF rectFC = new RectF(rectB.left + ScreenUtil.dip2px(mContext, 10)
                    , rectB.bottom + ScreenUtil.dip2px(mContext, 10) * i
                    , rectB.right - ScreenUtil.dip2px(mContext, 10)
                    , rectB.bottom + ScreenUtil.dip2px(mContext, 10) * (i + 1));
            canvas.drawRoundRect(rectFC, 20, 20, mLinePaint);
            mLinePaint.setColor(Color.parseColor("#4d5d7b"));
            mLinePaint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rectFC, 20, 20, mLinePaint);
        }

        RectF rectFSmallCircle = new RectF(mLampCenterX + mLampCenterCircleRadius * (3f / 5) - ScreenUtil.dip2px(mContext, 10),
                mLampCenterY + mLampCenterCircleRadius * (3f / 5) - ScreenUtil.dip2px(mContext, 20),
                mLampCenterX + 2 * mLampCenterCircleRadius * (3f / 5) - ScreenUtil.dip2px(mContext, 10),
                mLampCenterY + 2 * mLampCenterCircleRadius * (3f / 5) - ScreenUtil.dip2px(mContext, 20)
        );

        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.FILL);
        canvas.drawOval(rectFSmallCircle, mLinePaint);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.parseColor("#4d5d7b"));
        canvas.drawOval(rectFSmallCircle, mLinePaint);


        //大圆开始
        RectF rectFCircle = new RectF(mLampCenterX - mLampCenterCircleRadius,
                mLampCircleCenterY - mLampCenterCircleRadius,
                mLampCenterX + mLampCenterCircleRadius,
                mLampCircleCenterY + mLampCenterCircleRadius);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.FILL);
        canvas.drawOval(rectFCircle, mLinePaint);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.parseColor("#4d5d7b"));
        canvas.drawOval(rectFCircle, mLinePaint);


    }

    private void drawBorder(Canvas canvas) {
        canvas.drawRect(new RectF(0, 0, mWidth, mHeight), borderPaint);
//        canvas.scale(0.5f,0.5f);
////        canvas.drawRect(new RectF(0,0,mWidth*0.5f,mHeight*0.5f),borderPaint);
//        borderPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(new RectF(0,0,mWidth,mHeight),borderPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(),
                widthMeasureSpec);
        Logger.i(TAG, "onMeasure : " + width + " == " + height);
        mWidth = width;
        mHeight = height;


        mlampOutWith = mWidth * 0.8f;
        mLampCenterX = (float) Math.ceil((mWidth - (mPaddingLeft + mPaddingRight)) / 2);
        mLampCenterY = (float) Math.ceil(mLampCenter * (mHeight - (mPaddingLeft + mPaddingBottom)));
        mLampCenterRadius = (float) Math.ceil((mWidth - (mPaddingLeft + mPaddingRight)) / 2) - 80;

//        mLampCircleCenterX = mLampCenterX - 30;
        mLampCircleCenterY = mLampCenterY - 30;
        mLampCenterCircleRadius = mLampCenterRadius - 100;


        mBottomLeftCenterX = mPaddingLeft + ScreenUtil.dip2px(mContext, 20);
        mBottomLeftCenterY = (mHeight - (mPaddingLeft + mPaddingBottom)) * 0.9f;


        mBottomRightCenterX = ScreenUtil.getScreenWidth(mContext) - mPaddingRight - ScreenUtil.dip2px(mContext, 20);

        mBottomRightCenterY = mBottomLeftCenterY;


        mBottomArcRadius = ScreenUtil.getScreenWidth(mContext) * 1f / 4;

/////////////////////////////////////////////////////////////

        ll = mBottomLeftCenterX;
        lt = mBottomLeftCenterY - mBottomArcRadius * 5 / 4;
        lr = mBottomLeftCenterX + mBottomArcRadius / 4;
        lb = mBottomLeftCenterY - mBottomArcRadius;

        mCurrentState = STATE_OPEN;
//////////////////////////////////////////////////////////////////////////
        rl = mBottomRightCenterX - mBottomArcRadius / 4;
        rt = mBottomRightCenterY - mBottomArcRadius * 5 / 4;
        rr = mBottomRightCenterX;
        rb = mBottomLeftCenterY - mBottomArcRadius;

        mCurrentColorState = STATE_COLOR_WHITE;


//////////////////////////////////////////////////////////////////////

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.i(TAG, "event : " + "event.getX()" + event.getX() + "event.getY()" + event.getY());
        if (mEnabled) {
            this.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                    smothlyGo();
                    firstAffectArea = CURRENT_AFFECT_AREA_NONE;//reset
//                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
//                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void smothlyGo() {
//        Logger.i(TAG, "mLeftTouchDegrees:" + mLeftTouchDegrees);
        if (mCurrentEffectArea == CURRENT_AFFECT_AREA_SWITCH) {
            //[0,90]
            if (mLeftTouchDegrees >= 45 && mLeftTouchDegrees <= 90) {
                smoothLeftForward(mLeftTouchDegrees, 90.0d);
                mCurrentState = STATE_OPEN;
            } else {
                smoothLeftForward(mLeftTouchDegrees, 00.0d);
                mCurrentState = STATE_ClOSE;
            }
        }
        if (mCurrentEffectArea == CURRENT_AFFECT_AREA_COLOR) {
            //[-90,0]
            if (mRightTouchDegree <= -45) {
                smoothRightForward(mRightTouchDegree, -89.6930f);
                mCurrentColorState = STATE_COLOR_COLOUR;
            } else {
                smoothRightForward(mRightTouchDegree, 0.01f);
                mCurrentColorState = STATE_COLOR_WHITE;
            }
        }

    }

    private void smoothRightForward(double curDeg, double toDeg) {
        double step = 0.5;
        if (curDeg < toDeg) {
            for (; curDeg <= toDeg; ) {
                curDeg += step;
                translateRightBottom(curDeg);
            }
        } else {
            for (; curDeg >= toDeg; ) {
                curDeg -= step;
                translateRightBottom(curDeg);
            }
        }
    }

    private void smoothLeftForward(double curDeg, final double toDeg) {
        final double step = 0.5;
        if (curDeg < toDeg) {
            for (; curDeg <= toDeg; ) {
                curDeg += step;
                translateLeftBottom(curDeg);
            }
        } else {
            for (; curDeg >= toDeg; ) {
                curDeg -= step;
                translateLeftBottom(curDeg);
            }
        }
    }

    private void updateOnTouch(MotionEvent event) {
        boolean ignoreTouch = ignoreTouch(event.getX(), event.getY());
        Logger.i(TAG, "ignoreTouch : " + ignoreTouch);
//        if ()
        if (ignoreTouch) {
            return;
        }
        setPressed(true);
        if (mCurrentEffectArea == CURRENT_AFFECT_AREA_SWITCH) {
            handleSwitchArea(event);
        } else if (mCurrentEffectArea == CURRENT_AFFECT_AREA_COLOR) {
            Logger.i(TAG, "ignoreTouch : CURRENT_AFFECT_AREA_COLOR");
            handleSwitchColor(event);
        } else if (mCurrentEffectArea == CURRENT_AFFECT_AREA_SCALE) {
//            handleSwitchArea(event);
        } else {
            //do nothing
        }

    }

    private void handleSwitchColor(MotionEvent event) {
        //获取到旋转角度
        mRightTouchDegree = getTouchDegreesinColor(event.getX(), event.getY(), mBottomRightCenterX, mBottomRightCenterY);

        if (mRightTouchDegree >= -45){
            mCurrentColorText = colorArray[0];
        }else {
            mCurrentColorText = colorArray[1];
        }
        Logger.i(TAG, "Color : degree " + mRightTouchDegree);
//        mRightTouchDegree = Math.abs(mRightTouchDegree);
        translateRightBottom(mRightTouchDegree);
    }

    private void translateRightBottom(double degrees) {
        float dx = (float) (mBottomArcRadius * 4 / 4 * Math.sin(Math.toRadians(-degrees)));
        float dy = (float) (mBottomArcRadius * 4 / 4 * Math.cos(Math.toRadians(-degrees)));
//        Logger.i(TAG, "dx :" + dx + "dy :" + dy);//225.0
        rr = mBottomRightCenterX - dx;
        rb = mBottomRightCenterY - dy;
        rt = rb - mBottomArcRadius / 4;
        rl = rr - mBottomArcRadius / 4;
        invalidate();
    }

    private void handleSwitchArea(MotionEvent event) {
        //获取到旋转角度
        mLeftTouchDegrees = getTouchDegrees(event.getX(), event.getY(), mBottomLeftCenterX, mBottomLeftCenterY);
        Logger.i(TAG, "AAA : degree " + mLeftTouchDegrees);
        mLeftTouchDegrees = Math.abs(mLeftTouchDegrees);
        if (mLeftTouchDegrees >= 45) {
            mCurrentSwitchStateText = switchArray[1];
        } else {
            mCurrentSwitchStateText = switchArray[0];
        }
        translateLeftBottom(mLeftTouchDegrees);
    }

    private void translateLeftBottom(double leftDegree) {
        if (leftDegree > 90 || leftDegree < 0) {
            return;
        }
//        Logger.i(TAG, "sin " + Math.sin(leftDegree) + "cos " + Math.cos(leftDegree));
//        Logger.i(TAG, "translateLeftBottom " + leftDegree);
        float dx = (float) (mBottomArcRadius * 4 / 4 * Math.sin(Math.toRadians(leftDegree)));
        float dy = (float) (mBottomArcRadius * 5 / 4 * Math.cos(Math.toRadians(leftDegree)));
//        Logger.i(TAG, "dx :" + dx + "dy :" + dy);//225.0
        if (dx < 0) {
            return;
        }
        ll = mBottomLeftCenterX + dx;
        lr = ll + mBottomArcRadius * 1 / 4;
        lt = mBottomLeftCenterY - dy;
        if (lt >= mBottomLeftCenterY - mBottomArcRadius / 4) {
            lt = mBottomLeftCenterY - mBottomArcRadius / 4;
        }
        lb = lt + mBottomArcRadius * 1 / 4;

//        RectF rectF = new RectF(ll, lt, lr, lb);
//        Logger.i(TAG, "rectF : " + rectF.toString());
        invalidate();
    }

    private boolean ignoreTouch(float xPos, float yPos) {
        boolean ignore = true;
        //左下角
        float x1 = xPos - mBottomLeftCenterX;
        float y1 = yPos - mBottomLeftCenterY;
//        Logger.i(TAG, "mBottomLeftCenterX: " + mBottomLeftCenterX + "mBottomLeftCenterY" + mBottomLeftCenterY + "mBottomArcRadius: " + mBottomArcRadius);
        float touchRadius1 = (float) Math.sqrt(((x1 * x1) + (y1 * y1)));
        Logger.i(TAG, "touchRadius1: " + touchRadius1 + "x:" + x1 + "Y:" + y1);
        if (touchRadius1 >= mBottomArcRadius - 180 && touchRadius1 <= mBottomArcRadius + 180) {
            if (firstAffectArea != CURRENT_AFFECT_AREA_NONE && firstAffectArea != CURRENT_AFFECT_AREA_SWITCH) {//移除一次move 多次set
                return true;
            }
            mCurrentEffectArea = CURRENT_AFFECT_AREA_SWITCH;
            firstAffectArea = mCurrentEffectArea;
            ignore = false;
            return ignore;
        }
        //右下角
        float x2 = xPos - mBottomRightCenterX;
        float y2 = yPos - mBottomRightCenterY;
        float touchRadius2 = (float) Math.sqrt(((x2 * x2) + (y2 * y2)));
        if (touchRadius2 >= mBottomArcRadius - 180 && touchRadius2 <= mBottomArcRadius + 180) {
            if (firstAffectArea != CURRENT_AFFECT_AREA_NONE && firstAffectArea != CURRENT_AFFECT_AREA_COLOR) {//移除一次move 多次set
                return true;
            }
            mCurrentEffectArea = CURRENT_AFFECT_AREA_COLOR;
            firstAffectArea = mCurrentEffectArea;
            ignore = false;
            return ignore;
        }
        return ignore;
    }

    private double getTouchDegrees(float xPos, float yPos, float orignX, float orignY) {
        float x = xPos - orignX;
        float y = yPos - orignY;
        //invert the x-coord if we are rotating anti-clockwise
//        x= (mClockwise) ? x:-x;
        // convert to arc Angle
        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2));
        Logger.i(TAG, "angle is : " + angle);
        if (angle >= 90) {
            angle = 90;
        }
        if (angle >= 0 && angle <= 90) {
            return angle;
        }
//        angle -= mStartAngle;
        return -1;
    }

    private double getTouchDegreesinColor(float xPos, float yPos, float orignX, float orignY) {
        float x = xPos - orignX;
        float y = yPos - orignY;
        //invert the x-coord if we are rotating anti-clockwise
//        x= (mClockwise) ? x:-x;
        // convert to arc Angle
        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2));
        Logger.i(TAG, "angle is : " + angle);
        if (angle >= 0 && angle < 90) {
            angle = 0;
        }
        if (angle >= 180) {
            angle = -90;
        }
        return angle;
//        angle -= mStartAngle;
    }

    public void setEnabled(boolean enable) {
        this.mEnabled = enable;
    }


    public int getCurrentState() {
        return mCurrentState;
    }

    /**
     * 设置当前状态
     * @param state{ optional
     *             STATE_OPEN
     *            ,STATE_CLOSE}
     */
    public void setCurrentState(int state){
        mCurrentState = state;
    }

    public int getCurrentColorState() {
        return mCurrentColorState;
    }
    /**
     * 设置当前颜色
     * @param colorState{ optional
     *             STATE_COLOR_WHITE
     *            ,STATE_COLOR_COLOUR}
     */
    public void setCurrentEffectArea(int colorState) {
        this.mCurrentColorState = colorState;
    }
}
