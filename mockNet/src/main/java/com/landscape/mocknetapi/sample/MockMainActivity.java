package com.landscape.mocknetapi.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.landscape.mocknetapi.api.ApiSuite;
import com.landscape.mocknetapi.api.MockApi;

public class MockMainActivity extends AppCompatActivity {

    int count = 0;
    long lastTime = 0;
    UserModel userModel;

    TextView tvResult;
    TextView tvResult1;
    TextView tvResult2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.landscape.mocknetapi.R.layout.activity_mock_main);
        MockApi.addSuite(new ApiSuite("/accout/user", "account/user.json").setTimeDelay(3000));
        MockApi.addSuite(new ApiSuite("/accout/test1", "account/test1.json").setTimeDelay(3000));
        MockApi.addSuite(new ApiSuite("/accout/test2", "account/test2.json").setTimeDelay(3000));
        userModel = new UserModel();
//        userModel.requestUser(this, new UserModel.NetCallbk() {
//            @Override
//            public void success(String response) {
//                UserBean userBean = JSONS.parseObject(response, UserBean.class);
//                tvResult.setText(JSONS.parseJson(userBean));
//            }
//        });
//        userModel.requestTest1(this, new UserModel.NetCallbk() {
//            @Override
//            public void success(String response) {
//                UserBean userBean = JSONS.parseObject(response, UserBean.class);
//                tvResult1.setText(JSONS.parseJson(userBean));
//            }
//        });
//        userModel.requestTest2(this, new UserModel.NetCallbk() {
//            @Override
//            public void success(String response) {
//                UserBean userBean = JSONS.parseObject(response, UserBean.class);
//                tvResult2.setText(JSONS.parseJson(userBean));
//            }
//        });
        tvResult = (TextView) findViewById(com.landscape.mocknetapi.R.id.tv_result_mock);
        tvResult1 = (TextView) findViewById(com.landscape.mocknetapi.R.id.tv_result1);
        tvResult2 = (TextView) findViewById(com.landscape.mocknetapi.R.id.tv_result2);
        addTest();
    }

    private void addTest() {
        long timeDelay = 3000;
        count = 0;
        lastTime = System.currentTimeMillis();
        for (int i = 0; i < 250; i++) {
            int idx = i%2+1;
            MockApi.addSuite(new ApiSuite("/accout/test" + idx, "account/test"+idx+".json").setTimeDelay(timeDelay));
            userModel.requestStress(this,"http:XXXXXX/accout/test"+idx, new UserModel.NetCallbk() {
                @Override
                public void success(String response) {
                    UserBean userBean = JSONS.parseObject(response, UserBean.class);
                    tvResult.setText(JSONS.parseJson(userBean));
                    count++;
                    tvResult1.setText("count:" + count);
                    tvResult2.setText("use time:" + (System.currentTimeMillis()-lastTime));
                    lastTime = System.currentTimeMillis();
                }
            });
            if (i % 19 == 0) {
                timeDelay = 3000;
            } else {
                timeDelay += 150;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MockApi.unInstall();
    }
}