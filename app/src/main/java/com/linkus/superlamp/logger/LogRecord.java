package com.linkus.superlamp.logger;


public class LogRecord {
    public enum LogType {
        /**
         * 系统
         */
        SYSTEM,
        /**
         * 应用
         */
        APP,
    }

    private LogType type;
    private String content;

    public LogRecord(LogType type, String content) {
        this.type = type;
        this.content = content;
    }

    public LogType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
