package com.landscape.mockapi;

import android.app.Activity;
import android.content.Context;

import com.landscape.mockapi.util.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by landscape on 2016/10/20.
 */

public class MockApi {

    private static final String prefix = "mockdata";
    private static List<ApiSuite> suites = new ArrayList<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    public static void addSuite(ApiSuite suite) {
        for (ApiSuite apiSuite : suites) {
            if (suite.getMethod().equalsIgnoreCase(apiSuite.getMethod())) {
                suites.remove(apiSuite);
                break;
            }
        }
        suites.add(suite);
    }

    public static void setTimeDelay(long timeDelay) {
        for (ApiSuite apiSuite : suites) {
            apiSuite.setTimeDelay(timeDelay);
        }
    }

    public static void mockRequest(final Context context,final String url,final IMock mockListener) {
        for (final ApiSuite suite : suites) {
            if (url.contains(suite.getMethod())) {
                if (suite.getTimeDelay() > 0) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            MockApi.sleep(suite.getTimeDelay());
                            if (mockListener != null) {
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mockListener.success(mockResponse(context,url));
                                    }
                                });
                            }
                        }
                    });
                } else {
                    if (mockListener != null) {
                        mockListener.success(mockResponse(context,url));
                    }
                }
            }
        }
    }

    private static void sleep(long time) {
        try {
            if (time > 0) {
                TimeUnit.MILLISECONDS.sleep(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String mockResponse(Context context,String url) {
        String sBuffer = null;
        for (ApiSuite suite : suites) {
            if (url.contains(suite.getMethod())) {
                try {
                    sBuffer = FileReader.requestMockString(context, prefix + File.separator+suite.getFileName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sBuffer;
    }

    public static void unInstall() {
        executorService.shutdown();
    }

    public interface IMock{
        void success(String response);
    }

}
