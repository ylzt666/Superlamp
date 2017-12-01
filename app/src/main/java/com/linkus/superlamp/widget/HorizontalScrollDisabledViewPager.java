package com.linkus.superlamp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class HorizontalScrollDisabledViewPager extends ViewPager{
    private boolean noScroll = true;
    public HorizontalScrollDisabledViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollDisabledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (noScroll){
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (noScroll){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
