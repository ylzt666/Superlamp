package com.linkus.superlamp.behaviours;





import com.linkus.superlamp.logger.Logger;

import java.util.HashMap;

/**
 * Created by jizi.chen on 2017/8/28.
 */

public class BehaviorFactory {
    private static String TAG = "BehaviorFactory";
    private static HashMap<String, Class<? extends BaseBehavior>> mMap  = new HashMap<>();

    public static BaseBehavior create(Class<? extends BaseBehavior> clz) {
        try {
            return mMap.get(clz.getSimpleName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static final void regist(Class<? extends BaseBehavior> cls) {
        Logger.i(TAG, "regist " + cls.getSimpleName());
        mMap.put(cls.getSimpleName(), cls);
    }

    public static final void regist(Class<? extends BaseBehavior>... cls) {
        for (Class c :
                cls) {
            mMap.put(c.getSimpleName(), c);
        }
    }

}
