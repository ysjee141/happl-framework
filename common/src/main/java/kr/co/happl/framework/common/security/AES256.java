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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 암/복호화 클래스
 *
 * @author 지윤성
 */
@SuppressWarnings({"unused", "CheckStyle"})
public class AES256 {

  static final String secretKey = "12345678901234567890123456789012"; //32bit
  static String IV = ""; //16bit
  private static volatile AES256 INSTANCE;

  private AES256() {
    IV = secretKey.substring(0, 16);
  }

  public static AES256 getInstance() {
    return LazyHolder.INSTANCE;
  }

  /**
   * AES-256 암호화 함수
   *
   * @param str 원본 문자열
   * @return 암호화 된 문자열
   * @throws Exception 예외
   */
  public static String encode(String str) throws Exception {
    byte[] keyData = secretKey.getBytes();

    SecretKey secureKey = new SecretKeySpec(keyData, "AES");

    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

    byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));

    return new String(Base64.getEncoder().encode(encrypted));
  }

  /**
   * AES-256 복호화
   *
   * @param str 암호화 된 문자열
   * @return 복호화 된 원본 문자열
   * @throws Exception 예외
   */
  public static String decode(String str) throws Exception {
    byte[] keyData = secretKey.getBytes();
    SecretKey secureKey = new SecretKeySpec(keyData, "AES");
    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    c.init(Cipher.DECRYPT_MODE, secureKey,
        new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

    byte[] byteStr = Base64.getDecoder().decode(str.getBytes());

    return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
  }

  @SuppressWarnings("InstantiationOfUtilityClass")
  private static class LazyHolder {
    public static final AES256 INSTANCE = new AES256();
  }
}