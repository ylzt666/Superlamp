package com.linkus.superlamp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;


import com.linkus.superlamp.Constant;
import com.linkus.superlamp.logger.Logger;

import org.greenrobot.eventbus.EventBus;

//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

/**
 * Created by Administrator on 2017-08-30.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {
    private String TAG = getClass().getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        NetTools.NetState netWorkState = NetTools.getNetWorkState(context);
        boolean connected = NetTools.isConnected();
        Logger.e(TAG, "getNetWorkState >> " + netWorkState + ">> connected >> " + connected);
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetTools.NetState networkState = NetTools.getNetWorkState(context);
            Logger.e(TAG, networkState + "");
            if (networkState == NetTools.NetState.WIFI) {
                EventBus.getDefault().post(new EventCenter(new Object[]{NetTools.NetState.WIFI,connected}, Constant.MESSAGE_NETWORK));
            } else if (networkState == networkState.MOBILE) {
                EventBus.getDefault().post(new EventCenter(new Object[]{NetTools.NetState.MOBILE,connected},Constant.MESSAGE_NETWORK));
            }
        }
    }
}
