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

package kr.co.happl.framework.common.utils;

import java.util.Arrays;

/**
 * Java Reflection 처리를 위한 Helper 클래스
 *
 * @author JI YOONSEONG
 */
public class ReflectUtils {

  static final Class<?>[] defaultType = {String.class};

  /**
   * 클래스가 기본 데이터 형인지 검증
   * - 기본형: Primitive / String / Number Class Type
   *
   * @param clazz 검증할 클래스 타입
   * @return 기본형 유무
   */
  public static boolean isDefaultType(Class<?> clazz) {
    return clazz.isPrimitive()
        || Arrays.asList(defaultType).contains(clazz)
        || Number.class.isAssignableFrom(clazz);
  }

}
