package com.linkus.superlamp.providers;



import com.linkus.superlamp.behaviours.BaseBehavior;
import com.linkus.superlamp.http.okhttp.OkHttpUtils;
import com.linkus.superlamp.http.okhttp.callback.Callback;
import com.linkus.superlamp.http.okhttp.request.RequestCall;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.XulUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by jizi.chen on 2017/8/28.
 */

public abstract class BaseProvider {
    public  String TAG = getClass().getSimpleName();
    protected HashMap<String,String> paraMap = new HashMap<String,String>();
    protected String url;


    public BaseBehavior.Clause clause ;

    public static void init(){
        ProviderFactory.register(SplashProvider.class
                                    );
    }

    public BaseProvider() {

    }

    public static OkHttpUtils getHttpClient() {
        return OkHttpUtils.getInstance();
    }

    public BaseProvider addClause(BaseBehavior.Clause clause) {
        this.clause = clause;
        return this;
    }

    public void excute(){
        RequestCall call = buildRequest();
        if (call != null){
            call.execute(new Callback<String>() {
                @Override
                public String parseNetworkResponse(Response response, int id) throws Exception {
                    Logger.i(TAG,"parseNetworkResponse>> " + response.message());
                    return null;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    Logger.i(TAG,"onError >> " + e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i(TAG,"onResponse >> "+response);
                }
            });
        }

    }

    public void execute(final ResultListener listener){
        RequestCall call = buildRequest();
        if (call != null){
            call.execute(new Callback<String>() {
                @Override
                public String parseNetworkResponse(Response response, int id) throws Exception {
                    Logger.i(TAG,"parseNetworkResponse>> " + response.message());
//                    listener.parseNetworkResponse(response,id);
                    return response.body().string();
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onError(call,e,id);
                    Logger.e(TAG,"onError >> " + e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    Logger.i(TAG, "Thread cur thread " + Thread.currentThread().getName() + " and onResponse >> " + response);
                    listener.onResponse(response,id);
                }
            });
        }

    }

    private RequestCall buildRequest() {
        BaseBehavior.Clause.Method method = clause.method;
        RequestCall call = null;
        switch (method){
            case GET:
                 call = OkHttpUtils.get()
                         .url(url)
                         .headers(clause.headerMap)
                         .params(clause.clauseMap)
                         .build();
                break;
            case PULL:
                 call = OkHttpUtils.post()
                         .url(url)
                         .headers(clause.headerMap)
                         .params(clause.clauseMap)
                         .build();
            default:
                    break;
        }
        Logger.i(TAG, "request url is " + url + "\n"
                + "clause is " + clause.toString() + "\n"
                + "time " + XulUtils.timestamp()
        );
        return call;
    }

    public BaseProvider setUrl(String url){
        this.url = url;
        return this;
    }

    public interface ResultListener<T>{

        void onError(Call call, Exception e, int id);
        void onResponse(String response, int id);
    }

    public abstract class SimpleResultListener<T> implements ResultListener{
        public abstract void onError(Call call, Exception e, int id);
        public abstract void onResponse(String response, int id);
    }

}
