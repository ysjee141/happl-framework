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

package kr.co.happl.framework.common.logging;

import kr.co.happl.framework.common.logging.enums.LoggerType;
import kr.co.happl.framework.common.logging.enums.LoggingLevel;
import kr.co.happl.framework.common.logging.vo.HapplLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 시스템 로깅을 위한 클래스
 */
@SuppressWarnings("unused")
public class HapplLogger implements Serializable {

  private static final long serialVersionUID = -1798078948853050549L;
  private final Logger logger;

  /**
   * Logging 타입에 따른 Logger 생성
   *
   * @param loggerType Logging 타입
   */
  private HapplLogger(LoggerType loggerType) {
    this.logger = loggerType.getLogger();
  }

  private HapplLogger(Class<?> clazz) {
    this.logger = LoggerFactory.getLogger(clazz);
  }

  private HapplLogger(String loggerName) {
    this.logger = LoggerFactory.getLogger(loggerName);
  }

  /**
   * 싱글톤 Logger 생성
   *
   * @param loggerType 로그 타입
   * @return 싱글톤 Logger
   */
  public static HapplLogger getInstance(LoggerType loggerType) {
    return LazyHolder.HapplLogger.get(loggerType);
  }

  public static HapplLogger getInstance(Class<?> clazz) {
    return new HapplLogger(clazz);
  }

  public static HapplLogger getInstance(String loggerName) {
    return new HapplLogger(loggerName);
  }

  /**
   * 오류 로그 출력
   *
   * @param message 오류 메시지
   */
  public void error(String message) {
    this.error(null, message);
  }

  /**
   * 오류 로그 출력
   *
   * @param messages 오류 메시지
   */
  public void error(String[] messages) {
    this.error(null, messages);
  }

  /**
   * 오류 로그 출력
   *
   * @param ex       오류 객체
   * @param messages 오류 메시지
   */
  public void error(Exception ex, String[] messages) {
    this.printLog(LoggingLevel.ERROR, messages, ex);
  }

  /**
   * 오류 로그 출력
   *
   * @param ex 오류 객체
   * @param vo 오류 메시지 객체
   */
  public void error(Exception ex, HapplLogVo vo) {
    this.printLog(LoggingLevel.ERROR, vo, ex);
  }

  /**
   * 오류 로그 출력
   *
   * @param vo 오류 메시지 객체
   */
  public void error(HapplLogVo vo) {
    this.printLog(LoggingLevel.ERROR, vo, null);
  }

  /**
   * 오류 로그 출력
   *
   * @param ex      오류 객체
   * @param message 오류 메시지
   */
  public void error(Exception ex, String message) {
    this.error(ex, Collections.singletonList(message).toArray(new String[1]));
  }

  /**
   * 일반 로그 메시지 출력
   *
   * @param message 로그 메시지
   */
  public void info(String message) {
    this.info(null, message);
  }

  /**
   * 일반 로그 메시지 출력
   *
   * @param messages 로그 메시지
   */
  public void info(String[] messages) {
    this.info(null, messages);
  }

  /**
   * 일반 로그 메시지 출력
   *
   * @param ex      예외 객체
   * @param message 로그 메시지
   */
  public void info(Exception ex, String message) {
    this.info(ex, Collections.singletonList(message).toArray(new String[1]));
  }

  /**
   * 일반 로그 메시지 출력
   *
   * @param ex       예외 객체
   * @param messages 로그 메시지
   */
  public void info(Exception ex, String[] messages) {
    this.printLog(LoggingLevel.INFO, messages, ex);
  }

  /**
   * 일반 로그 메시지 출력
   *
   * @param ex 예외 객체
   * @param vo 로그 메시지 객체
   */
  public void info(Exception ex, HapplLogVo vo) {
    this.printLog(LoggingLevel.INFO, vo, ex);
  }

  /**
   * 일반 로그 메시지 출력
   *
   * @param vo 로그 메시지 객체
   */
  public void info(HapplLogVo vo) {
    this.printLog(LoggingLevel.INFO, vo, null);
  }

  /**
   * 추적 로그 메시지 출력
   *
   * @param message 로그 메시지
   */
  public void trace(String message) {
    this.trace(null, message);
  }

  /**
   * 추적 로그 메시지 출력
   *
   * @param messages 로그 메시지
   */
  public void trace(String[] messages) {
    this.trace(null, messages);
  }

  /**
   * 추적 로그 메시지 출력
   *
   * @param ex      예외 객체
   * @param message 로그 메시지
   */
  public void trace(Exception ex, String message) {
    this.trace(ex, Collections.singletonList(message).toArray(new String[1]));
  }

  /**
   * 추적 로그 메시지 출력
   *
   * @param ex       예외 객체
   * @param messages 로그 메시지
   */
  public void trace(Exception ex, String[] messages) {
    this.printLog(LoggingLevel.TRACE, messages, ex);
  }

  /**
   * 추적 로그 메시지 출력
   *
   * @param ex 예외 객체
   * @param vo 로그 메시지 객체
   */
  public void trace(Exception ex, HapplLogVo vo) {
    this.printLog(LoggingLevel.TRACE, vo, ex);
  }

