package com.linkus.superlamp.http.okhttp.builder;


import com.linkus.superlamp.http.okhttp.OkHttpUtils;
import com.linkus.superlamp.http.okhttp.request.OtherRequest;
import com.linkus.superlamp.http.okhttp.request.RequestCall;

public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
