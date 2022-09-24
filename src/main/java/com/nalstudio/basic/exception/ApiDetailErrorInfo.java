package com.nalstudio.basic.exception;

public class ApiDetailErrorInfo {
    private String target;

    private String message;

    public ApiDetailErrorInfo(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }
}
