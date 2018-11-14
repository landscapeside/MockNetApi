package com.landscape.mocknetapi.sample;

import com.landscape.mocknetapi.annotations.MOCK;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
  @MOCK("account/user_info")
  @GET("account/user_info")
  Flowable<UserBean> requestUser(@Path("user_name") String userName);
}
