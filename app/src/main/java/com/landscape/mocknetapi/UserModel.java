package com.landscape.mocknetapi;

import android.content.Context;

import com.landscape.mockapi.MockApi;

/**
 * Created by landscape on 2016/10/20.
 */

public class UserModel {

    public void requestUser(Context context,final NetCallbk callbk) {
        MockApi.mockRequest(context, "http:XXXXXX/accout/user", new MockApi.IMock() {
            @Override
            public void success(String response) {
                if (callbk != null) {
                    callbk.success(response);
                }
            }
        });
    }

    public void requestTest1(Context context,final NetCallbk callbk) {
        MockApi.mockRequest(context, "http:XXXXXX/accout/test1", new MockApi.IMock() {
            @Override
            public void success(String response) {
                if (callbk != null) {
                    callbk.success(response);
                }
            }
        });
    }

    public void requestTest2(Context context,final NetCallbk callbk) {
        MockApi.mockRequest(context, "http:XXXXXX/accout/test2", new MockApi.IMock() {
            @Override
            public void success(String response) {
                if (callbk != null) {
                    callbk.success(response);
                }
            }
        });
    }

    public void requestStress(Context context,String url,final NetCallbk callbk) {
        MockApi.mockRequest(context, url, new MockApi.IMock() {
            @Override
            public void success(String response) {
                if (callbk != null) {
                    callbk.success(response);
                }
            }
        });
    }

    public interface NetCallbk{
        void success(String response);
    }

}
