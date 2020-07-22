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

package kr.co.happl.framework.common.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Sha512 {
  /**
   * Sha-512 방식 암호화
   *
   * @param planText 원본 문자열
   * @return 암호화 된 문자열
   */
  public static String encrypt(String planText) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.reset();
      md.update(planText.getBytes(StandardCharsets.UTF_8));

      return String.format("%040x", new BigInteger(1, md.digest()));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}