package com.landscape.mocknetapi;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;


/**
 * Created by landscape on 2016/10/20.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
    }

    private static void initLog() {
        Logger.init("Silk V1.0")
                .methodCount(3)
                .methodOffset(2)
                .logTool(new AndroidLogTool());
    }
}
