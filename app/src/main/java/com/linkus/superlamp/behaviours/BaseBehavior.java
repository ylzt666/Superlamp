package com.linkus.superlamp.behaviours;



import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.providers.BaseProvider;
import com.linkus.superlamp.providers.ProviderFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;


/**
 * Created by jizi.chen on 2017/8/28.
 */

public abstract class BaseBehavior {
    protected String TAG = getClass().getSimpleName();
    protected BaseProvider provider = null;
//    public Gson gson = new Gson();
    protected String url;
//    public Gson getGson() {
//        return gson;
//    }
    public BaseBehavior() {
    }

    public BaseBehavior(Class target) {
        provider = ProviderFactory.create(target);
    }

    public BaseProvider getProvider() {
        return provider;
    }

    public static void init() {
        BehaviorFactory.regist(SplashBehavior.class
                                );
    }

    public static class Clause{

        public HashMap<String, String> clauseMap = new HashMap<>();
        public HashMap<String, String> conditionMap = new HashMap<>();
        public HashMap<String, String> headerMap = new HashMap<>();
        public static Method method;

        public Clause(){
        }


        public Clause(HashMap<String, String> headerMap, HashMap<String, String> clauseMap, HashMap<String, String> conditionMap, Method method) {
            this.clauseMap = clauseMap;
            this.conditionMap = conditionMap;
            this.headerMap = headerMap;
            this.method = method;
        }

        public Clause(HashMap<String, String> cHmap, HashMap<String, String> cLmap, HashMap<String, String> cCmap) {
            new Clause(cHmap,cLmap,cCmap,null);
        }

        public Clause method(Method method){
            this.method = method;
            return this;
        }

        public Clause addClause(String key, String value) {
            clauseMap.put(key, value);
            return this;
        }

        public Clause removeClause(String key) {
            clauseMap.remove(key);
            return this;
        }

        public Clause addCondition(String key, String condtion) {
            conditionMap.put(key, condtion);
            return this;
        }

        public Clause removeCondition(String key) {
            conditionMap.remove(key);
            return this;
        }

        public Clause addHeader(String key, String condtion) {
            headerMap.put(key, condtion);
            return this;
        }

        public Clause removeHeader(String key) {
            headerMap.remove(key);
            return this;
        }

        public boolean containsHeader(String header) {
            return headerMap != null && headerMap.containsKey(header);
        }

        public boolean containsCondition(String header) {
            return conditionMap != null && conditionMap.containsKey(header);
        }

        public boolean containsClause(String header) {
            return clauseMap != null && clauseMap.containsKey(header);
        }

        public void clearAll() {
            if (headerMap != null) {
                headerMap.clear();
            }
            if (clauseMap != null) {
                clauseMap.clear();
            }
            if (conditionMap != null) {
                conditionMap.clear();
            }
        }

        public Clause makeClone() {
            try {
                return clone();
            } catch (Exception e) {
                e.printStackTrace();
                return new Clause();
            }
        }

        @Override
        protected Clause clone() throws CloneNotSupportedException {
            HashMap<String,String> cCmap = (HashMap<String, String>) conditionMap.clone();
            HashMap<String,String> cHmap = (HashMap<String, String>) headerMap.clone();
            HashMap<String,String> cLmap = (HashMap<String, String>) clauseMap.clone();
            Method method = this.method;
            return new Clause(cHmap,cLmap,cCmap,method);
        }

        public enum Method {
            GET("get"),
            PULL("pull"),
            HEAD("head"),
            PATCH("patch");
            private final String value;

            Method(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        @Override
        public String toString() {
            return "method[" + method.toString() + "]" + "\n" +
                    "headers[" + headerMap.toString() + "]" + "\n" +
                    "conditons[" + conditionMap.toString() + "]" + "\n" +
                    "clause[" + clauseMap.toString() + "]" + "\n";
        }
    }
}
