package com.linkus.superlamp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import com.linkus.superlamp.R;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class LampLayout extends View {

    public   float base_start = 0;
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


    // 是否是白灯，true白灯，false彩灯
    private boolean isColdWarmLamp = false;


    private static final int START_ANGLE = 135;
    private static final int MAX_SWEEP_ANGLE = 270;

    private int progressWidth;

    /**
     * 渐变色Y的坐标
     */
    private float mLampCenterY;
    /**
     * 渐变色的半径
     */
    private float mLampCenterRadius;


    private Paint mLinePaint;
    private Paint mTestPaint;
    private float mLampCenterCircleRadius;
    private float mLampCircleCenterX;
    private float mLampCircleCenterY;

    /**
     * 渐变画笔
     */
    private Paint mTopGraditPaint;

    /**
     * 圆弧画笔
     */
    private Paint mTopRingPaint;


    /**
     * 当前是否被禁止
     */
    private boolean mEnabled = true;


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

    String[] colorArray = {"白", "彩"};

    private String mCurrentColorText = colorArray[0];

    String[] switchArray = {"开", "关"};

    private String mCurrentSwitchStateText = switchArray[0];


    private Paint progressBgPaint;
    private RectF rectF;

    private float wheelWidth;
    private float wheelHeight;
    private Paint draggerPaint;

    private RectF rotateR;

    private float totalDegree;

    private float sweepAngle;
    private boolean isInit = true;

    private Drawable mPointIcon;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    public  int  coldStart = 7500;
    public int coldEnd = 2700;
    private boolean shouldSkip = false;


    public LampLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public LampLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public LampLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LampLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }


    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        mContext = context;
        progressWidth = ScreenUtil.dip2px(mContext, 30);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.parseColor("#FF0000"));
        borderPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 5));
        borderPaint.setStyle(Paint.Style.STROKE);

        mTopGraditPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTopGraditPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 30));
        mTopGraditPaint.setStyle(Paint.Style.STROKE);
        mTopGraditPaint.setColor(Color.WHITE);
        mTopGraditPaint.setStrokeCap(Paint.Cap.ROUND);

//        mTopGraditPaint.setShader(new SweepGradient(cx,cy,new int[]{},))

        mTopRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTopRingPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        mTopRingPaint.setStyle(Paint.Style.STROKE);
        mTopRingPaint.setColor(Color.WHITE);
        mTopRingPaint.setStrokeCap(Paint.Cap.ROUND);


        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.WHITE);

        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
        mPaddingRight = getPaddingRight();
        mPaddingBottom = getPaddingBottom();
        mLampCenter = 33f / 100;


        progressBgPaint = new Paint();
        progressBgPaint.setStyle(Paint.Style.STROKE);
        progressBgPaint.setColor(Color.WHITE);
        progressBgPaint.setAntiAlias(true);
        progressBgPaint.setStrokeCap(Paint.Cap.ROUND);
        progressBgPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 30));//30

        draggerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        draggerPaint.setStyle(Paint.Style.FILL);
        draggerPaint.setColor(Color.WHITE);
        this.setOnTouchListener(new WheelTouchListener());
        mTestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTestPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        mTestPaint.setStyle(Paint.Style.STROKE);
        mTestPaint.setColor(Color.GRAY);
        mPointIcon = getResources().getDrawable(R.drawable.control_btn_pointer);

        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFlagsDrawFilter);
//        drawBorder(canvas);
        //颜色值改变
        onInitCircle();
        drawTop(canvas);
        Logger.i(TAG,"time 2 a "+System.currentTimeMillis());
    }

    private void drawBorder(Canvas canvas) {
        canvas.drawRect(new RectF(0, 0, mWidth, mHeight), borderPaint);
    }

    private Matrix mMatrix = new Matrix();
