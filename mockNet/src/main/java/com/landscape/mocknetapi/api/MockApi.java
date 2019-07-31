package com.landscape.mocknetapi.api;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.landscape.mocknetapi.util.FileReader;
import io.reactivex.Flowable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by landscape on 2016/10/20.
 */
public class MockApi {

  private Context context;
  private MockConvertor convertor;
  private long delayMilliSeconds;

  private MockApi(Builder builder) {
    context = builder.context;
    convertor = builder.convertor;
    delayMilliSeconds = builder.delayMilliSeconds;
  }

  public static Builder builder() {
    return new Builder();
  }

  public <T> T create(final Class<T> service) {

    return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
        new InvocationHandler() {
          @Override public Object invoke(Object proxy, Method method, @Nullable Object[] args)
              throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
              return method.invoke(this, args);
            }
            Type returnType = method.getGenericReturnType();
            if (AnnotationParser.isMockable(method)) {
              String mockResp = matchMockResponse(context, AnnotationParser.getMockPath(method));
              if (isMockDataAvailable(mockResp, returnType)) {
                return Flowable.just(convertor.convert(mockResp, returnType))
                    .delay(delayMilliSeconds, TimeUnit.MILLISECONDS);
              } else {
                return genErrorFlowable("mock data not exist");
              }
            } else {
              return genErrorFlowable("method is not mockable");
            }
          }
        });
  }

  private boolean isMockDataAvailable(String mockResp, Type returnType) {
    return !TextUtils.isEmpty(mockResp) && null != convertor.convert(mockResp, returnType);
  }

  private Flowable genErrorFlowable(String errMsg) {
    return Flowable.error(new Throwable(errMsg));
  }

  private static final String prefix = "mockdata";
  private static final String suffix = ".json";

  private String matchMockResponse(Context context, String url) {
    String sBuffer = null;
    try {
      sBuffer =
          FileReader.requestMockString(context, prefix + File.separator + url + suffix);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sBuffer;
  }

  public static final class Builder {
    private Context context;
    private MockConvertor convertor = new MockConvertor();
    private long delayMilliSeconds = 0;

    private Builder() {
    }

    public Builder context(Context val) {
      context = val;
      return this;
    }

    public Builder convertor(MockConvertor val) {
      convertor = val;
      return this;
    }

    public Builder delayMilliSeconds(long val) {
      delayMilliSeconds = val;
      return this;
    }

    public MockApi build() {
      if (context == null) {
        throw new IllegalArgumentException("context cannot be empty!");
      }
      return new MockApi(this);
    }
  }
}
