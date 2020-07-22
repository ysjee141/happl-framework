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

import java.io.File;
import java.util.Optional;

/**
 * 파일 처리 유틸리티 클래스
 */
public class FileUtils {

  /**
   * 디렉토리가 있는지 확인하고 없으면 생성
   *
   * @param path 검증할 디렉토리 객체
   * @return 처리 결과
   */
  public static boolean existsAndMakeDir(String path) {
    File file = new File(path);
    return !file.exists() && !file.mkdirs();
  }

  /**
   * 디렉토리 내 파일 목록 가져오기
   *
   * @param file 검색할 디렉토리 객체
   * @return 파일 목록
   */
  public static File[] getFiles(File file) {
    File[] result = {};

    if (file != null) {
      result = Optional.ofNullable(file.listFiles()).orElse(new File[]{});
    }
    return result;
  }

}
