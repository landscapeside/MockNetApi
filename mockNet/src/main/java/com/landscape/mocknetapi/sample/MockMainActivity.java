package com.landscape.mocknetapi.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.orhanobut.logger.Logger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MockMainActivity extends AppCompatActivity {
  TextView tvResult;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.landscape.mocknetapi.R.layout.activity_mock_main);
    tvResult = findViewById(com.landscape.mocknetapi.R.id.tv_result_mock);
  }

  public void startMock(View view) {
    UserRepository repository = new UserDataSource(this);
    repository.requestUserInfo("mock!")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<UserBean>() {
          @Override public void accept(UserBean userBean) throws Exception {
            tvResult.setText(userBean.toString());
            Logger.d(userBean.toString());
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Toast.makeText(MockMainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
          }
        }, new Action() {
          @Override public void run() throws Exception {
            Toast.makeText(MockMainActivity.this, "complete", Toast.LENGTH_LONG).show();
          }
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
