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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 문자열 처리를 위한 유틸리티
 */
@SuppressWarnings("unused")
public class StringUtils {

  private static final char[] ChoSung = {
      // 0x3131, 0x3132, 0x3134, 0x3137 // ㄱ ㄲ ㄴ ㄷ
      // , 0x3138, 0x3139, 0x3141, 0x3142 // ㄸ ㄹ ㅁ ㅂ
      // , 0x3143, 0x3145, 0x3146, 0x3147 // ㅃ ㅅ ㅆ ㅇ
      // , 0x3148, 0x3149, 0x314a, 0x314b // ㅈ ㅉ ㅊ ㅋ
      // , 0x314c, 0x314d, 0x314e // ㅌ ㅍ ㅎ
      0x3131, 0x3131, 0x3134, 0x3137, // ㄱ ㄱ ㄴ ㄷ
      0x3137, 0x3139, 0x3141, 0x3142, // ㄷ ㄹ ㅁ ㅂ
      0x3142, 0x3145, 0x3145, 0x3147, // ㅂ ㅅ ㅅ ㅇ
      0x3148, 0x3148, 0x314a, 0x314b, // ㅈ ㅈ ㅊ ㅋ
      0x314c, 0x314d, 0x314e // ㅌ ㅍ ㅎ
  };

  /**
   * 입력된 문자의 한글 초성 가져오기
   *
   * @param displayName 검색할 문자열
   * @return 한글 초성
   */
  public static String getIndexChar(String displayName) {
    if (displayName == null || displayName.length() == 0) {
      return "";
    }

    // Character.isUpperCase(key.charAt(0))
    char indexChar = displayName.trim().charAt(0);
    int index;
    String result;
    if (indexChar >= 0xAC00 && indexChar <= 0xD7A3) { // 가~ 힣
      index = (indexChar - 0xAC00) / (21 * 28);
      result = String.valueOf(ChoSung[index]);
    } else {
      result = String.valueOf(indexChar).toUpperCase();
    }

    return result;
  }


  /**
   * String 객체가 비어 있는지 확인
   *
   * @param s 체크할 문자열
   * @return 빈 객체 여부(true: 비어있음)
   */
  public static boolean isEmpty(String s) {
    return Optional.ofNullable(s).orElse("").trim().equals("");
  }

  /**
   * 문자열 내 공백이 포함 되어 있는지 확인
   *
   * @param css 체크할 문자열
   * @return 공백 포함 여부(true: 포함)
   */
  public static boolean isAnyBlank(CharSequence... css) {
    if (css.length == 0) {
      return true;
    } else {
      int len = css.length;

      for (CharSequence cs : css) {
        for (int i = 0; i < cs.length(); i++) {
          if (isBlank(Character.toString(cs.charAt(i)))) {
            return true;
          }
        }
      }
      return false;
    }
  }

  /**
   * 문자열 내 공백이 없는지 확인
   *
   * @param css 체크할 문자열
   * @return 공백 포함 여부(true: 포함 되어 있지 않음)
   */
  public static boolean isNoneBlank(CharSequence... css) {
    return !isAnyBlank(css);
  }

  /**
   * 문자열이 공백 인지 확인
   *
   * @param cs 체크할 문자열
   * @return 공백 여부 (true: 공백)
   */
  public static boolean isBlank(CharSequence cs) {
    int strLen;
    if (cs != null && (strLen = cs.length()) != 0) {
      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 문자열이 공백이 아닌지 확인
   *
   * @param cs 체크할 문자열
   * @return 공백 여부 (true: 공백이 아님)
   */
  public static boolean isNotBlank(CharSequence cs) {
    return !isBlank(cs);
  }

  /**
   * 숫자에 3개 마다 콤마(,)를 추가 한다.
   * addComma(12345678)
   * -> "12,345,678"
   *
   * @param value 변환할 문자열
   * @return 콤마(, )가 추가된 문자열
   */
  public static <T> String addComma(T value) {
    DecimalFormat df = new DecimalFormat("###,###");
    return df.format(value);
  }

  /**
   * 문자열을 구분자로 분리하여 List 객체로 반환
   *
   * @param str       분리할 문자열
   * @param delimiter 구분자
   * @return 분리된 List 객체
   */
  public static List<String> splitToList(String str, String delimiter) {

    ArrayList<String> list;

    if (str == null || "".equals(str)) {
      return null;
    }

    list = new ArrayList<>();
    int startIdx = 0;
    int oldIdx = 0;

    while (true) {
      startIdx = str.indexOf(delimiter, startIdx);
      if (startIdx == -1) {
        list.add(str.substring(oldIdx));
        break;
      }

      list.add(str.substring(oldIdx, startIdx));

      startIdx += delimiter.length();
      oldIdx = startIdx;
    }

    return list;

  }

  /**
   * 문자열을 구분자로 분리하여 문자열 배열로 반환
   *
   * @param str       분리할 문자열
   * @param delimiter 구분자
   * @return 분리된 문자열 배열
   */
  public static String[] split(String str, String delimiter) {
    return splitToList(str, delimiter).toArray(String[]::new);
  }

}
