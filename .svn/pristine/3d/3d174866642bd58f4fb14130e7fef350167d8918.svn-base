package com.linkus.superlamp;

import android.app.Application;

import com.linkus.superlamp.behaviours.BaseBehavior;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.providers.BaseProvider;


/**
 * Created by jizi.chen on 2017/8/28.
 */

public class App extends Application {

    private static App application;
    private static final String TAG = "App";
    private static boolean isX5Core;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        BaseProvider.init();
        BaseBehavior.init();
//        initX5();
    }

//    private void initX5() {
//        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//                Logger.i(TAG, "initX5Environment onCoreInitFinished");
//            }
//
//            @Override
//            public void onViewInitFinished(boolean isX5) {
//                isX5Core = isX5;
//                Logger.i(TAG, "initX5Environment onViewInitFinished >> " + isX5);
//            }
//        });
//        QbSdk.setDownloadWithoutWifi(true);
//        QbSdk.disAllowThirdAppDownload();
//    }

    /**
     * 获取APP单例
     */
    public static App getInstance() {
        if (application == null) {
            Logger.e(TAG, "getInstance and app is null");
        }
        return application;
    }


    public static boolean isX5Core() {
        return isX5Core;
    }


}