//    private Paint mBasePaint = new Paint();

    private void drawTop(Canvas canvas) {
        Logger.i(TAG,"time 1"+System.currentTimeMillis());
        //渐进色开始
        mLinePaint.setColor(Color.WHITE);

////        //渐进色
        canvas.save();
        canvas.rotate(90,rectF.centerX(),rectF.centerY());
        canvas.drawArc(rectF, 45, MAX_SWEEP_ANGLE, false, progressBgPaint);
        canvas.restore();

        //渐进色白线包裹
        int out = ScreenUtil.dip2px(mContext, 20);
        RectF rectRingF = new RectF(rectF.left - out,
                rectF.top - out,
                rectF.right + out,
                rectF.bottom + out);
        canvas.drawArc(rectRingF, START_ANGLE, MAX_SWEEP_ANGLE, false, mTopRingPaint);
        RectF rectRingF1 = new RectF(rectF.left + out,
                rectF.top + out,
                rectF.right - out,
                rectF.bottom - out);
        canvas.drawArc(rectRingF1, START_ANGLE, MAX_SWEEP_ANGLE, false, mTopRingPaint);

        canvas.rotate(-45, rectF.centerX(), rectRingF.centerY());
        RectF leftSmallCircle = new RectF();
        leftSmallCircle.left = rectRingF.left;
        leftSmallCircle.right = rectRingF1.left;
        leftSmallCircle.top = rectF.centerY() - (leftSmallCircle.right - leftSmallCircle.left) / 2;
        leftSmallCircle.bottom = rectF.centerY() + (leftSmallCircle.right - leftSmallCircle.left) / 2;
        canvas.drawArc(leftSmallCircle, 0, 180, false, mTopRingPaint);
        canvas.rotate(45, rectF.centerX(), rectRingF.centerY());

        RectF rightSmallCircle = new RectF();
        rightSmallCircle.left = rectRingF1.right;
        rightSmallCircle.right = rectRingF.right;
        rightSmallCircle.top = rectF.centerY() - (rightSmallCircle.right - rightSmallCircle.left) / 2;
        rightSmallCircle.bottom = rectF.centerY() + (rightSmallCircle.right - rightSmallCircle.left) / 2;
        canvas.rotate(45, rectF.centerX(), rectRingF.centerY());
        canvas.drawArc(rightSmallCircle, 0, 180, false, mTopRingPaint);
        canvas.rotate(-45, rectF.centerX(), rectRingF.centerY());

        //指针开始
        Bitmap bitmap = Bitmap.createBitmap((int) rectRingF1.width(), (int) rectRingF1.height(), Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(bitmap);
        rotateR = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        targetCanvas.drawRect(rotateR, mTestPaint);
        int width = mPointIcon.getIntrinsicWidth();
        int height = mPointIcon.getIntrinsicHeight();
        canvas.save();
        canvas.translate(rectF.left + out, rectF.top + out);
        float cx = rotateR.centerX() - rotateR.width() / 2 * (float) Math.cos(Math.toRadians(45)) + (out - progressBgPaint.getStrokeWidth() / 2);
        float cy = rotateR.centerY() + rotateR.height() / 2 * (float) Math.sin(Math.toRadians(45)) - (out - progressBgPaint.getStrokeWidth() / 2);
        mPointIcon.setBounds((int) cx - width / 2, (int) cy - height / 2, (int) cx + width / 2, (int) cy + height / 2);
        mPointIcon.draw(targetCanvas);
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setStrokeWidth(3);
//        targetCanvas.drawPoint(cx,cy,mLinePaint);
        mTestPaint.setDither(true);
        if (shouldSkip){
            mMatrix.setRotate(base_start,rotateR.centerX(),rotateR.centerY());
            shouldSkip = false;
        }
        canvas.drawBitmap(bitmap, mMatrix, mTestPaint);



        canvas.restore();

        //灯泡下面第一个大方块
        RectF rectB = new RectF(mLampCenterX - ScreenUtil.getScreenWidth() / 8,
                mLampCircleCenterY + mLampCenterCircleRadius - ScreenUtil.dip2px(mContext, 20)
                , mLampCenterX + ScreenUtil.getScreenWidth() / 8
                , mLampCircleCenterY + mLampCenterCircleRadius + ScreenUtil.dip2px(mContext, 5));
        mLinePaint.setColor(Color.WHITE);
//        mLinePaint.setStyle(Paint.Style.FILL);
//        canvas.drawRoundRect(rectB, 20, 20, mLinePaint);
//        mLinePaint.setColor(Color.parseColor("#4d5d7b"));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        canvas.drawRoundRect(rectB, 20, 20, mLinePaint);

        //灯泡下面小方块
        RectF rectFD = new RectF(rectB.left + rectB.width() * 0.3f
                , rectB.bottom + rectB.height() * 2
                , rectB.right - rectB.width() * 0.3f
                , rectB.bottom + rectB.height() * 5.2f / 2);
        mLinePaint.setColor(Color.WHITE);
        float[] radii = {0f, 0f, 0f, 0f, 12f, 12f, 12f, 12f};
        Path path = new Path();
        path.addRoundRect(rectFD, radii, Path.Direction.CW);
        mLinePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mLinePaint);
        // 4个小块
        for (int i = 0; i < 4; i++) {
            mLinePaint.setColor(Color.WHITE);
            RectF rectFC = new RectF(rectB.left + rectB.width() / 8
                    , rectB.bottom + rectB.height() / 2 * i
                    , rectB.right - rectB.width() / 8
                    , rectB.bottom + rectB.height() / 2 * (i + 1));
            canvas.drawRoundRect(rectFC, 10, 10, mLinePaint);
            mLinePaint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rectFC, 10, 10, mLinePaint);
        }

    }






    /**
     * 设置色盘颜色，更新UI
     *
     * @param color
     */
    public void setColorful(int color) {
        float angle = getColorAngle(color);
        shouldSkip = true;
        sweepAngle = base_start = angle;
        Log.i(TAG, "setColor() color = " + color + "    angle = " + angle);
        invalidate();
    }

    // 将颜色转换为角度
    private float getColorAngle(int color) {
        // 第一步，转为HSV模型
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        // 获取角度H（0-360）
        float angle = hsv[0];
        /*
         * 修改最左边的颜色值与最右边颜色值一致，导致底部颜色（红色）没有一个固定角度的问题
		 * 现在处理为损失（0-(180/140f * 5)）这个角度的颜色，左半部分不变压缩为135°，右半部分压缩为140°（色盘上135°-140°颜色无法获取到，对应HSV的（0-6.428））
		 */
        if (angle <= 5) {
            angle = 360;
        }
        // 以180°为中心点，乘以两边压缩比，色盘角度45-335的角度
        if (angle >= 180) {
            angle = (angle - 180) * (135 / 180f) + 180;
        } else {
            angle = (angle - 180) * (140 / 180f) + 180;
        }
        // 转成0-270°
        angle -= 45;
        // 反转，与显示的图片对应
        angle = 270 - angle;
        return angle;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(),
                widthMeasureSpec);
