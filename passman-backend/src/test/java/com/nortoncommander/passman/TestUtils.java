package com.nortoncommander.passman;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

  public static String getJsonString(Object object) throws Exception {
    return new ObjectMapper().writeValueAsString(object);
  }
}
