package com.linkus.superlamp.behaviours;


import android.text.TextUtils;


import com.linkus.superlamp.Constant;
import com.linkus.superlamp.beans.StartBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.providers.BaseProvider;
import com.linkus.superlamp.providers.SplashProvider;


import okhttp3.Call;

/**
 * Created by jizi.chen on 2017/8/28.
 */

public class SplashBehavior extends BaseBehavior {

    private OnStartResponse startListener;

    public SplashBehavior() {
        super(SplashProvider.class);
    }

    public void setStartListener(OnStartResponse listener) {
        this.startListener = listener;
    }

    public interface OnStartResponse {
        void startResponseSuccess(StartBean bean);
        void startResponseFailed(Exception e);
        void startForNothing();
    }

    public void requestStartPage() {
        Clause startClause = new Clause().method(Clause.Method.GET);
        if (startListener != null){
           startListener.startForNothing();
        }
        getProvider()
                .setUrl(Constant.URL_Start_API)
                .addClause(startClause)
                .execute(new BaseProvider.ResultListener<String>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (startListener != null){
                            startListener.startResponseFailed(e);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, "Thread thread " + Thread.currentThread().getName());
                    }
                });


    }

}
