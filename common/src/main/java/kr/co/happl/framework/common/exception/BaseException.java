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

package kr.co.happl.framework.common.exception;

import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Objects;

@SuppressWarnings("unused")
public class BaseException extends Exception {

  private static final long serialVersionUID = -4574916105745518228L;
  private final HapplLogger logger = HapplLogger.getInstance(LoggerType.ERROR);
  private String exceptionMessage = "";

  public BaseException() {
    super();
  }

  public BaseException(String message) {
    super(message);
    this.exceptionMessage = message;
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
    this.exceptionMessage = message;
  }

  public BaseException(Throwable cause) {
    super(cause);
  }

  protected BaseException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.exceptionMessage = message;
  }

  public void printExceptionLog() {
    logger.error(this.exceptionMessage);
  }

  @Override
  public void printStackTrace() {
    logger.error(this.getMessage());
    super.printStackTrace();
  }

  @Override
  public void printStackTrace(PrintStream s) {
    super.printStackTrace(s);
  }

  @Override
  public void printStackTrace(PrintWriter s) {
    super.printStackTrace(s);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BaseException)) {
      return false;
    }
    BaseException that = (BaseException) o;
    return Objects.equals(logger, that.logger)
        && Objects.equals(exceptionMessage, that.exceptionMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logger, exceptionMessage);
  }
}