//        Logger.i(TAG, "onMeasure : " + width + " == " + height);
        mWidth = width;
        mHeight = height;
        mlampOutWith = mWidth * 0.8f;
        mLampCenterX = (float) Math.ceil((mWidth - (mPaddingLeft + mPaddingRight)) / 2);
        mLampCenterY = (float) Math.ceil(mLampCenter * (mHeight - (mPaddingLeft + mPaddingBottom)));
        mLampCenterRadius = (float) Math.ceil((mWidth - (mPaddingLeft + mPaddingRight) - ScreenUtil.getScreenWidth() * 2 / 5) / 2);

//        mLampCircleCenterX = mLampCenterX - 30;
        mLampCircleCenterY = mLampCenterY - ScreenUtil.dip2px(mContext, 30);
        mLampCenterCircleRadius = mLampCenterRadius + 60;
        mCurrentColorState = STATE_COLOR_WHITE;

//////////////////////////////////////////////////////////////////////


        rectF = new RectF(mLampCenterX - mLampCenterRadius,
                mLampCenterY - mLampCenterRadius,
                mLampCenterX + mLampCenterRadius,
                mLampCenterY + mLampCenterRadius);



        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }


    private class WheelTouchListener implements OnTouchListener {
        private double startAngle;
        private boolean isOut = false;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    boolean touchWheel = isTouchWheel((int) event.getX(), (int) event.getY());
                    if (!touchWheel) {
                        isOut = true;
                        return false;
                    }
                    startAngle = getAngle(event.getX(), event.getY());
                    if (rotateListener != null) {
                        rotateListener.onRotateChangeStart(sweepAngle, sweepAngle / MAX_SWEEP_ANGLE);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isOut) {
                        return false;
                    }
                    double currentAngle = getAngle(event.getX(), event.getY());
                    rotateWheel((float) (startAngle - currentAngle));
                    startAngle = currentAngle;
                    break;
                case MotionEvent.ACTION_UP:
                    if (rotateListener != null) {
                        rotateListener.onRotateChangeEnd(sweepAngle, sweepAngle / MAX_SWEEP_ANGLE);
                    }
                    if (warmChangeListener != null && isColdWarmLamp){
                        warmChangeListener.onWhiteChange(getWhiteWarmK());
                    }
                    onColorChangeEnd(true);
                    isOut = false;
                    break;
            }
            requestDisallowInterceptTouchEvent();
            return true;
        }
    }

    private void rotateWheel(float degrees) {
        if (degrees > MAX_SWEEP_ANGLE) {
            degrees = degrees - 360;
        } else if (degrees < -MAX_SWEEP_ANGLE) {
            degrees = 360 + degrees;
        }
        float temp = degrees;
        if (sweepAngle < 0 || sweepAngle > MAX_SWEEP_ANGLE) {
            return;
        }
        if (sweepAngle + degrees > MAX_SWEEP_ANGLE) {
            temp = MAX_SWEEP_ANGLE - sweepAngle;
        }
        if (sweepAngle + degrees < 0) {
            temp = -sweepAngle;
        }
        mMatrix.postRotate(temp, rotateR.centerX(), rotateR.centerY());
        sweepAngle += temp;
        sweepAngle %= 360;
        if (sweepAngle < 0) {
            sweepAngle += 360;
        }
        invalidate();
        if (rotateListener != null) {
            rotateListener.onRoateChange(sweepAngle, true);
        }
        Logger.i(TAG, "isColdWarmLamp" + isColdWarmLamp);
        if (warmChangeListener != null && isColdWarmLamp){
            warmChangeListener.onWhiteChanging(getWhiteWarmK());
        }
        onColorChangeEnd(false);
    }

    private boolean isTouchWheel(int x, int y) {
        return rectF != null && rectF.contains(x, y);
    }

    private double getTouchRadius(int x, int y) {
        int cx = (int) (x - rectF.width() / 2);
        int cy = (int) (y - rectF.height() / 2);
        return Math.hypot(cx, cy);
    }

    private void requestDisallowInterceptTouchEvent() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    /**
     * 触屏事件角度
     */
    private double getAngle(double x, double y) {
        wheelWidth = rectF.width();
        wheelHeight = rectF.height();
        x = x - rectF.centerX();
        y = rectF.centerY() - y;
        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    /**
     * 根据点获取象限
     *
     * @return 1, 2, 3, 4
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }


    public int getColor() {
        float angle = sweepAngle ;    // 135 ~ (360+45),  0-270
        // 以180°为中心点，乘以两边压缩比，色盘角度45-335的角度
        if (angle >= 135) {
            angle = (angle - 135)*(180/140f) + 180 ;
        } else {
            angle = angle * (180 / 135f) ;
        }
        angle = 360 - angle;

        //angle = (angle + 270)%360;
        float[] hsv = new float[]{angle, 1, 1};
        return Color.HSVToColor(hsv);
    }


    // 是否有冷暖灯
    private boolean hasWarmLamp = true;

    public boolean isHasWarmLamp() {
        return hasWarmLamp;
    }


    /**
     * 设置是否有冷暖灯
     *
     * @param hasWarmLamp     true 有冷暖灯			false 无
     * @param triggerListener 是否触发onColorChangeEnd()监听
     */
    public void setHasWarmLamp(boolean hasWarmLamp, boolean triggerListener) {
        this.hasWarmLamp = hasWarmLamp;
        if (isColdWarmLamp) {
            onInitCircle();
            invalidate();
            if (triggerListener) {
                onColorChangeEnd(true);
            }
        }
    }


    /**
     * 设置是彩灯还是白灯
     *
     * @param flag            true 白灯		false 彩灯
     * @param triggerListener 是否触发onColorChangeEnd()监听
     */
    public void setIsColdWarmLamp(boolean flag, boolean triggerListener) {
        if (flag != isColdWarmLamp) {
            isColdWarmLamp = flag;
            onInitCircle();
            invalidate();
            if (triggerListener) {
                onColorChangeEnd(true);
            }
        }
    }


    // 初始化渲染
    protected void onInitCircle() {
        if (!isColdWarmLamp) {
            // 颜色值的角度
            int[] colorAngle = new int[]{360, 330, 300, 270, 240, 210, 180, 150, 120, 90, 60, 30,0};
//            printPlateAngleAndOffsetPlus();
            // 每个角度对应的位置，生成方式见最底部
            float[] positions = new float[]{0.125f, 0.1875f, 0.25f, 0.3125f, 0.375f, 0.4375f, 0.5f, 0.5648148f, 0.6296296f, 0.6944444f, 0.7592593f, 0.8240741f, 0.8888889f};
            int[] colors = new int[colorAngle.length];
            float hsv[] = new float[]{0f, 1f, 1f};
            for (int i = 0; i < colors.length; i++) {
                // 根据HSV色盘，旋转的角度取色
                hsv[0] = colorAngle[i];
                colors[i] = Color.HSVToColor(hsv);
            }
            SweepGradient sweepGradient = new SweepGradient(mLampCenterX, mLampCenterY, colors, positions);
            progressBgPaint.setShader(sweepGradient);
        } else {
            // 当有冷暖灯时，绘制从白到黄渐变，否者处理为灰色的不可用色圈
            if (hasWarmLamp) {
                int wramWhite1 = Color.HSVToColor(new float[]{49, 1, 30});
                int wramWhite2 = Color.HSVToColor(new float[]{49, 1, 60});
                int wramWhite3 = Color.HSVToColor(new float[]{49, 1, 90});
//                int wramWhite4 = Color.HSVToColor(new float[]{49, 1, 120});
//                int wramWhite5 = Color.HSVToColor(new float[]{49, 1, 150});
                Logger.i(TAG, "wramWhite1 >> " + wramWhite1 + "wramWhite2" + wramWhite2 + "wramWhite3" + wramWhite3);
                int[] colors = new int[]{Color.WHITE, wramWhite1, wramWhite2, wramWhite3, wramWhite3};
                float[] positions = new float[]{0.0f, 0.25f, 0.375f, 0.75f, 1f};
                SweepGradient sweepGradient = new SweepGradient(mLampCenterX, mLampCenterY, colors, positions);
                progressBgPaint.setShader(sweepGradient);
            } else {
                progressBgPaint.setShader(null);
                progressBgPaint.setColor(0xFFFFFBE5);
            }
        }
    }

    public int getWhiteWarmK(){
        return  coldStart - (int)((coldStart-coldEnd) * (sweepAngle /MAX_SWEEP_ANGLE));
    }


    public void setWhiteWarm(int k) {
        Logger.i(TAG,"time 1 a "+System.currentTimeMillis());
        Logger.i(TAG, "hasWarmLamp" + hasWarmLamp + "isColdWarmLamp" + isColdWarmLamp);
        shouldSkip = true;
        if (hasWarmLamp && isColdWarmLamp) {
            if (k > coldStart || k < coldEnd) {
                return;
            }
            int delta = k - coldEnd;
            float angle = MAX_SWEEP_ANGLE - (float) delta / (coldStart - coldEnd) * MAX_SWEEP_ANGLE;
            sweepAngle = base_start = angle;
        }


    }


    private OnColorChangeListener mColorListener;

    /**
     * 设置进度变化监听
     *
     * @param listener
     */
    public void setOnColorChangeListener(OnColorChangeListener listener) {
        this.mColorListener = listener;
    }

    private void onColorChangeEnd(boolean isEnd) {
        if (mColorListener != null) {
            int pixel = getColor();
            //获取颜色
            int redValue = Color.red(pixel);
            int blueValue = Color.blue(pixel);
            int greenValue = Color.green(pixel);
            if (isEnd) {
                mColorListener.onColorChangeEnd(redValue, greenValue, blueValue);
            } else {
                mColorListener.onColorChange(redValue, greenValue, blueValue);
            }
        }
    }

    /**
     * 进度变化监听
     */
    public interface OnColorChangeListener {
        /**
         * 颜色改变
         *
         * @param red
         * @param green
         * @param blue
         */
        void onColorChange(int red, int green, int blue);

        /**
         * 颜色改变结束回调
         *
         * @param red
         * @param green
         * @param blue
         */
        void onColorChangeEnd(int red, int green, int blue);
    }


    public interface RotateChangeListener {
        /**
         * 顺时针，0 ---> 360度，最顶点为0
         *
         * @param degress
         * @param fromUser 是否通过旋转的方式使角度改变
         */
        void onRoateChange(float degress, boolean fromUser);

        void onRotateChangeStart(float degree,float percent);

        void onRotateChangeEnd(float degree,float percent);
    }

    private RotateChangeListener rotateListener;

    public void setRotateChangeListener(RotateChangeListener listener) {
        this.rotateListener = listener;
    }

    private static void printPlateAngleAndOffsetPlus() {
        int partNum = 12;    // 生成12 + 1个代表点
        int compressPoint = 180;    // 以中心点作为压缩中心
        // 生成角度原（[360, 330, 300, 270, 240, 210, 180, 150, 120, 90, 60, 30, 0]）
        int[] sourceAngle = new int[partNum];
        for (int i = 0; i < sourceAngle.length; i++) {
            sourceAngle[i] = 360 - i * (360 / (partNum - 1));
        }

        float percentLeft = 135 / 180f;
        float percentRight = 140 / 180f;
        int[] targetAngle = new int[partNum];
        for (int i = 0; i < sourceAngle.length; i++) {
            if (sourceAngle[i] >= 180) {
                targetAngle[i] = (int) ((sourceAngle[i] - compressPoint) * percentLeft + compressPoint);
            } else {
                targetAngle[i] = (int) ((sourceAngle[i] - compressPoint) * percentRight + compressPoint);
            }
        }
        float[] percentAngle = new float[partNum];
        for (int i = 0; i < percentAngle.length; i++) {
            if (sourceAngle[i] >= 180) {
                percentAngle[i] = 1 - (((sourceAngle[i] - compressPoint) * percentLeft + compressPoint) / 360);
            } else {
                percentAngle[i] = 1 - (((sourceAngle[i] - compressPoint) * percentRight + compressPoint) / 360);
            }
        }
    }

    public static String toStringF(float[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "{}";

        StringBuilder b = new StringBuilder();
        b.append('{');
        for (int i = 0; ; i++) {
            b.append(a[i]).append("f");
            if (i == iMax)
                return b.append('}').toString();
            b.append(", ");
        }
    }

    public interface WarmChangeListener {
        void onWhiteChange(int warm);
        void onWhiteChanging(int warm);
    }
    private WarmChangeListener warmChangeListener;
    public void setWarmChangeListener(WarmChangeListener listener){
        this.warmChangeListener = listener;
    }

}
