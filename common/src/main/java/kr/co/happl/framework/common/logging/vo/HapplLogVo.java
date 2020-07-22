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

package kr.co.happl.framework.common.logging.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.happl.framework.common.annotations.ExcludeReflectField;
import kr.co.happl.framework.common.logging.enums.LoggingLevel;
import org.slf4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class HapplLogVo implements Serializable {

  @ExcludeReflectField
  private static final long serialVersionUID = -4453823641628916830L;

  @SuppressWarnings("FieldCanBeLocal")
  private String hostname;
  @SuppressWarnings("FieldCanBeLocal")
  private String serverTimeStamp;
  @SuppressWarnings("FieldCanBeLocal")
  private String serverIp;
  @SuppressWarnings("FieldCanBeLocal")
  private String callerMethod;
  @SuppressWarnings("FieldCanBeLocal")
  private String callerClass;

  @ExcludeReflectField
  private String[] messages;

  public HapplLogVo() {
    this.setCallerInfo(Thread.currentThread().getStackTrace());
  }

  public HapplLogVo(StackTraceElement[] elements) {
    this.setCallerInfo(elements);
  }

  private void setCallerInfo(StackTraceElement[] elements) {
    try {
      InetAddress host = InetAddress.getLocalHost();
      hostname = host.getHostName();
      serverIp = host.getHostAddress();
      serverTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

      for (int i = 0; i < elements.length; i++) {
        if (i > 0
            && !elements[i].getClassName().endsWith("HapplLogger")
            && !elements[i].getClassName().endsWith("HapplLogVo")
            && !elements[i].getClassName().endsWith("Exception")
            && !Class.forName(elements[i].getClassName())
            .getSuperclass().getName().endsWith("Exception")
        ) {
          callerClass = elements[i].getClassName();
          callerMethod = elements[i].getMethodName();
          break;
        }
      }

    } catch (UnknownHostException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Log 정보를 문자열로 반환
   *
   * @return 변환된 정보 문자열
   */
  public String getLogString() {
    String result = "";
    try {
      result = new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 로그 기본 정보 출력
   *
   * @param logger 출력 로그
   * @param level  로깅 레벨
   */
  public void printLog(Logger logger, LoggingLevel level) {
    try {
      Method method = logger.getClass().getMethod(level.toString().toLowerCase(), String.class);
      method.invoke(logger, "#############################################################");
      for (Field field : this.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        if (!field.isAnnotationPresent(ExcludeReflectField.class)) {
          method.invoke(logger,
              String.format("## %-16s: %s", field.getName().toUpperCase(), field.get(this))
          );
        }
      }
      method.invoke(logger, "#############################################################");
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public String[] getMessages() {
    return messages;
  }

  public void setMessages(String[] messages) {
    this.messages = messages;
  }
}
