package com.linkus.superlamp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class SharePrefrenceUtil {
    public static String TAG = "SharePrefrenceUtil";
    public static void put(Context context, String key, String value) {
        Log.i(TAG, "put value key " + key + " value is " + value);
        assert key == null;
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String get(Context context, String key) {
        return get(context, key, "");
    }

    public static String get(Context context, String key, String defVal) {
        Log.i(TAG, "get value key " + key);
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getString(key, defVal);
    }

    public static boolean removeKey(Context context, String key) {
        Log.i(TAG, "remove key " + key);
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.edit().remove(key).commit();
    }
}
