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

package kr.co.happl.framework.common.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * timezone 관련 정책
 * http://joda-time.sourceforge.net/timezones.html
 * http://lifeiscool.tistory.com/118
 */
@SuppressWarnings("unused")
public class DateUtils {
  //properties로 부터 가져 오도록 변경할 것.
  private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String DEFAULT_TIMEZONE = "Asia/Seoul";

  // 참고 : https://github.com/sid2656/utils_utils/blob/master/code/utils/src/main/java/utils/utils/DateUtil.java
  private DateUtils() {
  }

  /**
   * 사용 가능한 Time Zone 구하기
   *
   * @return 사용 가능한 Time Zone
   */
  @SuppressWarnings("unchecked")
  public static JSONArray getAvailableTimeZones() {
    JSONArray timezoneInfos = new JSONArray();

    Set<String> zoneIds = DateTimeZone.getAvailableIDs();
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("ZZ");

    for (String zoneId : zoneIds) {
      String offset = dateTimeFormatter.withZone(DateTimeZone.forID(zoneId)).print(0);
      String longName = TimeZone.getTimeZone(zoneId).getDisplayName();
      Map<String, Object> timezoneObj = new HashMap<>();
      timezoneObj.put("id", zoneId);
      timezoneObj.put("offset", offset);
      timezoneObj.put("name", longName);

      timezoneInfos.add(new JSONObject(timezoneObj));
    }

    return timezoneInfos;
  }

  /**
   * 현재 날짜를 UTC 형식으로 가져오기
   *
   * @return 현재 날짜
   */
  public static String getUtcString() {
    DateTime dt = new DateTime(DateTimeZone.UTC);
    return dt.toString(DEFAULT_FORMAT);
  }

  /**
   * 현재 날짜를 UTC 형식으로 포맷 지정하여 가져오기
   *
   * @param dateFormat 날짜 포맷
   * @return 현재 날짜
   */
  public static String getUtcString(String dateFormat) {
    DateTime dt = new DateTime(DateTimeZone.UTC);
    return dt.toString(dateFormat);
  }

  public static DateTime getUtcTime() {
    return DateTime.now(DateTimeZone.UTC);
  }

