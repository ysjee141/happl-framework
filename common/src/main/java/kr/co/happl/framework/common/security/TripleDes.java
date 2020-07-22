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

import kr.co.happl.framework.common.utils.StringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@SuppressWarnings("unused")
public class TripleDes {
  //default Key
  private static final String DEFAULT_KEY = "ts_tourvisbackoffice-key";

  /**
   * Triple DES Key 생성
   *
   * @param keyValue Triple Des 암호화 키 문자열
   * @return 생성된 Triple Des Key
   * @throws Exception 예외
   */
  public static Key getKey(String keyValue) throws Exception {
    DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
    return keyFactory.generateSecret(desKeySpec);
  }

  /**
   * 문자열을 암호화
   *
   * @param originText 원본 문자열
   * @param secretKey  암호화 키
   * @return 암호화 된 문자열
   * @throws Exception 예외
   */
  public static String encrypt(String originText, String secretKey) throws Exception {

    String key;
    if (secretKey.length() != 24) {
      key = DEFAULT_KEY;
    } else {
      key = secretKey;
    }

    if (StringUtils.isNoneBlank(originText)) {
      javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede/ECB/PKCS5Padding");
      cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getKey(key));
      byte[] inputBytes = originText.getBytes(StandardCharsets.UTF_8);
      byte[] outputBytes = cipher.doFinal(inputBytes);
      return new String(Base64.getEncoder().encode(outputBytes));
    } else {
      return "";
    }

  }

  /**
   * 문자열 복호화
   *
   * @param encryptedText 암호화 된 문자열
   * @param secretKey  암호화 키
   * @return 복호화 된 문자열
   * @throws Exception 예외
   */
  public static String decrypt(String encryptedText, String secretKey) throws Exception {
    String key;
    if (secretKey.length() != 24) {
      key = DEFAULT_KEY;
    } else {
      key = secretKey;
    }

    if (StringUtils.isNoneBlank(encryptedText)) {
      javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede/ECB/PKCS5Padding");
      cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey(key));
      byte[] inputBytes = Base64.getDecoder().decode(encryptedText.getBytes());
      byte[] outputBytes = cipher.doFinal(inputBytes);

      return new String(outputBytes, StandardCharsets.UTF_8);
    } else {
      return "";
    }
  }

  /*
   * TripleDES에서 사용 할 IV 생성
   */
  private static IvParameterSpec getDesIV(String userIv) {
    return new IvParameterSpec(userIv.getBytes());
  }

  /*
   * TripleDES에서 사용 할 키 생성
   */
  private static Key getDesKey(String keyValue) throws Exception {
    String lastKey = keyValue.substring(0, 8);
    String encKey = keyValue + lastKey;

    DESedeKeySpec desKeySpec = new DESedeKeySpec(encKey.getBytes());
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");

    return keyFactory.generateSecret(desKeySpec);
  }

}
