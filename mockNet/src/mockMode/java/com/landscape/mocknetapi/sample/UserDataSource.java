package com.landscape.mocknetapi.sample;

import android.content.Context;
import com.landscape.mocknetapi.api.MockApi;
import io.reactivex.Flowable;

public class UserDataSource implements UserRepository {

  MockApi mockApi;
  UserApi userApi;

  public UserDataSource(final Context context) {
    mockApi = MockApi.builder().context(context).delayMilliSeconds(3000).build();
    userApi = mockApi.create(UserApi.class);
  }

  @Override public Flowable<UserBean> requestUserInfo(String userName) {
    return userApi.requestUser(userName);
  }
}
