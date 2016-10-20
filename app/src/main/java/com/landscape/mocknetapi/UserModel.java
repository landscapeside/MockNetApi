package com.landscape.mocknetapi;

import android.content.Context;

import com.landscape.mockapi.MockApi;

/**
 * Created by landscape on 2016/10/20.
 */

public class UserModel {

    public String requestUser(Context context) {
        return MockApi.mockResponse(context,"http:XXXXXX/accout/user");
    }

}