  /**
   * 추적 로그 메시지 출력
   *
   * @param vo 로그 메시지 객체
   */
  public void trace(HapplLogVo vo) {
    this.printLog(LoggingLevel.TRACE, vo, null);
  }

  /**
   * 디버깅 로그 메시지 출력
   *
   * @param message 로그 메시지
   */
  public void debug(String message) {
    this.debug(null, message);
  }

  /**
   * 디버깅 로그 메시지 출력
   *
   * @param messages 로그 메시지
   */
  public void debug(String[] messages) {
    this.debug(null, messages);
  }

  /**
   * 디버깅 로그 메시지 출력
   *
   * @param ex      예외 객체
   * @param message 로그 메시지
   */
  public void debug(Exception ex, String message) {
    this.debug(ex, Collections.singletonList(message).toArray(new String[1]));
  }

  /**
   * 디버깅 로그 메시지 출력
   *
   * @param ex       예외 객체
   * @param messages 로그 메시지
   */
  public void debug(Exception ex, String[] messages) {
    this.printLog(LoggingLevel.DEBUG, messages, ex);
  }

  /**
   * 디버깅 로그 메시지 출력
   *
   * @param ex 예외 객체
   * @param vo 로그 메시지 객체
   */
  public void debug(Exception ex, HapplLogVo vo) {
    this.printLog(LoggingLevel.DEBUG, vo, ex);
  }

  /**
   * 디버깅 로그 메시지 출력
   *
   * @param vo 로그 메시지 객체
   */
  public void debug(HapplLogVo vo) {
    this.printLog(LoggingLevel.DEBUG, vo, null);
  }

  /**
   * 경고 로그 메시지 출력
   *
   * @param message 로그 메시지
   */
  public void warn(String message) {
    this.warn(null, message);
  }

  /**
   * 경고 로그 메시지 출력
   *
   * @param messages 로그 메시지
   */
  public void warn(String[] messages) {
    this.warn(null, messages);
  }

  /**
   * 경고 로그 메시지 출력
   *
   * @param ex      예외 객체
   * @param message 로그 메시지
   */
  public void warn(Exception ex, String message) {
    this.warn(ex, Collections.singleton(message).toArray(new String[1]));
  }

  /**
   * 경고 로그 메시지 출력
   *
   * @param ex       예외 객체
   * @param messages 로그 메시지
   */
  public void warn(Exception ex, String[] messages) {
    this.printLog(LoggingLevel.WARN, messages, ex);
  }

  /**
   * 경고 로그 메시지 출력
   *
   * @param ex 예외 객체
   * @param vo 로그 메시지 객체
   */
  public void warn(Exception ex, HapplLogVo vo) {
    this.printLog(LoggingLevel.WARN, vo, ex);
  }

  /**
   * 경고 로그 메시지 출력
   *
   * @param vo 로그 메시지 객체
   */
  public void warn(HapplLogVo vo) {
    this.printLog(LoggingLevel.WARN, vo, null);
  }

  /**
   * 로깅 레벨에 따른 로그 출력 함수
   * Exception 객체가 null 일 경우 로그 메시지만 출력하고,
   * Exception 객체가 null 이 아닐 경우 로그 메시지 출력 후 Stack Trace 를 출력합니다.
   * 함수 호출시 {@link HapplLogVo#printLog(Logger, LoggingLevel)} 가 같이 호출 됩니다.
   *
   * @param level    로깅 레벨
   * @param messages 로그 메시지
   * @param ex       예외 객체
   */
  private void printLog(LoggingLevel level, String[] messages, Exception ex) {
    HapplLogVo vo = new HapplLogVo();
    if (messages != null) {
      vo.setMessages(messages);
    }
    this.printLog(level, vo, ex);
  }

  /**
   * 로깅 레벨에 따른 로그 출력 함수
   * Exception 객체가 null 일 경우 로그 메시지만 출력하고,
   * Exception 객체가 null 이 아닐 경우 로그 메시지 출력 후 Stack Trace 를 출력합니다.
   * 함수 호출시 {@link HapplLogVo#printLog(Logger, LoggingLevel)} 가 같이 호출 됩니다.
   *
   * @param level 로깅 레벨
   * @param vo    로그 메시지 객체
   * @param ex    예외 객체
   */
  private void printLog(LoggingLevel level, HapplLogVo vo, Exception ex) {
    vo.printLog(logger, level);
    try {
      if (ex != null) {
        for (String message : vo.getMessages()) {
          logger.getClass().getMethod(level.toString().toLowerCase(), String.class, Throwable.class)
              .invoke(logger, message, ex);
        }
      } else {
        for (String message : vo.getMessages()) {
          logger.getClass().getMethod(level.toString().toLowerCase(), String.class)
              .invoke(logger, message);
        }
      }
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HapplLogger)) {
      return false;
    }
    HapplLogger logger1 = (HapplLogger) o;
    return Objects.equals(logger, logger1.logger);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logger);
  }

  /**
   * 싱글톤 처리를 위한 Lazy Holder 클래스
   */
  private static class LazyHolder {
    private static final Map<LoggerType, HapplLogger> HapplLogger = new ConcurrentHashMap<>();

    static {
      Stream.of(LoggerType.values())
          .forEach(item -> HapplLogger.put(item, new HapplLogger(item)));
    }
  }
}