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
import kr.co.happl.framework.common.logging.vo.HapplLogVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HapplLoggerTest {

	static Stream<Arguments> loggerStream() {
		return Stream.of(LoggerType.values())
				.map(item -> Arguments.of(item, HapplLogger.getInstance(item)));
	}

	static Stream<Arguments> logData() {
		return Stream.of(
				Arguments.of(null, "예외 없는 로그 테스트 메시지"),
				Arguments.of(new Exception("테스트 예외"), "예외 있는 로그 테스트 메시지")
		);
	}

	@ParameterizedTest(name = "{0} 타입 Logger 생성")
	@MethodSource("loggerStream")
	void getInstance(LoggerType loggerType, HapplLogger logger) {
		assertEquals(HapplLogger.getInstance(loggerType),logger);
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("logData")
	void error(Exception e, String message) {
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		logger.error(e, message);
		HapplLogger logger2 = HapplLogger.getInstance(this.getClass());
		logger2.error(e, message);
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("logData")
	void info(Exception e, String message) {
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		logger.info(e, message);
		HapplLogger logger2 = HapplLogger.getInstance(this.getClass());
		logger2.info(e, message);
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("logData")
	void warn(Exception e, String message) {
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		logger.warn(e, message);
		HapplLogger logger2 = HapplLogger.getInstance(this.getClass());
		logger2.warn(e, message);
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("logData")
	void debug(Exception e, String message) {
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		logger.debug(e, message);
		HapplLogger logger2 = HapplLogger.getInstance(this.getClass());
		logger2.debug(e, message);
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("logData")
	void trace(Exception e, String message) {
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		logger.trace(e, message);
		HapplLogger logger2 = HapplLogger.getInstance(this.getClass());
		logger2.trace(e, message);
	}

	@Test
	void multiMessages() {
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		String[] messages = { "다중 메시지 #1","다중 메시지 #2" };
		logger.info(messages);
	}

	@Test
	void byHapplLogVo() {
		HapplLogVo vo = new HapplLogVo();
		vo.setMessages(new String[]{"VO 예외 #1", "VO 예외 #2"});
		HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);
		logger.info(vo);
	}

}