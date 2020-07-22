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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 암호화 클래스
 */
@SuppressWarnings("unused")
public class MD5 {

  /**
   * MD5 암호화
   *
   * @param data String data MD5로 암호화 할 String
   * @return 암호화 된 String
   * @throws NoSuchAlgorithmException 예외
   */
  public static String encrypt(String data)
      throws NoSuchAlgorithmException {
    StringBuilder strEncData = new StringBuilder();

    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] byteData = data.getBytes();
    md.update(byteData);
    byte[] digest = md.digest();
    for (byte instantDigest : digest) {
      strEncData.append(Integer.toHexString(instantDigest & 0xFF).toUpperCase());
    }

    return strEncData.toString();
  }
}
