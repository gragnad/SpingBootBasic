package com.nalstudio.basic.exception;

import java.util.ArrayList;
import java.util.List;

public class ApiErrorInfo {

    private String message;

    private final List<ApiDetailErrorInfo> details = new ArrayList<ApiDetailErrorInfo>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addDetailInfo(String target, String message) {
        details.add(new ApiDetailErrorInfo(target, message));
    }

    public List<ApiDetailErrorInfo> getDetails() {
        return details;
    }
}
