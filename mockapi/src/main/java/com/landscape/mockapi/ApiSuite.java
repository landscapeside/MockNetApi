package com.landscape.mockapi;

import java.util.List;

/**
 * Created by landscape on 2016/10/19.
 */

public class ApiSuite {

    private String method;
    private String fileName;
    private long timeDelay = 0l;

    public ApiSuite(String method, String fileName) {
        this.method = method;
        this.fileName = fileName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTimeDelay() {
        return timeDelay;
    }

    public ApiSuite setTimeDelay(long timeDelay) {
        this.timeDelay = timeDelay;
        return this;
    }
}
