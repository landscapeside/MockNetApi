package com.landscape.mocknetapi.api;

import com.landscape.mocknetapi.util.JSONS;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MockConvertor {

  public <T> T convert(String resp, Type returnType) {
    return JSONS.parseObject(resp, ((ParameterizedType)returnType).getActualTypeArguments()[0]);
  }
}
