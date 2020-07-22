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

package kr.co.happl.framework.common.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 패스워드 기반 암화화 클래스
 */
@SuppressWarnings({"unused", "CheckStyle"})
public class PBE {
  /**
   * 데이터를 암호화하는 기능
   *
   * @param data 암호화할 데이터
   * @param pw   암호화 패스워드
   * @return 암호화된 데이터
   */
  public static String encode(String data, String pw) {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setAlgorithm("PBEWithMD5AndDES");
    encryptor.setPassword(pw);
    return encryptor.encrypt(data);
  }

  /**
   * 데이터를 복호화하는 기능
   *
   * @param data data 복호화할 데이터
   * @param pw   복호화 패스워드
   * @return 복호화된 데이터
   */
  public static String decode(String data, String pw) {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setAlgorithm("PBEWithMD5AndDES");
    encryptor.setPassword(pw);
    return encryptor.decrypt(data);
  }
}
