package com.landscape.mockapi;

import android.content.Context;

import com.landscape.mockapi.util.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by landscape on 2016/10/20.
 */

public class MockApi {

    private static final String prefix = "mockdata";
    private static List<ApiSuite> suites = new ArrayList<>();

    public static void addSuite(ApiSuite suite) {
        for (ApiSuite apiSuite : suites) {
            if (suite.getMethod().equalsIgnoreCase(apiSuite.getMethod())) {
                suites.remove(apiSuite);
                break;
            }
        }
        suites.add(suite);
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

}
