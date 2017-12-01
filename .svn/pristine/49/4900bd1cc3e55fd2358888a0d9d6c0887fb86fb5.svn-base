package com.linkus.superlamp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 54757 on 9/12/2017.
 */

public class StartBean extends SuperBean{

    private String errorID;
    private String code;

    public String getErrorID() {
        return errorID;
    }

    public void setErrorID(String errorID) {
        this.errorID = errorID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String errorMsg;

    public List<Data> list = new ArrayList<>();


    public static class Data implements Serializable {
        public String Orderby;
        public String Url;

        public String getOrderby() {
            return Orderby;
        }

        public void setOrderby(String orderby) {
            Orderby = orderby;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "Orderby='" + Orderby + '\'' +
                    ", Url='" + Url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StartBean{" +
                "errorID='" + errorID + '\'' +
                ", code='" + code + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", list=" + list +
                '}';
    }
}
