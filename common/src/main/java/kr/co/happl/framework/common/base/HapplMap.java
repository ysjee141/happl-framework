/*
 * Copyright (c) $year, HAPPL Developer Group. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is a framework for developing JAVA based solutions through Spring Framework (Spring Boot).
 * This code may be used for any projects, but may not be suitable and makes no warranty.
 *
 * This code is publicly available and can be used with any solution.
 * However, HAPPL Developer Group is not responsible for any problems arising from duplication, modification
 * and redistribution of this code.
 *
 * If you have any questions regarding the reproduction, modification, redistribution or use of this code,
 * please contact us via the contact details below.
 *
 * @url https://velog.io/@ysjee141
 * @author JI YOONSEONG
 * @email ysjee141@gmail.com
 */

package kr.co.happl.framework.common.base;

import kr.co.happl.framework.common.utils.CommonUtils;
import org.json.simple.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * HapplMap
 *
 * @author JI YOONSEONG
 **/
@SuppressWarnings("unused")
public class HapplMap<K, V> extends HashMap<K, V> {
  private static final long serialVersionUID = -747258255956003430L;

  public HapplMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public HapplMap(int initialCapacity) {
    super(initialCapacity);
  }

  public HapplMap() {
    super();
  }

  public HapplMap(Map<? extends K, ? extends V> m) {
    super(m);
  }

  public <T> HapplMap(T instance) {
    this.putAll((Map<? extends K, ? extends V>) CommonUtils.convertPojoToMap(instance));
  }

  /**
   * Map 정보를 Json Object로 변환
   *
   * @return 변환된 Json Object
   */
  public JSONObject toJson() {
    JSONObject result = new JSONObject();
    if (this.keySet().toArray()[0] instanceof String) {
      //noinspection unchecked
      result = CommonUtils.convertMapToJson((Map<String, ?>) this);
    }
    return result;
  }

  /**
   * Map 객체를 MultiValueMap으로 변환
   *
   * @return 변환된 MultiValueMap
   */
  public MultiValueMap<String, String> toMultiValueMap() {
    MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
    if (this.keySet().toArray()[0] instanceof String) {
      //noinspection unchecked
      result = CommonUtils.convertMapToMultivalue((Map<String, ?>) this);
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
