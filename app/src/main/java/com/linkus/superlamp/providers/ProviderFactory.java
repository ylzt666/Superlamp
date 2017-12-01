package com.linkus.superlamp.providers;

import java.util.HashMap;

/**
 * Created by jizi.chen on 2017/8/28.
 */

public class ProviderFactory {

    public static HashMap<String, Class<? extends BaseProvider>> mMap = new HashMap<>();

    public static BaseProvider create(Class<? extends BaseProvider> clazz) {
        try {
            return mMap.get(clazz.getSimpleName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void register(Class<? extends BaseProvider> clazz) {
        mMap.put(clazz.getSimpleName(), clazz);
    }

    public static void register(Class<? extends BaseProvider>... clazz) {
        for (Class c : clazz) {
            mMap.put(c.getSimpleName(), c);
        }
    }

    @Override
    public String toString() {
        return mMap.toString();
    }
}
