package com.landscape.mocknetapi.sample;

import io.reactivex.Flowable;

public interface UserRepository {

  Flowable<UserBean> requestUserInfo(String userName);

}