  /**
   * 날짜를 현재 Timezone 날짜로 변환하기
   *
   * @param dt 변환 할 날짜 문자열
   * @return 변환된 날짜
   */
  public static String getLocalString(String dt) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_FORMAT);
    DateTime paramdt = formatter.withZoneUTC().parseDateTime(dt);
    DateTime localdt = new DateTime(paramdt, DateTimeZone.forID(DEFAULT_TIMEZONE));
    return localdt.toString(DEFAULT_FORMAT);
  }

  /**
   * 날짜를 현재 Timezone 날짜로 변환하기
   *
   * @param dt 변환 할 날짜 문자열
   * @return 변환된 날짜
   */
  public static String getLocalString(DateTime dt) {
    DateTime localdt = new DateTime(dt, DateTimeZone.forID(DEFAULT_TIMEZONE));
    return localdt.toString(DEFAULT_FORMAT);
  }

  /**
   * 날짜를 현재 Timezone 날짜로 변환하기
   *
   * @param dt         변환 할 날짜 문자열
   * @param dateFormat 날짜 포맷
   * @return 변환된 날짜
   */
  public static String getLocalString(String dt, String dateFormat) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
    DateTime paramdt = formatter.withZoneUTC().parseDateTime(dt);
    DateTime localdt = new DateTime(paramdt, DateTimeZone.forID(DEFAULT_TIMEZONE));
    return localdt.toString(dateFormat);
  }

  /**
   * 날짜를 현재 Timezone 날짜로 변환하기
   *
   * @param dt         변환 할 날짜 문자열
   * @param dateFormat 날짜 포맷
   * @return 변환된 날짜
   */
  public static String getLocalString(DateTime dt, String dateFormat) {
    DateTime localdt = new DateTime(dt, DateTimeZone.forID(DEFAULT_TIMEZONE));
    return localdt.toString(dateFormat);
  }

  /**
   * 날짜를 현재 Timezone 날짜로 변환하기
   *
   * @param dt         변환 할 날짜 문자열
   * @param dateFormat 날짜 포맷
   * @param timeZone   적용할 TimeZone
   * @return 변환된 날짜
   */
  public static String getLocalString(String dt, String dateFormat, String timeZone) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
    DateTime paramdt = formatter.withZoneUTC().parseDateTime(dt);
    DateTime localdt = new DateTime(paramdt, DateTimeZone.forID(timeZone));
    return localdt.toString(dateFormat);
  }

  /**
   * 날짜를 Timezone 적용 날짜로 변환 하기
   *
   * @param dt         변환 할 날짜
   * @param dateFormat 날짜 포맷
   * @param timeZone   적용할 TimeZone
   * @return 변환된 날짜
   */
  public static String getLocalString(DateTime dt, String dateFormat, String timeZone) {
    DateTime localdt = new DateTime(dt, DateTimeZone.forID(timeZone));
    return localdt.toString(dateFormat);
  }

  /**
   * 날짜/시간를 현재 Timezone 날짜로 변환하기
   *
   * @param dt 변환 할 날짜 문자열
   * @return 변환된 날짜/시간
   */
  public static DateTime getLocalTime(String dt) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_FORMAT);
    DateTime paramdt = formatter.withZoneUTC().parseDateTime(dt);
    return new DateTime(paramdt, DateTimeZone.forID(DEFAULT_TIMEZONE));
  }

  /**
   * 날짜/시간을 현재 Timezone 날짜로 변환 하기
   *
   * @param dt 변환 할 날짜/시간
   * @return 변환된 날짜/시간
   */
  public static DateTime getLocalTime(DateTime dt) {
    return new DateTime(dt, DateTimeZone.forID(DEFAULT_TIMEZONE));
  }

  /**
   * 날짜/시간을 현재 Timezone 날짜로 변환 하기
   *
   * @param dt         변환 할 날짜/시간 문자열
   * @param dateFormat 날짜 포맷
   * @return 변환된 날짜/시간
   */
  public static DateTime getLocalTime(String dt, String dateFormat) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
    DateTime paramdt = formatter.withZoneUTC().parseDateTime(dt);
    return new DateTime(paramdt, DateTimeZone.forID(DEFAULT_TIMEZONE));
  }

  /**
   * 날짜/시간을 Timezone 적용하여 날짜로 변환 하기
   *
   * @param dt         변환 할 날짜/시간 문자열
   * @param dateFormat 날짜 포맷
   * @param timeZone   적용할 TimeZone
   * @return 변환된 날짜/시간
   */
  public static DateTime getLocalTime(String dt, String dateFormat, String timeZone) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
    DateTime paramdt = formatter.withZoneUTC().parseDateTime(dt);
    return new DateTime(paramdt, DateTimeZone.forID(timeZone));
  }

  /**
   * 날짜/시간을 Timezone 적용하여 날짜로 변환 하기
   *
   * @param dt       변환 할 날짜/시간
   * @param timeZone 적용할 TimeZone
   * @return 변환된 날짜/시간
   */
  public static DateTime getLocalTime(DateTime dt, String timeZone) {
    return new DateTime(dt, DateTimeZone.forID(timeZone));
  }

  /**
   * 오늘 날짜
   *
   * @param dateFormat 날짜 포맷
   * @return 지정된 날짜 포맷으로 오늘 날짜 반환
   */
  public static String getCurrentDay(String dateFormat) {

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(System.currentTimeMillis()));

    return new SimpleDateFormat(dateFormat).format(cal.getTime());
  }

  /**
   * 날짜 계산 함수 (일 단위)
   *
   * @param dateFormat 날짜 포맷
   * @param del        계산할 날짜(일 단위)
   * @return 계산된 날짜 (YYYY-MM-dd)
   */
  public static String getAddDate(String dateFormat, int del) {

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(System.currentTimeMillis()));
    cal.add(Calendar.DATE, del);

    return new SimpleDateFormat(dateFormat).format(cal.getTime());
  }

  /**
   * 날짜 더하기
   *
   * @param sourceDate 원래 날짜
   * @param dateFormat 날짜 포맷
   * @param del        계산할 날짜 값
   * @param dateType   계산할 날짜 값 유형
   * @return 계산된 날짜
   * @throws Exception 예외
   */
  public static String getAddDate(
      String sourceDate, String dateFormat, int del, int dateType
  ) throws Exception {

    String date;

    if (del == 0) {
      date = sourceDate;
    } else {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new SimpleDateFormat(dateFormat).parse(sourceDate));
      cal.add(dateType, del);
      date = new SimpleDateFormat(dateFormat).format(cal.getTime());
    }

    return date;
  }

  /**
   * 날짜 계산 (월 단위)
   *
   * @param dateFormat 날짜 포맷
   * @param del        계산할 날짜 값(월 단위)
   * @return 계산된 날짜
   */
  public static String getMonthAgoDate(String dateFormat, int del) {

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(System.currentTimeMillis()));
    cal.add(Calendar.MONTH, del);

    return new SimpleDateFormat(dateFormat).format(cal.getTime());
  }

  /**
   * 요일 구하기
   *
   * @param sourceDate 원본 날짜
   * @param dateFormat 날짜 포맷
   * @return 요일 상수 값
   */
  public static int getDayOfWeek(String sourceDate, String dateFormat) throws Exception {
    int dayOfWeek;

    Calendar cal = Calendar.getInstance();
    cal.setTime(new SimpleDateFormat(dateFormat).parse(sourceDate));

    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

    return dayOfWeek;
  }

  /**
   * 특정 년, 월, 주 차의 특정 요일 날짜 구하기
   *
   * @param year       년도
   * @param month      월
   * @param week       주차
   * @param dateOfWeek 요일
   * @param dateFormat 날자 포맷
   * @return 계산된 날짜 반환
   */
  public static String getDateByDateOfWeek(
      String year, String month, String week, int dateOfWeek, String dateFormat
  ) {
    Calendar c = Calendar.getInstance();

    int y = Integer.parseInt(year);
    int m = Integer.parseInt(month) - 1;
    int w = Integer.parseInt(week);

    c.set(Calendar.YEAR, y);
    c.set(Calendar.MONTH, m);
    c.set(Calendar.WEEK_OF_MONTH, w);

    switch (dateOfWeek) {
      case 0:
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        break;
      case 1:
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        break;
      case 2:
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        break;
      case 3:
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        break;
      case 4:
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        break;
      case 5:
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        break;
      case 6:
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        break;
      default:
        throw new NullPointerException();
    }

    return new SimpleDateFormat(dateFormat).format(c.getTime());
  }

  /**
   * 마지막 요일 날짜 구하기
   *
   * @param year       년도
   * @param month      월
   * @param dayOfWeek  주차
   * @param dateFormat 날짜 포맷
   * @return 계산된 날짜
   */
  public static String getLastDayOfWeek(int year, int month, int dayOfWeek, String dateFormat) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month - 1, 1);

    int nowDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    if (nowDayOfWeek > dayOfWeek) {
      cal.add(Calendar.DAY_OF_MONTH, -(nowDayOfWeek - dayOfWeek));
    } else {
      cal.add(Calendar.DAY_OF_MONTH, -(7 - dayOfWeek));
    }

    return new SimpleDateFormat(dateFormat).format(cal.getTime());
  }

  /**
   * 첫번째 요일 날짜 구하기
   *
   * @param year       년도
   * @param month      월
   * @param dayOfWeek  주차
   * @param dateFormat 날짜 포맷
   * @return 계산된 날짜
   */
  public static String getFirstDayOfWeek(int year, int month, int dayOfWeek, String dateFormat) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, month - 1, 1);

    int nowDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    if (nowDayOfWeek > dayOfWeek) {
      cal.add(Calendar.DAY_OF_MONTH, ((7 - nowDayOfWeek) + dayOfWeek));
    } else {
      cal.add(Calendar.DAY_OF_MONTH, (dayOfWeek - nowDayOfWeek));
    }

    return new SimpleDateFormat(dateFormat).format(cal.getTime());
  }

  /**
   * 기간별 날짜 구하기
   *
   * @param startDateParam 시작 날짜
   * @param endDateParam   종료 날짜
   * @return 계산된 날짜
   * @throws ParseException Parsing 예외
   */
  public static List<String> getPeriodDay(
      String startDateParam, String endDateParam
  ) throws ParseException {

    List<Date> dates = new ArrayList<>();
    List<String> dateList = new ArrayList<>();

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = formatter.parse(startDateParam);
    Date endDate = formatter.parse(endDateParam);
    long interval = 24 * 1000 * 60 * 60;
    long endTime = endDate.getTime();
    long curTime = startDate.getTime();
    while (curTime <= endTime) {
      dates.add(new Date(curTime));
      curTime += interval;
    }
    for (Date date : dates) {
      String ds = formatter.format(date);
      dateList.add(ds);
    }

    return dateList;
  }

  /**
   * 날짜에 Time Zone 적용 하기
   * UTC 값이 true 인 경우 Local Time 반환
   * UTC 값이 false 인 경우 UTC Time 반환
   *
   * @param datetime 적용할 날짜
   * @param utc      UTC 유무
   * @return Time Zone 적용된 날짜
   * @link timestamp 관련 참고 - http://jsonobject.tistory.com/34
   */
  public static String formatUtcToLocalAndBackTime(String datetime, boolean utc) {
    String returnTimeDate;
    DateTime dtUtc;
    DateTimeZone timezone = DateTimeZone.getDefault();
    DateTimeFormatter formatDT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTime dateDateTime1 = formatDT.parseDateTime(datetime);
    DateTime now = new DateTime();
    DateTime nowUtc = new LocalDateTime(now).toDateTime(DateTimeZone.UTC);
    long instant = now.getMillis();
    long instantUtc = nowUtc.getMillis();
    long offset = instantUtc - instant;

    if (utc) {
      //convert to local time
      dtUtc = dateDateTime1.withZoneRetainFields(DateTimeZone.UTC);
      //dtUTC = dateDateTime1.toDateTime(timezone);
      dtUtc = dtUtc.plusMillis((int) offset);
    } else {
      //convert to UTC time
      dtUtc = dateDateTime1.withZoneRetainFields(timezone);
      dtUtc = dtUtc.minusMillis((int) offset);
    }

    returnTimeDate = dtUtc.toString(formatDT);

    return returnTimeDate;
  }

  /**
   * 날짜 문자열을 Date 객체로 변환
   *
   * @param strDate 원본 날짜 문자열
   * @return 변환된 Date 객체
   */
  public static Date strToDate(String strDate) {
    if (strDate == null || strDate.length() < 6) {
      throw new IllegalArgumentException("illeage date format");
    }

    String fmt = "yyyy-MM-dd HH:mm:ss";

    if (strDate.length() == 19) {
      if (strDate.indexOf("-") > 0) {
        fmt = "yyyy-MM-dd HH:mm:ss";
      } else if (strDate.indexOf("/") > 0) {
        fmt = "yyyy/MM/dd HH:mm:ss";
      }
    } else if (strDate.length() == 18) {
      if (strDate.indexOf("-") > 0) {
        fmt = "yyyy-MM-dd HH:mm:ss";
      } else if (strDate.indexOf("/") > 0) {
        fmt = "yyyy/MM/dd HH:mm:ss";
      }
    } else if (strDate.length() == 16) {
      if (strDate.indexOf("-") > 0) {
        fmt = "yyyy-MM-dd HH:mm";
      } else if (strDate.indexOf("/") > 0) {
        fmt = "yyyy/MM/dd HH:mm";
      }
    } else if (strDate.length() == 14) {

      fmt = "yyyyMMddHHmmss";
    } else if (strDate.length() == 10) {
      if (strDate.indexOf("-") > 0) {
        fmt = "yyyy-MM-dd";
      } else if (strDate.indexOf("/") > 0) {
        fmt = "yyyy/MM/dd";
      } else if (strDate.indexOf(".") > 0) {
        fmt = "yyyy.MM.dd";
      }
    } else if (strDate.length() == 8) {
      if (strDate.indexOf("-") > 0) {
        fmt = "yy-MM-dd";
      } else if (strDate.indexOf("/") > 0) {
        fmt = "yy/MM/dd";
      } else if (strDate.indexOf(".") > 0) {
        fmt = "yy.MM.dd";
      } else {
        fmt = "yyyyMMdd";
      }
    }

    SimpleDateFormat formatter = new SimpleDateFormat(fmt);
    ParsePosition pos = new ParsePosition(0);
    return formatter.parse(strDate, pos);
  }

  /**
   * 문자열 형식 날짜를 Date 형식으로 변환하기
   *
   * @param strDate 변환할 날짜 문자열
   * @param fmt     날짜 포맷
   * @return 변환된 Date 객체
   */
  public static Date strToDate(String strDate, String fmt) {
    SimpleDateFormat formatter = new SimpleDateFormat(fmt);
    ParsePosition pos = new ParsePosition(0);
    return formatter.parse(strDate, pos);
  }

  /**
   * 날짜 비교 (일 단위)
   *
   * @param date1 비교 할 날짜 1
   * @param date2 비교 할 날짜 2
   * @return 비교 결과 (일 단위)
   */
  public static int diffDate(Date date1, Date date2) {
    return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
  }

  /**
   * 날짜 비교 (일 단위)
   *
   * @param date1 비교 할 날짜 1
   * @param date2 비교 할 날짜 2
   * @return 비교 결과 (일 단위)
   */
  public static int diffDate(String date1, String date2) {
    return diffDate(strToDate(date1), strToDate(date2));
  }

  /**
   * 날짜 비교 (시간 단위)
   *
   * @param date1 비교 할 날짜 1
   * @param date2 비교 할 날짜 2
   * @return 비교 결과 (시간 단위)
   */
  public static int diffHour(Date date1, Date date2) {
    return (int) ((date1.getTime() - date2.getTime()) / (60 * 60 * 1000));
  }

  /**
   * 날짜 비교 (시간 단위)
   *
   * @param date1 비교 할 날짜 1
   * @param date2 비교 할 날짜 2
   * @return 비교 결과 (시간 단위)
   */
  public static int diffHour(String date1, String date2) {
    return diffHour(strToDate(date1), strToDate(date2));
  }

  /**
   * 날짜 비교 (분 단위)
   *
   * @param date1 비교 할 날짜 1
   * @param date2 비교 할 날짜 2
   * @return 비교 결과 (분 단위)
   */
  public static int diffMinute(Date date1, Date date2) {
    return (int) ((date1.getTime() - date2.getTime()) / (60 * 1000));
  }

  /**
   * 날짜 비교 (분 단위)
   *
   * @param date1 비교 할 날짜 1
   * @param date2 비교 할 날짜 2
   * @return 비교 결과 (분 단위)
   */
  public static int diffMinute(String date1, String date2) {
    return diffMinute(strToDate(date1), strToDate(date2));
  }


  /**
   * 문자열 날짜 포맷 변경
   *
   * @param strDate        문자열 날짜
   * @param strOrginFormat 기존 포맷
   * @param strNewFormat   변경될 포맷
   * @return dateString
   */
  public static String convertDateFormat(
      String strDate, String strOrginFormat, String strNewFormat
  ) {
    String dateString;

    try {

      SimpleDateFormat originFormat = new SimpleDateFormat(strOrginFormat);
      Date date = originFormat.parse(strDate);
      SimpleDateFormat format2 = new SimpleDateFormat(strNewFormat);
      dateString = format2.format(date);

    } catch (ParseException e) {
      return strDate;
    }

    return dateString;
  }
}
