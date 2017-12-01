package com.linkus.superlamp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.linkus.superlamp.R;

/**
 * Created by Administrator on 2016/7/13/013.
 */

public class ToastUtils {
    ToastUtils INSTANCE;// 实现单例
    private static Toast mToast;
    private static TextView mTvToast;
    public static void showToast(Context ctx, String content) {
        cancelToast();
        if (mToast == null) {
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.BOTTOM, 0, 30);
            mToast.setDuration(Toast.LENGTH_SHORT);
            View _root = LayoutInflater.from(ctx).inflate(R.layout.toast_custom_common, null);//自定义样式，自定义布局文件
            mTvToast = (TextView) _root.findViewById(R.id.tvCustomToast);
            mToast.setView(_root);
        }
        mTvToast.setText(content);
        mToast.show();//展示toast
    }
    public void showToast(Context ctx, int stringId) {
        showToast(ctx, ctx.getString(stringId));
    }
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
            mTvToast = null;
        }
    }

}