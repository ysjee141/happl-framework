/*
 * Copyright(c) 2020, HAPPL Developer Group. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is a framework for developing JAVA based solutions through Spring Framework
 * (or Spring Boot). This code may be used for any projects, but may not be suitable and makes
 * no warranty.
 *
 * This code is publicly available and can be used with any solution.
 * However, HAPPL Developer Group is not responsible for any problems arising from duplication,
 * modification and redistribution of this code.
 *
 * If you have any questions regarding the reproduction, modification, redistribution or use of
 * this code, please contact us via the contact details below.
 *
 * @url https://velog.io/@ysjee141
 * @author JI YOONSEONG
 * @email ysjee141@gmail.com
 */

/*
 */

package kr.co.happl.framework.common.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@SuppressWarnings("unused")
public class JsonUtils {

  private static final HapplLogger logger = HapplLogger.getInstance(LoggerType.INFO);

  /**
   * JSONArray 를 Key Value 를 통해 정렬
   *
   * @param json 원본 JSONArray 객체
   * @param key  정렬할 key
   * @return 정렬된 JSONArray
   */
  @SuppressWarnings("unchecked")
  public static JSONArray sortJsonByKey(JSONArray json, String key) {
    JSONArray sorted = new JSONArray();
    SortedMap<Object, JSONObject> map = new TreeMap<>();

    for (Object o : json) {
      JSONObject tmp = (JSONObject) o;
      map.put(tmp.get(key), tmp);
    }

    Set<Object> numbers = map.keySet();

    for (Object number : numbers) {
      sorted.add(map.get(number));
    }

    return sorted;
  }

  /**
   * JSON 객체를 Pojo 형식으로 변환
   *
   * @param json  원본 Json 데이터
   * @param clazz 변환할 Pojo 클래스 타입
   * @param <T>   변환할 Pojo 클래스 타입
   * @return 변환된 Pojo 인스턴스
   */
  public static <T> T jsonBind(JSONObject json, Class<T> clazz) {
    return jsonBind(json.toJSONString(), clazz);
  }

  /**
   * JSON 객체를 Pojo 형식으로 변환
   *
   * @param json  원본 Json 데이터
   * @param clazz 변환할 Pojo 클래스 타입
   * @param <T>   변환할 Pojo 클래스 타입
   * @return 변환된 Pojo 인스턴스
   */
  public static <T> T jsonBind(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      return mapper.readValue(json, clazz);
    } catch (JsonParseException e) {
      logger.error(e, "JsonParseException");
    } catch (JsonMappingException e) {
      logger.error(e, "JsonMappingException");
    } catch (IOException e) {
      logger.error(e, "IOException");
    }
    return null;
  }

}
