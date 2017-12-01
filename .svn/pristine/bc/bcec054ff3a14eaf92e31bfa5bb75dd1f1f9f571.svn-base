package com.linkus.superlamp.logger;

import android.util.Log;


import com.linkus.superlamp.Config;

import java.text.SimpleDateFormat;

public class Logger {
    private static int LOG_LEVEL = 6;
    private static int INFO = 3;
    private static int ERROR = 1;
    private static int WARN = 2;
    private static int DEBUG = 4;
    private static int VERBOS = 5;
    /**
     * 部分平台不打印tag,自动把TAG合msg打印
     */
    public static boolean isPrintTagInInfo = false;

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        recordLog(tag, msg);
        if (LOG_LEVEL > INFO && Config.FUNCTION_OPEN_LOGGER) {
            if (isPrintTagInInfo) {
                msg = "tag:" + tag + ", " + msg;
            }

            if (tr == null) {
                Log.i(tag, msg);
            } else {
                Log.i(tag, msg, tr);
            }
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        recordLog(tag, msg);
        if (LOG_LEVEL > ERROR && Config.FUNCTION_OPEN_LOGGER) {
            if (isPrintTagInInfo) {
                msg = "tag:" + tag + ", " + msg;
            }
            if (tr == null) {
                Log.e(tag, msg);
            } else {
                Log.e(tag, msg, tr);
            }
        }

    }

    public static void v(String tag, String msg, Throwable tr) {
        recordLog(tag, msg);
        if (LOG_LEVEL > VERBOS && Config.FUNCTION_OPEN_LOGGER) {
            if (isPrintTagInInfo) {
                msg = "tag:" + tag + ", " + msg;
            }
            if (tr == null) {
                Log.v(tag, msg);
            } else {
                Log.v(tag, msg, tr);
            }
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        recordLog(tag, msg);
        if (LOG_LEVEL > DEBUG && Config.FUNCTION_OPEN_LOGGER) {
            if (isPrintTagInInfo) {
                msg = "tag:" + tag + ", " + msg;
            }
            if (tr == null) {
                Log.d(tag, msg);
            } else {
                Log.d(tag, msg, tr);
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        recordLog(tag, msg);
        if (LOG_LEVEL > WARN && Config.FUNCTION_OPEN_LOGGER) {
            if (isPrintTagInInfo) {
                msg = "tag:" + tag + ", " + msg;
            }
            if (tr == null) {
                Log.w(tag, msg);
            } else {
                Log.w(tag, msg, tr);
            }
        }
    }

    /**
     * @deprecated，一般情况，不应调用这样的代码
     */
    @SuppressWarnings("unused")
    private static String getClassName() {
        String className = Thread.currentThread().getStackTrace()[4]
                .getClassName();
        return className.substring(className.lastIndexOf(".") + 1);

    }

    public static void i(String msg) {
        recordLog(msg);
        if (LOG_LEVEL > INFO && Config.FUNCTION_OPEN_LOGGER)

            Log.i("Starcor", msg);
    }

    public static void e(String msg) {
        recordLog(msg);
        if (LOG_LEVEL > ERROR && Config.FUNCTION_OPEN_LOGGER)
            Log.e("Starcor", msg);

    }

    public static void v(String msg) {
        recordLog(msg);
        if (LOG_LEVEL > VERBOS && Config.FUNCTION_OPEN_LOGGER)
            Log.v("Starcor", msg);
    }

    public static void d(String msg) {
        recordLog(msg);
        if (LOG_LEVEL > DEBUG && Config.FUNCTION_OPEN_LOGGER)
            Log.d("Starcor", msg);
    }


    public static void w(String msg) {
        recordLog(msg);
        if (LOG_LEVEL > WARN && Config.FUNCTION_OPEN_LOGGER)
            Log.w("Starcor", msg);
    }

    private static SimpleDateFormat dateFormat;

    private static synchronized void recordLog(String tag, String msg) {
        if (!Config.FUNCTION_ENABLE_LOGUPLOAD) {
            return;
        }
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        String log = dateFormat.format(System.currentTimeMillis()) + " tag:" + tag + "--->" + msg + "\n";
        LogCache.getInstance().addLog2Cache(LogRecord.LogType.APP, log);
    }

    private static void recordLog(String msg) {
        recordLog("Starcor", msg);
    }
}
