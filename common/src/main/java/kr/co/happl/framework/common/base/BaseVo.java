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

import java.io.Serializable;

/**
 * Value Object 추상 클래스
 * <pre>
 *  - Hash Code / Equals 필수 구현
 *  - toJson 함수 지원
 * </pre>
 */
@SuppressWarnings("unused")
public abstract class BaseVo implements Serializable {

  private static final long serialVersionUID = -5698387555537853789L;

  @Override
  public abstract int hashCode();

  @Override
  public abstract boolean equals(Object obj);

  /**
   * 객체 정보를 JSON으로 변환
   * - {@link CommonUtils#convertPojoToJson} 함수를 통해 구현
   *
   * @return 객체 JSON 정보
   */
  public JSONObject toJson() {
    return CommonUtils.convertPojoToJson(this);
  }

}
