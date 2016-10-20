package com.landscape.mockapi.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by landscape on 2016/10/20.
 */

public class FileReader {
    public static InputStream openMockFile(Context appContext,String relativePath,boolean supportSDCard) throws IOException {
        InputStream inputStream;
        if (supportSDCard) {
            File file = new File(relativePath);
            inputStream = new FileInputStream(file);
        } else {
            inputStream = appContext.getAssets().open(relativePath);
        }
        return inputStream;
    }

    public static String requestMockString(Context appContext, String relativePath) throws IOException {
        return requestMockString(appContext, relativePath,false);
    }

    public static String requestMockString(Context appContext, String relativePath, boolean supportSDCard) throws IOException {
        StringBuilder sbJson = new StringBuilder();
        InputStream inputStream = openMockFile(appContext,relativePath,supportSDCard);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sbJson.append(line);
            }
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sbJson.toString();
    }

}
