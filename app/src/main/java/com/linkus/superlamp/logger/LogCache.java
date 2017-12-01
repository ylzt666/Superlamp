package com.linkus.superlamp.logger;








import com.linkus.superlamp.Config;

import java.util.ArrayList;
import java.util.List;

public class LogCache implements BaseCache {
    public static final int LOG_RECORD_LIMIT = 768;
    private List<LogRecord> cacheList;
    private static LogCache logCache;

    public static LogCache getInstance() {
        if (logCache == null) {
            logCache = new LogCache();
        }
        return logCache;
    }

    public LogCache() {
        cacheList = new ArrayList<LogRecord>();
    }

    @Override
    public LogRecord getCache() {
        if (cacheList != null && !cacheList.isEmpty()) {
            return cacheList.remove(0);
        }
        return null;
    }

    StringBuilder _appLogCache;
    StringBuilder _sysLogCache;

    @Override
    public synchronized void addLog2Cache(LogRecord.LogType type, String msg) {
        if (!Config.FUNCTION_ENABLE_LOGUPLOAD) {
            return;
        }
        if (cacheList == null) {
            return;
        }

        switch (type) {
            case APP:
                if (_appLogCache == null) {
                    _appLogCache = new StringBuilder();
                }
                if (writeCache(type, msg, _appLogCache)) {
                    _appLogCache = null;
                }
                break;
            case SYSTEM:
            default:
                if (_sysLogCache == null) {
                    _sysLogCache = new StringBuilder();
                }
                if (writeCache(type, msg, _sysLogCache)) {
                    _sysLogCache = null;
                }
                break;
        }
    }

    private boolean writeCache(LogRecord.LogType type, String msg, StringBuilder cache) {
        if (cache.length() == 0 && msg.length() >= LOG_RECORD_LIMIT) {
            addLog2Cache(new LogRecord(type, msg + "\n"));
        } else {
            cache.append(msg).append("\n");
            if (cache.length() >= LOG_RECORD_LIMIT) {
                addLog2Cache(new LogRecord(type, cache.toString()));
                return true;
            }
        }
        return false;
    }

    private void addLog2Cache(LogRecord logRecord) {
        if (logRecord == null) {
            return;
        }
        synchronized (cacheList) {
            //Avoid unusual situation
            if (cacheList.size() > 10000) {
                cacheList.clear();
            }
            cacheList.add(logRecord);
        }
    }

}
