package com.linkus.superlamp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.linkus.superlamp.R;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class ProgressArs extends View {
    private String TAG = getClass().getSimpleName();
    private Drawable mThumb;
    private Drawable mHighIcon;
    private Drawable mLowIcon;
    private int mWidth;
    private int mHeight;
    private Paint mBorderPaint;

    private Context mContext;

    private int mCenterX;

    private Paint mProgressPaint;

    private int mStartX;

    private int mArcLength;

    private int mCircleRadius;

    private float StartAngle = -120;
    private float SweepAngle = 60;
    private RectF circleRectF;


    private int max = 100;

    private int currentProgress = 0;

    private float currentDegree = StartAngle;
    private Paint mTextPaint;
    private Paint mBackGroundPaint;

    public ProgressArs(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ProgressArs(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ProgressArs(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        // 加载拖动图标
        mThumb = getResources().getDrawable(R.drawable.img_lamp_brightness);// 圆点图片
        int thumbHalfheight = mThumb.getIntrinsicHeight() / 2;
        int thumbHalfWidth = mThumb.getIntrinsicWidth() / 2;
        mThumb.setBounds(-thumbHalfWidth, -thumbHalfheight, thumbHalfWidth, thumbHalfheight);

        mHighIcon = getResources().getDrawable(R.drawable.img_lamp_brightness_high);
        thumbHalfheight = mHighIcon.getIntrinsicHeight() / 2;
//        thumbHalfWidth = mHighIcon.getIntrinsicWidth() / 2;
        mHighIcon.setBounds(0+20, -thumbHalfheight, mHighIcon.getIntrinsicWidth()+20, thumbHalfheight);
        mLowIcon = getResources().getDrawable(R.drawable.img_lamp_brightness_low);
        thumbHalfheight = mLowIcon.getIntrinsicHeight() / 2;
//        thumbHalfWidth = mLowIcon.getIntrinsicWidth() / 2;
        mLowIcon.setBounds(-2 * mLowIcon.getIntrinsicWidth(), -thumbHalfheight, -mLowIcon.getIntrinsicWidth(), thumbHalfheight);


        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBorderPaint.setColor(Color.parseColor("#FF0000"));
        mBorderPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 1));
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(getResources().getColor(R.color.blue));
        mProgressPaint.setStrokeWidth(ScreenUtil.dip2px(mContext, 8));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getResources().getColor(R.color.blue));
        mTextPaint.setTextSize(24);

        mBackGroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackGroundPaint.setColor(getResources().getColor(R.color.white));
        mBackGroundPaint.setStyle(Paint.Style.STROKE);

//        mHeight = ScreenUtil.getScreenHeight() / 6;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        Logger.i(TAG, "onSizeChanged" + mWidth + "aaaa" + mHeight);
        mCenterX = mWidth / 2;
        int screenWidth = ScreenUtil.getScreenWidth(mContext);
        mStartX = mCenterX - screenWidth * 4 / 5 / 2;
        mArcLength = screenWidth * 3 / 5;
        mCircleRadius = mArcLength;

        int top = mHeight / 3;
        int bottom = top + mCircleRadius * 2;
        int left = mCenterX - mCircleRadius;
        int right = mCenterX + mCircleRadius;
        circleRectF = new RectF(left, top, right, bottom);


        minValidateTouchArcRadius = (int) (mCircleRadius - mThumb.getIntrinsicWidth() * 0.8f);
        maxValidateTouchArcRadius = (int) (mCircleRadius + mThumb.getIntrinsicWidth() * 0.8f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.i(TAG, "onDraw" + getWidth() + getHeight());
//        drawBorder(canvas);
//        drawPath(canvas);
        drawProgressArc(canvas);
        drawBlup(canvas);
        drawProgressThumb(canvas);
        drawTextPercent(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        PointF point1 = new PointF(0, 0);
        PointF point2 = new PointF(getWidth() * 1 / 8, getHeight() * 3 / 5);
        PointF point3 = new PointF(getWidth() * 2 / 4, getHeight());
        PointF point4 = new PointF(getWidth() * 3 / 4, getHeight() * 4 / 5);
        PointF point5 = new PointF(getWidth() * 4 / 4, getHeight() * 1 / 5);
        path.cubicTo(point1.x, point1.y, point2.x, point2.y, point3.x,point3.y);
        path.moveTo(point3.x, point3.y);
        path.cubicTo(point3.x, point3.y, point4.x, point4.y, point5.x, point5.y);
//        path.quadTo();
        canvas.drawPath(path, mBackGroundPaint);
    }


    private boolean isDrawProgress = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchArc((int) x, (int) y)) {
                    isDrawProgress = true;
                    Logger.i(TAG, "isTouchArc true");
                    updateProgress(x, y);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDrawProgress) {
                    updateProgress(x, y);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isDrawProgress = false;
                if (changeListener != null) {
                    changeListener.onBrightChangeEnd(currentProgress* 100 /max,(int) (currentProgress / max * 255f), false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void updateProgress(float x, float y) {
        int cx = (int) (x - circleRectF.centerX());
        int cy = (int) (y - circleRectF.centerY());
//        Logger.i(TAG,"角度"+  Math.atan2(cy, cx));
        double maxSweep = Math.toRadians(SweepAngle) / Math.PI;
//        Logger.i(TAG, "Math.atan2(cy, cx) " + maxSweep);
        double sweep = Math.atan2(cy, cx) / Math.PI + Math.toRadians(StartAngle + 180) / Math.PI;
//        Logger.i(TAG, "sweep sweep " + sweep);
        currentProgress = (int) ((maxSweep + sweep) / maxSweep * max);
        if (currentProgress < 0) {
            currentProgress = 0;
        }
        if (currentProgress > max) {
            currentProgress = max;
        }
        updateProgress();
//        currentProgress = angle * SweepAngle / 360
        if (changeListener != null) {
            changeListener.onBrightChange(currentProgress* 100 /max ,(int) (currentProgress * 255f / max), false);
        }
    }

    private void updateProgress() {
//        Logger.i(TAG, "currentProgress is " + currentProgress);
        float delta = currentProgress * SweepAngle / max;
        currentDegree = StartAngle + delta;
        if (currentDegree < StartAngle) {
            currentDegree = StartAngle;
        }
        if (currentDegree > StartAngle + SweepAngle) {
            currentDegree = StartAngle + SweepAngle;
        }
//        Logger.i(TAG, "currentDegree" + currentDegree);
        invalidate();
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        progress = Math.min(progress, max);
        currentProgress = Math.max(0, progress);
        updateProgress();
    }


    public void setBright(int bright) {
        if (bright < 0) {
            bright = 0;
        }
        if (bright > 255) {
            bright = 255;
        }
        currentProgress = (int) (bright / 255.0f);
        updateProgress();
    }


    public int getProgress() {
        return currentProgress;
    }

    public int getMax() {
        return max;
    }


    private OnBrightChangeListener changeListener;

    public void setOnBrightChangeListener(OnBrightChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface OnBrightChangeListener {
        void onBrightChange(int percent ,int bright, boolean fromUser);
        void onBrightChangeEnd(int percent ,int bright, boolean fromUser);
    }


    private void drawTextPercent(Canvas canvas) {
        float top = circleRectF.top + ScreenUtil.dip2px(mContext, 40);
        String text = String.format("亮度%d", currentProgress);
        float width = mTextPaint.measureText(text + "%");
        canvas.drawText(text + "%", mCenterX - width / 2, top, mTextPaint);
    }


    private void drawProgressThumb(Canvas canvas) {
        mProgressPaint.setColor(getResources().getColor(R.color.yellow_choose));
        canvas.drawArc(circleRectF, StartAngle, currentDegree - StartAngle, false, mProgressPaint);
        PointF pointF = ChartUtil.calcArcEndPointXY(circleRectF.centerX(), circleRectF.centerY(), mCircleRadius, currentDegree);
        canvas.save();
        canvas.translate(pointF.x, pointF.y);
        mThumb.draw(canvas);
        canvas.restore();
    }

    private void drawBlup(Canvas canvas) {
        PointF pointF = ChartUtil.calcArcEndPointXY(circleRectF.centerX(), circleRectF.centerY(), mCircleRadius, StartAngle);
        Logger.i(TAG, "pointF " + pointF.x);
//        mBorderPaint.setStrokeWidth(8);
//        canvas.drawPoint(pointF.x,pointF.y,mBorderPaint);
        canvas.save();
        canvas.translate(pointF.x, pointF.y);
        mLowIcon.draw(canvas);
        canvas.restore();
        pointF = ChartUtil.calcArcEndPointXY(circleRectF.centerX(), circleRectF.centerY(), mCircleRadius, StartAngle + SweepAngle);
        canvas.save();
        canvas.translate(pointF.x, pointF.y);
        mHighIcon.draw(canvas);
        canvas.restore();
    }

    private void drawProgressArc(Canvas canvas) {
        mProgressPaint.setColor(getResources().getColor(R.color.blue));
        canvas.drawArc(circleRectF, StartAngle, SweepAngle, false, mProgressPaint);
    }

    private void drawBorder(Canvas canvas) {
        canvas.drawRect(new RectF(0, 0, mWidth, mHeight), mBorderPaint);
    }


    private int minValidateTouchArcRadius; // 最小有效点击半径
    private int maxValidateTouchArcRadius; // 最大有效点击半径

    // 判断是否按在圆边上
    private boolean isTouchArc(int x, int y) {
        double d = getTouchRadius(x, y);
        Log.i("myTag", "isTouchArc() d = " + d + "     minValidateTouchArcRadius = " + minValidateTouchArcRadius + "   maxValidateTouchArcRadius = " + maxValidateTouchArcRadius);
        if (d >= minValidateTouchArcRadius && d <= maxValidateTouchArcRadius) {
            return true;
        }
        return false;
    }

    // 计算某点到圆点的距离
    private double getTouchRadius(int x, int y) {
        int cx = x - getWidth() / 2;
        //int cy = y - getHeight() / 2;
        int cy = y - (int) circleRectF.centerY();
        cy = Math.abs(cy);
        Log.i("myTag", "getTouchRadius() cy = " + cy);
        return Math.hypot(cx, cy);
    }


    public static class ChartUtil {

        /**
         * 依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
         *
         * @param cirX
         * @param cirY
         * @param radius
         * @param cirAngle
         * @return
         */
        public static PointF calcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle) {
            float posX = 0.0f;
            float posY = 0.0f;
            //将角度转换为弧度
            float arcAngle = (float) (Math.PI * cirAngle / 180.0);
            if (cirAngle < 90) {
                posX = cirX + (float) (Math.cos(arcAngle)) * radius;
                posY = cirY + (float) (Math.sin(arcAngle)) * radius;
            } else if (cirAngle == 90) {
                posX = cirX;
                posY = cirY + radius;
            } else if (cirAngle > 90 && cirAngle < 180) {
                arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
                posX = cirX - (float) (Math.cos(arcAngle)) * radius;
                posY = cirY + (float) (Math.sin(arcAngle)) * radius;
            } else if (cirAngle == 180) {
                posX = cirX - radius;
                posY = cirY;
            } else if (cirAngle > 180 && cirAngle < 270) {
                arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
                posX = cirX - (float) (Math.cos(arcAngle)) * radius;
                posY = cirY - (float) (Math.sin(arcAngle)) * radius;
            } else if (cirAngle == 270) {
                posX = cirX;
                posY = cirY - radius;
            } else {
                arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
                posX = cirX + (float) (Math.cos(arcAngle)) * radius;
                posY = cirY - (float) (Math.sin(arcAngle)) * radius;
            }
            return new PointF(posX, posY);
        }

        public static PointF calcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle, float orginAngle) {
            cirAngle = (orginAngle + cirAngle) % 360;
            return calcArcEndPointXY(cirX, cirY, radius, cirAngle);
        }
    }
}
