package com.landscape.mockapi;

import java.util.List;

/**
 * Created by landscape on 2016/10/19.
 */

public class ApiSuite {

    private String method;
    private String fileName;

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
}
