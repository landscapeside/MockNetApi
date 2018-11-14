package com.landscape.mocknetapi.api;

import com.landscape.mocknetapi.annotations.MOCK;
import java.lang.reflect.Method;

public class AnnotationParser {

  public static boolean isMockable(Method method) {
    return method.isAnnotationPresent(MOCK.class);
  }

  public static String getMockPath(Method method) {
    MOCK mock = method.getAnnotation(MOCK.class);
    return mock.value();
  }
}
