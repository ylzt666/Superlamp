package com.linkus.superlamp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.linkus.superlamp.App;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：屏幕相关工具类
 * </p>
 *
 * @author cjz
 * @version 1.0
 * @createdate 2015-12-17 11:17
 */
public class ScreenUtil {

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得屏幕的宽
     */
    public static int getScreenWidth(Context context) {
        WindowManager window = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**
     * 获得屏幕的宽
     */
    public static int getScreenWidth() {
        DisplayMetrics outMetrics = getDisplayMetrics();
        return outMetrics.widthPixels;
    }

    @NonNull
    private static DisplayMetrics getDisplayMetrics() {
        WindowManager window = (WindowManager) App.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获得屏幕的高
     */
    public static int getScreenHeight(Context context) {
        WindowManager window = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * 获得屏幕的高
     */
    public static int getScreenHeight() {
        DisplayMetrics outMetrics = getDisplayMetrics();
        return outMetrics.heightPixels;
    }

}