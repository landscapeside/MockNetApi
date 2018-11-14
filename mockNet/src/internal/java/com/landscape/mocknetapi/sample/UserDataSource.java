package com.landscape.mocknetapi.sample;

import android.content.Context;
import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * simulate realistic scene,unnecessary for this sample
 */
public class UserDataSource implements UserRepository {

  // not used,just for stub
  Retrofit retrofit = new Retrofit.Builder()
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl("http://test")
      .client(new OkHttpClient.Builder().build())
      .build();
  UserApi userApi;

  public UserDataSource(final Context context) {
    userApi = retrofit.create(UserApi.class);
  }

  @Override public Flowable<UserBean> requestUserInfo(String userName) {
    return userApi.requestUser(userName);
  }
}

