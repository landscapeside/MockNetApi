package com.landscape.mocknetapi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import com.landscape.mockapi.ApiSuite;
import com.landscape.mockapi.MockApi;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserModel userModel;

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MockApi.addSuite(new ApiSuite("/accout/user","account/user.json"));
        userModel = new UserModel();
        UserBean userBean = JSONS.parseObject(userModel.requestUser(this),UserBean.class);
        tvResult = (TextView) findViewById(R.id.tv_result);
        tvResult.setText(JSONS.parseJson(userBean));
    }
}
